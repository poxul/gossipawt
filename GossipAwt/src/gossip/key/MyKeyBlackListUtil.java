package gossip.key;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import gossip.lib.file.FileNameUtil.PathResource;
import gossip.lib.file.FileService;
import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;
import gossip.manager.LanguageManager;
import gossip.util.FileLoaderUtil;
import gossip.util.LanguageUtil;

public class MyKeyBlackListUtil {

	private static final Logger logger = MyLogger.getLog(MyKeyBlackListUtil.class);

	private static final String EMPTY_KEY_TEXT = "";

	/**
	 * Map in der zu jeder Landeseinstellung eine Map hinterlegt ist in der zu jedem
	 * Listennamen ein Dateiname zugeordnet wurde.
	 */
	private static final Map<Locale, Map<String, String>> blackListFiles = new HashMap<Locale, Map<String, String>>(6);

	/**
	 * Map in der alle Sperrlisten zwischengespeichert werdeb, die schon einmal
	 * geladen wurden.
	 */
	private static final Map<String, MyKeyBlackList> blackListsMap = new HashMap<>();

	private MyKeyBlackListUtil() {
	}

	public static MyKeyBlackList stripBlackListLines(List<List<MyKey>> keyLines, Locale locale, String listName) {
		MyKeyBlackList resultList = new MyKeyBlackList();
		MyKeyBlackList bl = getBlackList(locale, listName);
		for (List<MyKey> keys : keyLines) {
			List<MyKey> next = copyKeyLine(keys);
			next = stripBlackList(next, bl);
			resultList.add(new ArrayList<>(next));
		}
		return resultList;
	}

	/**
	 * Return true if the input text contains a character listed in the blacklist.
	 * 
	 * @param txt           input text
	 * @param locale        county
	 * @param blackListName name of the blacklist e.g. "articleNumber"
	 * @return
	 */
	public static boolean checkForBlocked(String txt, Locale locale, String blackListName) {
		MyKeyBlackList bl = getBlackList(locale, blackListName);
		return bl == null ? false : bl.containsBlockedKeys(txt);
	}

	/**
	 * deep copy
	 * 
	 * @param keys
	 * @return
	 */
	private static List<MyKey> copyKeyLine(List<MyKey> keys) {
		List<MyKey> next = new ArrayList<>();
		for (MyKey k : keys) {
			next.add(new MyKey(k));
		}
		return next;
	}

	private static List<MyKey> stripBlackList(List<MyKey> keys, MyKeyBlackList bl) {
		for (MyKey k : keys) {
			stripBlackListKeyLines(k, bl);
		}
		return keys;
	}

	private static void stripBlackListKeyLines(MyKey k, MyKeyBlackList bl) {
		for (List<MyKey> keys : bl) {
			stripBlackListKeys(k, keys);
		}
	}

	private static void stripBlackListKeys(MyKey k, List<MyKey> bl) {
		for (MyKey key : bl) {
			String black = key.getKeyText(); // black lists have no alt and shift keys!
			if (StringUtil.compare(k.getKeyText(), black)) {
				k.setKeyText(EMPTY_KEY_TEXT);
			} else if (StringUtil.compare(k.getAltKeyText(), black)) {
				k.setAltKeyText(EMPTY_KEY_TEXT);
			} else if (StringUtil.compare(k.getShiftKeyText(), black)) {
				k.setShiftKeyText(EMPTY_KEY_TEXT);
			}
		}
	}

	public synchronized static MyKeyBlackList getBlackList(Locale locale, String name) {
		MyKeyBlackList list = blackListsMap.get(name);
		if (list == null) {
			Document xml = getBlackListDocument(locale, name);
			list = new MyKeyBlackList();
			LanguageUtil.parseBlackList(xml, list);
			blackListsMap.put(name, list);
		}
		return list;
	}

	private synchronized static String getBlackListFileName(Locale locale, String type) {
		if (blackListFiles.isEmpty()) {
			String path = FileService.getPath(PathResource.RESOURCE) + File.separatorChar + "xml" + File.separatorChar + "keys" + File.separatorChar + "BlackLists.xml";
			Document doc = FileLoaderUtil.getDocFromXml(path);
			LanguageUtil.parseBlackListFiles(doc, blackListFiles);
		}
		Map<String, String> resultMap = blackListFiles.get(locale);
		if (resultMap == null) {
			logger.warn(MyLogger.OBSERVER_MARKER, "try to use manager definition of locale={} type={}", locale, type);
			resultMap = blackListFiles.get(LanguageManager.getLocale());
		}
		if (resultMap != null) {
			return resultMap.get(type);
		} else {
			logger.error("Invalid blacklist={} Type={}", locale, type);
			return null;
		}
	}

	public static Document getBlackListDocument(Locale locale, String type) {
		String fileName = getBlackListFileName(locale, type);
		Document doc = null;
		if (!StringUtil.isNullOrEmpty(fileName)) {
			doc = FileLoaderUtil.getDocFromXml(fileName);
		}
		return doc;
	}

}
