package gossip.view.dictionary;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.config.InputItemConstants;
import gossip.data.device.DeviceData.ApplicationType;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.util.MyLogger;
import gossip.manager.LanguageManager;

public class JPanelDictionary extends JPanelDisposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(JPanelDictionary.class);

	public JPanelDictionary() {
		init();
	}

	private void init() {
		logger.info("start init");
		buildView();
	}

	private void buildView() {
		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(ColorConstants.EDIT_VIEW_BACKGROUND);
		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabpane.addTab(LanguageManager.getLocaleText(InputItemConstants.ITEM_APPLICATION_MACHINE), createDictionaryTab(ApplicationType.MACHINE));
		tabpane.addTab(LanguageManager.getLocaleText(InputItemConstants.ITEM_APPLICATION_DESCTOP), createDictionaryTab(ApplicationType.DESKTOP));
		add(tabpane, BorderLayout.CENTER);
	}

	private JPanelDictionaryTab createDictionaryTab(ApplicationType filterType) {
		return new JPanelDictionaryTab(filterType);
	}

}
