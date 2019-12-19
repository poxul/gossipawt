package gossip.rule;

import gossip.lib.util.StringUtil;
import gossip.view.keyboard.input.InputItemId;

public class IsDoubleNumber extends InputRule {

	public static final String RULE_NAME = "IS_NUMBER";

	public IsDoubleNumber(InputItemId alertId) {
		super(alertId);
	}

	@Override
	public boolean verify(String inputStr) {
		if (inputStr != null && inputStr.length() == 1) {
			if (StringUtil.compare(inputStr, "-") || StringUtil.compare(inputStr, "+") || StringUtil.compare(inputStr, ".")) {
				return true; // allow Minus, Plus and Dot at start of input
			}
		}

		Double value = null;
		if (!StringUtil.isNullOrEmpty(inputStr)) {
			try {
				value = Double.parseDouble(inputStr);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return true;
		}
		return value != null;
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
