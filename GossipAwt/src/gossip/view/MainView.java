package gossip.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.data.AwtData;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;
import gossip.view.chatview.JPanelChatView;
import gossip.view.dictionary.JPanelDictionary;

public class MainView extends JPanelDisposable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(MainView.class);

	static final String CHAT_VIEW = "chat";
	static final String DICTIONARY_VIEW = "dictionary";

	private final ViewController viewController;

	/**
	 * Main
	 */
	private JPanelChatView chatView;

	/**
	 * Footer
	 */
	private MainFooterView footerView;

	/**
	 * Headline
	 */
	private MainHeaderView headlineView;

	/**
	 * Center view
	 */
	private JPanelDisposable view;
	private CardLayout cardLayout = new CardLayout();

	/**
	 * Panel that shows possible chat clients
	 */
	private JPanelDictionary dictionary;

	/*
	 * Modle data
	 */
	private String viewName = CHAT_VIEW;

	private final AwtData data;

	public MainView(ViewController viewController, AwtData data) {
		this.viewController = viewController;
		this.data = data;
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
		JPanelChatView panel = new JPanelChatView(viewController);
		return panel;
	}

	private MainFooterView createFooterView() {
		MainFooterView panel = new MainFooterView(viewController, data);
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

	private JPanelDictionary getDictionaryView() {
		if (dictionary == null) {
			dictionary = new JPanelDictionary();
		}
		return dictionary;
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

	private JPanelDisposable getView() {
		if (view == null) {
			view = new JPanelDisposable();
			view.setLayout(cardLayout);
			view.add(getChatView(), CHAT_VIEW);
			view.add(getDictionaryView(), DICTIONARY_VIEW);
		}
		return view;
	}

	private void init() {
		buildView();
		data.getMainTabProperty().addModelChangeListener((source, origin, oldValue, newValue) -> {
			String name = data.getMainTabProperty().getValue();
			switchView(name);
		});

	}

	public boolean isChatView() {
		return StringUtil.compare(CHAT_VIEW, viewName);
	}

	public void switchView(String name) {
		if (!StringUtil.compare(viewName, name)) {
			cardLayout.show(getView(), name);
			viewName = name;
		}
	}

}
