package gossip.rule;

import gossip.lib.util.StringUtil;
import gossip.view.keyboard.input.InputItemId;

public class MaxStringLength extends InputRule {

	public static final String RULE_NAME = "MAX_STRING_LENGTH";

	private int maxLength;

	public MaxStringLength(InputItemId alertId, int maxLength) {
		super(alertId);
		this.maxLength = maxLength;
	}

	@Override
	public boolean verify(String inputStr) {
		if (!StringUtil.isNullOrEmpty(inputStr)) {
			if (inputStr.length() <= maxLength) {
				return true;
			}
		} else {
			return true; // no problem with an empty string
		}
		return false;
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
		if (!(obj instanceof MaxStringLength))
			return false;
		MaxStringLength other = (MaxStringLength) obj;
		if (maxLength != other.maxLength)
			return false;
		return true;
	}

}
