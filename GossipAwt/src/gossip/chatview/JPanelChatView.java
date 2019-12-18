package gossip.chatview;

import org.apache.logging.log4j.Logger;

import gossip.data.AwtBroker;
import gossip.data.OperatorSayMessage;
import gossip.data.client.ClientDataModel;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.util.MyLogger;

public class JPanelChatView extends JPanelDisposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(JPanelChatView.class);

	public JPanelChatView() {
		init();
	}

	private ClientDataModel getClientData() {
		return AwtBroker.get().getController().getClientData();
	}

	private void init() {

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

		// TODO Auto-generated method stub
		buildView();
	}

	private void addIncommingMessage(OperatorSayMessage newValue) {
		// TODO Auto-generated method stub

	}

	private void addMyMessage(OperatorSayMessage newValue) {
		// TODO Auto-generated method stub

	}

	private void updateReceiverCount() {
		// TODO Auto-generated method stub

	}

	private void updateClients() {
		// TODO Auto-generated method stub

	}

	private void updateClientCount() {
		// TODO Auto-generated method stub

	}

	private void buildView() {
		// TODO Auto-generated method stub

	}

}
