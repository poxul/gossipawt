package gossip.data;

import java.util.Collection;

import org.apache.logging.log4j.Logger;

import gossip.connect.message.MessageDispatcher;
import gossip.connect.proto.Client;
import gossip.connect.proto.Communication.CommunicationState;
import gossip.data.MyProfile;
import gossip.data.MyProfileId;
import gossip.data.OperatorSayMessage;
import gossip.data.client.ClientDataModel;
import gossip.lib.util.MyLogger;
import gossip.run.ConfigurationService;
import gossip.run.GossipClient;

public class AwtDataController {

	private static final Logger logger = MyLogger.getLog(AwtDataController.class.getName());

	private GossipClient gClient;
	private final AwtData data;

	private SelecitonChangedListener selectionChangedListener = (selected, isAdded) -> {
		logger.info("selection changed {} {}", selected, isAdded);
		if (isAdded) {
			getClientData().select(selected);
		} else {
			getClientData().unselect(selected);
		}
	};

	public AwtDataController(AwtData data) {
		this.data = data;
	}

	public void say(String txt) {
		getClientData().say(txt);
	}

	private Client getClient() {
		return gClient.getClient();
	}

	private MessageDispatcher getDispatcher() {
		return getClient().getDispatcher();
	}

	public ClientDataModel getClientData() {
		return gClient.getData();
	}

	public void init(GossipClient gClient) {
		this.gClient = gClient;

		// init server connection state
		getDispatcher().addChannelConditionListener(event -> {
			logger.info("received connection changed event: {}", event);
			switch (event.getState()) {
			case connected:
				data.setConnected(true);
				break;
			default:
				data.setConnected(false);
				break;
			}
		});
		data.setConnected(getDispatcher().getCommunicationState() == CommunicationState.connected);

		// init client count
		getClientData().addModelChangeListener((source, origin, oldValue, newValue) -> {
			if (ClientDataModel.SYNC.equals(origin) || ClientDataModel.CLEAR.equals(origin)) {
				updateClientCount();
				updateClients();
			} else if (ClientDataModel.SELECTION.equals(origin)) {
				updateReceiverCount();
				updateClients();
			} else if (ClientDataModel.SAY.equals(origin)) {
				if (newValue instanceof OperatorSayMessage) {
					addMyMessage((OperatorSayMessage) newValue);
				} else {
					logger.error("invalid say event {}", newValue);
				}
			} else if (ClientDataModel.INCOMMING.equals(origin)) {
				if (newValue instanceof OperatorSayMessage) {
					addIncommingMessage((OperatorSayMessage) newValue);
				} else {
					logger.error("invalid say event {}", newValue);
				}
			} else {
				logger.warn("unhandled event {} {}", origin, newValue);
			}
		});

		// initial set properties
		updateClients();
		updateClientCount();
		updateReceiverCount();
		updateMyProfile(ConfigurationService.getMyProfile());
		// init done
	}

	private void addIncommingMessage(OperatorSayMessage osm) {
		data.addMessage(osm);
	}

	private void addMyMessage(OperatorSayMessage osm) {
		data.addMessage(osm);
	}

	private void updateMyProfile(MyProfile myProfile) {
		data.setMyProfile(myProfile);
	}

	protected void updateClientCount() {
		ClientDataModel model = getClientData();
		int size = model.getDirectorySize();
		data.setConnectionsSize(size);
	}

	protected void updateReceiverCount() {
		ClientDataModel model = getClientData();
		int size = model.getSelected().size();
		data.setReceiverSize(size);
	}

	protected void updateClients() {
		ClientDataModel model = getClientData();
		Collection<MyProfile> collection = model.getDirectoryContent();
		data.setConnections(collection);
		data.addSelectionChangedListener(selectionChangedListener);
	}
	
	public MyProfile getMyProfile(MyProfileId id) {
		return getClientData().getMyProfileOf(id);
	}
}
