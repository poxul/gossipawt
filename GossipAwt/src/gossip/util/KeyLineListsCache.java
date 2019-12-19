package gossip.util;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import gossip.view.keyboard.key.MyKey;


public class KeyLineListsCache {

	private final Map<Locale, List<List<MyKey>>> keyLineLists = new HashMap<>();

	public List<List<MyKey>> getKeyLineList(Locale locale) {
		synchronized (keyLineLists) {
			return keyLineLists.get(locale);
		}
	}

	public void putKeyLineList(Locale locale, List<List<MyKey>> keyLineList) {
		synchronized (keyLineLists) {
			keyLineLists.put(locale, keyLineList);
		}
	}

}