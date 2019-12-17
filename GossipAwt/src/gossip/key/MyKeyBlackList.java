package gossip.key;

import java.util.ArrayList;
import java.util.List;

public class MyKeyBlackList extends ArrayList<List<MyKey>> {

	private static final long serialVersionUID = 1L;

	private static boolean containsString(List<MyKey> line, String str) {
		boolean result = false;
		for (MyKey key : line) {
			if (key.containsString(str)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean containsBlockedKeys(String str) {
		boolean result = false;
		for (char c : str.toCharArray()) {
			result = containsKey(c);
			if (result) {
				break;
			}
		}
		return result;
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	public boolean containsKey(char c) {
		boolean result = false;
		String str = String.valueOf(c);
		for (List<MyKey> line : this) {
			result = containsString(line, str);
			if (result) {
				break;
			}
		}
		return result;
	}

}