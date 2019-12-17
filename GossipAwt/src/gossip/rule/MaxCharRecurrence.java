package gossip.rule;

import gossip.inputelement.InputItemId;

public class MaxCharRecurrence extends RegExRule {

	private static final String RULE_NAME = "MAX_CHAR_RECURRENCE";

	private int maxLength;
	private static final String REPEAT_CHAR_REGEX = "([^\\x00-\\x1F])\\1{%d}";
	private boolean active = false;

	public MaxCharRecurrence(InputItemId alertId, int maxLength) {
		super(alertId);
		this.maxLength = maxLength;
		if (maxLength > 1) {
			active = true;
			initExpression(String.format(REPEAT_CHAR_REGEX, maxLength));
		}
	}

	@Override
	public boolean verify(String inputStr) {
		if (active) {
			// Just invert the result
			return !super.verify(inputStr);
		} else {
			return true;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxLength;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof MaxCharRecurrence))
			return false;
		MaxCharRecurrence other = (MaxCharRecurrence) obj;
		if (maxLength != other.maxLength)
			return false;
		return true;
	}

}
