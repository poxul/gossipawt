package gossip.rule;

import gossip.lib.util.StringUtil;
import gossip.view.keyboard.input.InputItemId;

public class InputRuleDoubleMinValue extends InputRuleNumber<Double> {

	public static final String RULE_NAME = "MIN_VALUE_DOUBLE";

	public InputRuleDoubleMinValue(InputItemId alertId, Double minValue) {
		super(alertId, minValue);
	}

	@Override
	public Double getValue(String inputStr) {
		Double value = null;
		if (!StringUtil.isNullOrEmpty(inputStr)) {
			try {
				value = Double.parseDouble(inputStr);
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return value;
	}

	@Override
	public boolean verify(String inputStr) {
		Double value = getValue(inputStr);
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
