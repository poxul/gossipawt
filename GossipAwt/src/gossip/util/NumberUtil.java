package gossip.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.apache.commons.lang3.math.NumberUtils;

import gossip.lib.util.StringUtil;

public class NumberUtil {

	private static final DecimalFormat DOUBLE_FROMAT = new DecimalFormat("#.#######");

	static {
		DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
		sym.setDecimalSeparator('.');
		DOUBLE_FROMAT.setDecimalFormatSymbols(sym);
	}

	public static String doubleToSting(double v) {
		return DOUBLE_FROMAT.format(v);
	}

	public static long parseHexNumber(String str, long defaultNum) {
		long num;
		if (str != null) {
			try {
				num = Long.valueOf(str, 16);
			} catch (NumberFormatException e) {
				num = defaultNum;
			}
		} else {
			num = defaultNum;
		}
		return num;
	}

	public static String longToHexString(String str, long defaultNum) {
		long val;
		try {
			val = Long.valueOf(str);
		} catch (Exception e) {
			val = defaultNum;
		}
		return Long.toHexString(val);
	}

	public static int parseIntNumber(String str, int defaultNum) {
		int num;
		if (str != null) {
			try {
				num = Integer.valueOf(str.trim());
			} catch (NumberFormatException e) {
				num = defaultNum;
			}
		} else {
			num = defaultNum;
		}
		return num;
	}

	public static Number parseNumber(String str, int defaultNum) {
		Number num;
		if (str != null) {
			try {
				num = NumberUtils.createNumber(str);
			} catch (NumberFormatException e) {
				num = defaultNum;
			}
		} else {
			num = defaultNum;
		}
		return num;
	}

	public static long parseLongNumber(String str, long defaultNum) {
		long num;
		if (str != null) {
			try {
				num = Long.valueOf(str.trim());
			} catch (NumberFormatException e) {
				num = defaultNum;
			}
		} else {
			num = defaultNum;
		}
		return num;
	}

	public static float parseFloatNumber(String str, float defaultNum) {
		float num;
		if (str != null) {
			try {
				num = Float.valueOf(str);
			} catch (NumberFormatException e) {
				num = defaultNum;
			}
		} else {
			num = defaultNum;
		}
		return num;
	}

	public static double parseDoubleNumber(String str, double defaultNum) {
		double num;
		if (str != null) {
			try {
				num = Double.valueOf(str);
			} catch (NumberFormatException e) {
				num = defaultNum;
			}
		} else {
			num = defaultNum;
		}
		return num;
	}

	public static int getStepOf(double value, int steps) {
		return getStepOf(value / steps);
	}

	private static int getStepOf(double value) {
		int diff = (int) (Math.log10(value * 2));
		diff = (int) Math.pow(10, diff);
		return getNormStepOf(value / diff) * diff;
	}

	private static int getNormStepOf(double value) {
		if (value < steps[0]) {
			return steps[0];
		}

		if (value > steps[steps.length - 1]) {
			return steps[steps.length - 1];
		}

		double diffResult = 0;
		int step = 0;
		for (int i = 0; i < steps.length; i++) {
			double diff = calculateWeight(value, i);
			if (diff > diffResult) {
				step = i;
				if (diff >= 1.0) {
					break;
				}
				diffResult = diff;
			}
		}
		return steps[step];
	}

	/**
	 * p2 _______ p3
	 * 
	 * / \ / \
	 * 
	 * ---- ------
	 * 
	 * p1 p4
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @param value
	 * @return
	 */
	private static double fuzzy(double p1, double p2, double p3, double p4, double value) {
		double result;

		if (value < p1 || value > p4) {
			result = 0;
		} else if (value > p2 && value < p3) {
			result = 1.0;
		} else if (value < p4) {
			result = (p2 - p1) / 100.0 * (value - p1);
		} else {
			result = (p4 - p3) / 100.0 * (value - p3);
		}
		return result;
	}

	private static double calculateWeight(double value, int idx) {
		double p1 = idx > 0 ? steps[idx - 1] : 0;
		double p2 = steps[idx] - 0.3;
		double p3 = steps[idx] + 0.3;
		double p4 = idx < steps.length - 1 ? steps[idx + 1] : Double.MAX_VALUE;
		return fuzzy(p1, p2, p3, p4, value);
	}

	private static final int[] steps = {
			1,
			2,
			5
	};

	public static boolean parseBoolean(String str) {
		boolean rc = false;
		if (!StringUtil.isNullOrEmpty(str)) {
			if (str.equalsIgnoreCase("true")) {
				rc = true;
			} else if (str.equals("1")) {
				rc = true;
			} else if (str.equals("2")) {
				rc = true;
			}
		}
		return rc;
	}

	public static double calculatePercent(long value, long base) {
		double result = 0.0;
		if (value != 0 && base != 0) {
			result = (value / (double) base) * 100.0;
		}
		return result;
	}

	public static int doubleToInt(double value) throws NumberFormatException {
		if (value > Integer.MAX_VALUE || value < Integer.MIN_VALUE) {
			throw new NumberFormatException("value out of range " + value);
		}
		int intValue = (int) Math.round(value);
		return intValue;
	}

	public static int[] parseIntValues(String strValues) {
		int[] values = null;
		if (!StringUtil.isNullOrEmpty(strValues)) {
			String[] results = strValues.trim().split("\\|");
			if (results != null && results.length > 0) {
				values = new int[results.length];
				for (int i = 0; i < results.length; i++) {
					values[i] = parseIntNumber(results[i], -1);
				}
			}
		}
		return values;
	}

	public static double[] parseDoubleValues(String strValues) {
		double[] values = null;
		if (!StringUtil.isNullOrEmpty(strValues)) {
			String[] results = strValues.trim().split("\\|");
			if (results != null && results.length > 0) {
				values = new double[results.length];
				for (int i = 0; i < results.length; i++) {
					values[i] = parseDoubleNumber(results[i], -1);
				}
			}
		}
		return values;
	}

}
