package gossip.rule;

import gossip.inputelement.InputItemId;
import gossip.lib.util.StringUtil;

public class InputRuleDoubleMaxValue extends InputRuleNumber<Double> {

	public final static String RULE_NAME = "MAX_VALUE_DOUBLE";

	public InputRuleDoubleMaxValue(InputItemId alertId, Double maxValue) {
		super(alertId, maxValue);
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
