package gossip.rule;

import gossip.lib.util.StringUtil;
import gossip.view.keyboard.input.InputItemId;

public class MinCharType extends InputRule {

	public static final String RULE_NAME = "MIN_CHAR_TYPE";

	private int minLength;
	private int type;

	public static final int TYPE_UPPER_CASE = 1;
	public static final int TYPE_LOWER_CASE = 2;
	public static final int TYPE_DIGIT = 3;

	public MinCharType(InputItemId alertId, int minLength, int type) {
		super(alertId);
		this.minLength = minLength;
		this.type = type;
	}

	@Override
	public boolean verify(String inputStr) {
		int len = 0;
		if (!StringUtil.isNullOrEmpty(inputStr)) {
			switch (type) {
				case 1:
					len = getNumUpercase(inputStr);
					break;
				case 2:
					len = getNumLowercase(inputStr);
					break;
				case 3:
					len = getNumDigits(inputStr);
					break;

				default:
					break;
			}

		}
		return len >= minLength;
	}

	private int getNumLowercase(String str) {
		int i = 0;
		char[] dest = new char[str.length()];

		str.getChars(0, str.length(), dest, 0);

		for (int j = 0; j < dest.length; j++) {
			if (Character.isLowerCase(dest[j])) {
				i++;
			}
		}

		return i;
	}

	private int getNumUpercase(String str) {
		int i = 0;
		char[] dest = new char[str.length()];

		str.getChars(0, str.length(), dest, 0);

		for (int j = 0; j < dest.length; j++) {
			if (Character.isUpperCase(dest[j])) {
				i++;
			}
		}

		return i;
	}

	private int getNumDigits(String str) {
		int i = 0;
		char[] dest = new char[str.length()];

		str.getChars(0, str.length(), dest, 0);

		for (int j = 0; j < dest.length; j++) {
			if (Character.isDigit(dest[j])) {
				i++;
			}
		}

		return i;
	}

	@Override
	public String getName() {
		return RULE_NAME;
	}

	@Override
	public SEVERITY getSeverity() {
		return SEVERITY.BLOCKING;
	}

}
