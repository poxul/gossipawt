package gossip.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.data.MyProfileId;
import gossip.data.OperatorSayMessage;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;
import gossip.view.chatview.JPanelChatView;
import gossip.view.dictionary.JPanelDictionary;

public class MainView extends JPanelDisposable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(MainView.class);

	private static final String CHAT_VIEW = "chat";
	private static final String DICTIONARY_VIEW = "dictionary";

	
	public class ViewController {
		
		public void showDictionary() {
			switchView( DICTIONARY_VIEW);
		}
		
		public void showChat() {
			switchView( CHAT_VIEW);
		}
		
		public boolean isChatView() {
			return StringUtil.compare(CHAT_VIEW, viewName);
		}
		
	}
	
	private ViewController viewController = new ViewController();
	
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

	/*
	 * Center view
	 */
	private JPanelDisposable view;
	private CardLayout cardLayout = new CardLayout();

	private JPanelDictionary dictionary;

	private String viewName = CHAT_VIEW;

	public MainView() {
		init();
	}

	private void buildView() {
		setLayout(new BorderLayout());
		setBackground(ColorConstants.MAIN_VIEW_BACKGROUND);
		add(getHeatlineView(), BorderLayout.NORTH);
		add(getView(), BorderLayout.CENTER);
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

	private JPanelDisposable getView() {
		if (view == null) {
			view = new JPanelDisposable();
			view.setLayout(cardLayout);
			view.add(getChatView(), CHAT_VIEW);
			view.add(getDictionaryView(), DICTIONARY_VIEW);
		}
		return view;
	}

	public void switchView(String name) {
		if (!StringUtil.compare(viewName, name)) {
			cardLayout.show(getView(), name);
			viewName = name;
		}
	}

	private JPanelDictionary getDictionaryView() {
		if (dictionary == null) {
			dictionary = new JPanelDictionary();
		}
		return dictionary;
	}

	private MainFooterView createFooterView() {
		MainFooterView panel = new MainFooterView(viewController);
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
