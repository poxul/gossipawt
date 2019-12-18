package gossip.rule;

import gossip.keyboard.input.InputItemId;
import gossip.lib.util.StringUtil;

public class IsNotEmpty extends InputRule {

	public static final String RULE_NAME = "IS_NOT_EMPTY";

	public IsNotEmpty(InputItemId alertId) {
		super(alertId);
	}

	@Override
	public boolean verify(String inputStr) {
		return !StringUtil.isNullOrEmpty(inputStr);
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
