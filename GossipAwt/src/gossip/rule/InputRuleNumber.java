package gossip.rule;

import gossip.keyboard.input.InputItemId;

public abstract class InputRuleNumber<T extends Number> extends InputRule {

	private final T referenceValue;

	public InputRuleNumber(InputItemId alertId, T referenceValue) {
		super(alertId);
		this.referenceValue = referenceValue;
	}

	public T getReferenceValue() {
		return referenceValue;
	}

	public abstract T getValue(String inputStr);

}
