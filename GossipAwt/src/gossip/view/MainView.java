package gossip.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import gossip.chatview.JPanelChatView;
import gossip.config.ColorConstants;
import gossip.config.FontConstants;
import gossip.data.AwtBroker;
import gossip.data.MyProfileId;
import gossip.data.OperatorSayMessage;
import gossip.keyboard.input.InputItemId;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.manager.LanguageManager;
import gossip.util.StringValueUtil;

public class MainView extends JPanelDisposable {

	private static final long serialVersionUID = 1L;

	private static final int HEADLINE_HEIGHT = 30;
	private static final int FOOTER_HEIGHT = 30;

	private JPanelChatView chatView;
	private JPanelDisposable footerView;

	/*
	 * Headline
	 */
	private JPanelDisposable headlineView;
	private JPanelDisposable serverConnectionPanel;
	private JCheckBox serverConnectionCheckBox;
	private JPanelDisposable versionPanel;
	private JLabel versionLabel;

	private JPanelDisposable clientsPanel;

	private JLabel clientsLabel;

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
		OperatorSayMessage m = new OperatorSayMessage(sender, "hallo wer da?", new Date());
		panel.addMessage(m);
		// dummy message end
		return panel;
	}

	private JPanelDisposable createFooterView() {
		JPanelDisposable panel = new JPanelDisposable();
		panel.setBackground(Color.green);
		panel.setPreferredSize(new Dimension(100, FOOTER_HEIGHT));
		return panel;
	}

	private JPanelDisposable createHeadlineView() {
		JPanelDisposable panel = new JPanelDisposable();
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder());
		panel.setPreferredSize(new Dimension(100, HEADLINE_HEIGHT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(getServerConnectionPanel(), BorderLayout.WEST);
		panel.add(getClientsPanel(), BorderLayout.CENTER);
		panel.add(getVersionPanel(), BorderLayout.EAST);
		return panel;
	}

	private JPanelDisposable getServerConnectionPanel() {
		if (serverConnectionPanel == null) {
			serverConnectionPanel = new JPanelDisposable();
			serverConnectionPanel.setLayout(new FlowLayout());
			serverConnectionPanel.setOpaque(false);
			serverConnectionPanel.setPreferredSize(new Dimension(130, HEADLINE_HEIGHT));
			serverConnectionPanel.add(getServerConnectionCheckBox());
		}
		return serverConnectionPanel;
	}

	private JPanelDisposable getVersionPanel() {
		if (versionPanel == null) {
			versionPanel = new JPanelDisposable();
			versionPanel.setLayout(new FlowLayout());
			versionPanel.setOpaque(false);
			versionPanel.setPreferredSize(new Dimension(130, HEADLINE_HEIGHT));
			versionPanel.add(getVersionLabel());
		}
		return versionPanel;
	}

	private JPanelDisposable getClientsPanel() {
		if (clientsPanel == null) {
			clientsPanel = new JPanelDisposable();
			clientsPanel.setLayout(new FlowLayout());
			clientsPanel.setOpaque(false);
			clientsPanel.setPreferredSize(new Dimension(130, HEADLINE_HEIGHT));
			clientsPanel.add(getClientsLabel());
		}
		return clientsPanel;
	}

	private JLabel getVersionLabel() {
		if (versionLabel == null) {
			versionLabel = new JLabel(StringValueUtil.buildVersionString());
			versionLabel.setOpaque(false);
			versionLabel.setFont(FontConstants.MAIN_HEADLINE_FONT);
		}
		return versionLabel;
	}

	private JLabel getClientsLabel() {
		if (clientsLabel == null) {
			clientsLabel = new JLabel("0");
			clientsLabel.setOpaque(false);
			clientsLabel.setFont(FontConstants.MAIN_HEADLINE_FONT);
		}
		return clientsLabel;
	}

	private JCheckBox getServerConnectionCheckBox() {
		if (serverConnectionCheckBox == null) {
			serverConnectionCheckBox = new JCheckBox(LanguageManager.getLocaleText(new InputItemId("headline.serverconnection")));
			serverConnectionCheckBox.setOpaque(false);
			serverConnectionCheckBox.setEnabled(false);
			serverConnectionCheckBox.setFont(FontConstants.MAIN_HEADLINE_FONT);
		}
		return serverConnectionCheckBox;
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
		buildView();
		// observe
		AwtBroker.get().getData().getServerConnectedProperty().addModelChangeListener((source, origin, oldValue, newValue) -> updateServerState());
		AwtBroker.get().getData().getClients().addModelChangeListener((source, origin, oldValue, newValue) -> updateClientCount());
		// initial read data
		updateServerState();
		updateClientCount();
	}

	private void updateClientCount() {
		int clients = AwtBroker.get().getData().getClients().size();
		getClientsLabel().setText(StringValueUtil.buildClientsString(clients));
	}

	private void updateServerState() {
		boolean state = AwtBroker.get().getData().getServerConnectedProperty().getValue();
		getServerConnectionCheckBox().setSelected(state);
	}

}
