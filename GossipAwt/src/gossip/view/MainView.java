package gossip.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;
import java.util.UUID;

import gossip.chatview.JPanelChatView;
import gossip.config.ColorConstants;
import gossip.data.AwtBroker;
import gossip.data.MyProfileId;
import gossip.data.OperatorSayMessage;
import gossip.data.client.ClientDataModel;
import gossip.lib.panel.disposable.JPanelDisposable;

public class MainView extends JPanelDisposable {

	private static final long serialVersionUID = 1L;

	private JPanelChatView chatView;
	private JPanelDisposable headlineView;
	private JPanelDisposable footerView;

	public MainView() {
		init();
	}

	private void buildView() {
		setLayout(new BorderLayout());
		setBackground(ColorConstants.MAIN_VIEW_BACKGROUND);
		add(getHeatlineView(), BorderLayout.NORTH);
		add(getChatView(), BorderLayout.CENTER);
		add(getFooterView(), BorderLayout.SOUTH);
	}

	private JPanelChatView createChatView() {
		JPanelChatView panel = new JPanelChatView();
		// dummy message
		MyProfileId sender = new MyProfileId(new UUID(0, 1));
		OperatorSayMessage m = new OperatorSayMessage(sender, "lala", new Date());
		panel.addMessage(m);
		// dummy message end
		return panel;
	}

	private JPanelDisposable createFooterView() {
		JPanelDisposable panel = new JPanelDisposable();
		panel.setBackground(Color.green);
		panel.setPreferredSize(new Dimension(100, 20));
		return panel;
	}

	private JPanelDisposable createHeadlineView() {
		JPanelDisposable panel = new JPanelDisposable();
		panel.setBackground(Color.red);
		panel.setPreferredSize(new Dimension(100, 20));
		return panel;
	}

	private JPanelChatView getChatView() {
		if (chatView == null) {
			chatView = createChatView();
		}
		return chatView;
	}

	private JPanelDisposable getFooterView() {
		if (footerView == null) {
			footerView = createFooterView();
		}
		return footerView;
	}

	private JPanelDisposable getHeatlineView() {
		if (headlineView == null) {
			headlineView = createHeadlineView();
		}
		return headlineView;
	}

	private void init() {
		// init observation
		getClientData().addModelChangeListener((source, origin, oldValue, newValue) -> {
			if (ClientDataModel.SYNC.equals(origin) || ClientDataModel.CLEAR.equals(origin)) {
				updateClientCount();
				updateClients();
			} else if (ClientDataModel.SELECTION.equals(origin)) {
				updateReceiverCount();
				updateClients();
			} 
		});

		buildView();
	}

	private void updateReceiverCount() {
		// TODO Auto-generated method stub
		
	}

	private void updateClientCount() {
		// TODO Auto-generated method stub
		
	}

	private void updateClients() {
		// TODO Auto-generated method stub
		
	}

	private ClientDataModel getClientData() {
		return AwtBroker.get().getController().getClientData();
	}
}
