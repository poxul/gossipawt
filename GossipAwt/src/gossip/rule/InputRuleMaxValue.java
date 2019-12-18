package gossip.rule;

import gossip.keyboard.input.InputItemId;
import gossip.lib.util.StringUtil;

public class InputRuleMaxValue extends InputRuleNumber<Integer> {

	public final static String RULE_NAME = "MAX_VALUE";

	public InputRuleMaxValue(InputItemId alertId, Integer maxValue) {
		super(alertId, maxValue);
	}

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
		return value == null ? false : value <= getReferenceValue();
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
