package gossip.rule;

import gossip.inputelement.InputItemId;
import gossip.lib.util.StringUtil;

public class InputRuleUMinValue extends InputRuleNumber<Long> {

	public static final String RULE_NAME = "MIN_VALUE";

	public InputRuleUMinValue(InputItemId alertId, Long minValue) {
		super(alertId, minValue);
	}

	@Override
	public Long getValue(String inputStr) {
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

		Long result;
		if (value > Long.MAX_VALUE) {
			result = Long.MAX_VALUE;
		} else if (value < Long.MIN_VALUE) {
			result = Long.MIN_VALUE;
		} else {
			result = value;
		}
		return result;
	}

	@Override
	public boolean verify(String inputStr) {
		Long value = getValue(inputStr);
		return (value == null ? false : value >= getReferenceValue());
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
