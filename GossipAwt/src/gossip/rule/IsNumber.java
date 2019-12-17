package gossip.rule;

import gossip.inputelement.InputItemId;
import gossip.lib.util.StringUtil;

public class IsNumber extends InputRule {

	public static final String RULE_NAME = "IS_NUMBER";

	public IsNumber(InputItemId alertId) {
		super(alertId);
	}

	@Override
	public boolean verify(String inputStr) {
		if (inputStr != null && inputStr.length() == 1) {
			if (StringUtil.compare(inputStr, "-") || StringUtil.compare(inputStr, "+") || StringUtil.compare(inputStr, ".")) {
				return true; // allow Minus, Plus and Dot at start of input
			}
		}

		Integer value = null;
		if (!StringUtil.isNullOrEmpty(inputStr)) {
			try {
				value = Integer.parseInt(inputStr);
			} catch (NumberFormatException e) {
				try {
					// HEX format ?
					value = Integer.parseInt(inputStr, 16);
				} catch (NumberFormatException e1) {
					return false;
				}
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
