package gossip.rule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gossip.inputelement.InputItemId;
import gossip.lib.util.StringUtil;


public class RegExRule extends InputRule {

	public static final String RULE_NAME = "REG_EX_RULE";

	private Pattern pattern;

	RegExRule(InputItemId alertId) {
		super(alertId);
	}

	public RegExRule(InputItemId alertId, String expressionString) {
		super(alertId);
		initExpression(expressionString);
	}

	protected void initExpression(String expressionString) {
		if (!StringUtil.isNullOrEmpty(expressionString)) {
			pattern = Pattern.compile(expressionString);
		}
	}

	@Override
	public boolean verify(String inputStr) {
		if (!StringUtil.isNullOrEmpty(inputStr) && pattern != null) {
			Matcher matcher = pattern.matcher(inputStr);
			return matcher.find();
		} else {
			return false;
		}
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
