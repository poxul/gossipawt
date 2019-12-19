package gossip.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import gossip.config.DimensionConstants;
import gossip.config.FontConstants;
import gossip.data.AwtBroker;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.manager.LanguageManager;
import gossip.util.StringValueUtil;
import gossip.view.keyboard.input.InputItemId;

public class MainHeaderView extends JPanelDisposable {

	private static final long serialVersionUID = 1L;

	/*
	 * Headline
	 */
	private JPanelDisposable serverConnectionPanel;
	private JCheckBox serverConnectionCheckBox;

	private JPanelDisposable versionPanel;
	private JLabel versionLabel;

	private JPanelDisposable clientsPanel;
	private JLabel clientsLabel;

	public MainHeaderView() {
		init();
	}

	private void buildView() {
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder());
		setPreferredSize(new Dimension(100, DimensionConstants.HEADLINE_HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(getServerConnectionPanel(), BorderLayout.WEST);
		add(getClientsPanel(), BorderLayout.CENTER);
		add(getVersionPanel(), BorderLayout.EAST);
	}

	private JPanelDisposable getServerConnectionPanel() {
		if (serverConnectionPanel == null) {
			serverConnectionPanel = new JPanelDisposable();
			serverConnectionPanel.setLayout(new FlowLayout());
			serverConnectionPanel.setOpaque(false);
			serverConnectionPanel.setPreferredSize(new Dimension(DimensionConstants.HEADLINE_SERVER_CONNECTION_WIDTH, DimensionConstants.HEADLINE_HEIGHT));
			serverConnectionPanel.add(getServerConnectionCheckBox());
		}
		return serverConnectionPanel;
	}

	private JPanelDisposable getVersionPanel() {
		if (versionPanel == null) {
			versionPanel = new JPanelDisposable();
			versionPanel.setLayout(new FlowLayout());
			versionPanel.setOpaque(false);
			versionPanel.setPreferredSize(new Dimension(DimensionConstants.HEADLINE_VERSION_WIDTH, DimensionConstants.HEADLINE_HEIGHT));
			versionPanel.add(getVersionLabel());
		}
		return versionPanel;
	}

	private JPanelDisposable getClientsPanel() {
		if (clientsPanel == null) {
			clientsPanel = new JPanelDisposable();
			clientsPanel.setLayout(new FlowLayout());
			clientsPanel.setOpaque(false);
			clientsPanel.setPreferredSize(new Dimension(DimensionConstants.HEADLINE_NUM_CLIENTS_WIDTH, DimensionConstants.HEADLINE_HEIGHT));
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
			serverConnectionCheckBox = new JCheckBox(LanguageManager.getLocaleText(new InputItemId("headline.server_connection")));
			serverConnectionCheckBox.setOpaque(false);
			serverConnectionCheckBox.setEnabled(false);
			serverConnectionCheckBox.setFont(FontConstants.MAIN_HEADLINE_FONT);
		}
		return serverConnectionCheckBox;
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
