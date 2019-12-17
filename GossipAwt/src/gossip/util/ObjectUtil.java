package gossip.util;

import java.util.ArrayList;
import java.util.List;

public class ObjectUtil {

	/**
	 * Avoid null pointer exception at calling equals.
	 * 
	 * @param o1
	 * @param o2
	 * @return Returns true if o1 equals o2 or both are null.
	 */
	public static boolean compare(Object o1, Object o2) {
		if (o1 == null) {
			return (o2 == null);
		}
		return o1.equals(o2);
	}

	public static int[] reverse(int[] a) {
		int end = a.length - 1;
		int i = 0;
		while (i < end) {
			int temp = a[i];
			a[i] = a[end];
			a[end] = temp;
			end--;
			i++;
		}
		return a;
	}

	static boolean isValidList(List<?> list) {
		return (list != null && !list.isEmpty());
	}

	public static boolean compareDouble(double d1, double d2) {
		return (Math.abs(d1 - d2) < 0.000001);
	}

	/**
	 * Erzeugt eine Liste von Elemente, die in der Liste1 vorhanden sind aber nicht in Liste2
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	@SuppressWarnings({
			"rawtypes",
			"unchecked" })
	public static List<?> getMissingList(List<?> list1, List<?> list2) {
		if (list1 == null || list1.isEmpty()) {
			return null;
		}
		if (list2 == null || list2.isEmpty()) {
			return new ArrayList(list1);
		}

		List resultList = new ArrayList();
		for (Object object : list1) {
			if (!list2.contains(object)) {
				resultList.add(object);
			}
		}
		return resultList;
	}

	public static boolean checkNotNull(Object... param) {
		boolean rc = true;
		for (int i = 0; i < param.length; i++) {
			if (param[i] == null) {
				rc = false;
				break;
			}
		}
		return rc;
	}

}
