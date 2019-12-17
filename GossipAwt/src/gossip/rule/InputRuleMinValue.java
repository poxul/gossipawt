package gossip.rule;

import gossip.inputelement.InputItemId;
import gossip.lib.util.StringUtil;

public class InputRuleMinValue extends InputRuleNumber<Integer> {

	public static final String RULE_NAME = "MIN_VALUE";

	public InputRuleMinValue(InputItemId alertId, Integer minValue) {
		super(alertId, minValue);
	}

	@Override
	public Integer getValue(String inputStr) {
		Long value = null;
		if (!StringUtil.isNullOrEmpty(inputStr)) {
			try {
				value = Long.parseLong(inputStr);
			} catch (NumberFormatException e) {
				try {
					// HEX format ?
					value = Long.parseLong(inputStr, 16);
				} catch (NumberFormatException e1) {
					return null;
				}
			}
		} else {
			value = Long.valueOf(0); // Ein leeres Feld wird als gut betrachtet
		}

		Integer result;
		if (value > Integer.MAX_VALUE) {
			result = Integer.MAX_VALUE;
		} else if (value < Integer.MIN_VALUE) {
			result = Integer.MIN_VALUE;
		} else {
			result = value.intValue();
		}
		return result;
	}

	@Override
	public boolean verify(String inputStr) {
		Integer value = getValue(inputStr);
		return value == null ? false : value >= getReferenceValue();
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
