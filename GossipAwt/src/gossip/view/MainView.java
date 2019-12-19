package gossip.view;

import java.awt.BorderLayout;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import gossip.chatview.JPanelChatView;
import gossip.config.ColorConstants;
import gossip.data.MyProfileId;
import gossip.data.OperatorSayMessage;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.util.MyLogger;

public class MainView extends JPanelDisposable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(MainView.class);
	
	/*
	 * Main
	 */
	private JPanelChatView chatView;

	/*
	 * Footer
	 */
	private MainFooterView footerView;

	/*
	 * Headline
	 */
	private MainHeaderView headlineView;

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

	private MainFooterView createFooterView() {
		MainFooterView panel = new MainFooterView();
		logger.info("create footer");
		return panel;
	}

	private MainHeaderView createHeadlineView() {
		MainHeaderView panel = new MainHeaderView();
		logger.info("creata header");
		return panel;
	}

	private JPanelChatView getChatView() {
		if (chatView == null) {
			chatView = createChatView();
		}
		return chatView;
	}

	private MainFooterView getFooterView() {
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
	}

}
