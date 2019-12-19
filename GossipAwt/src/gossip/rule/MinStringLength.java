package gossip.rule;

import gossip.lib.util.StringUtil;
import gossip.view.keyboard.input.InputItemId;

public class MinStringLength extends InputRule {

	public static final String RULE_NAME = "MIN_STRING_LENGTH";

	private int minLength;

	public MinStringLength(InputItemId alertId, int minLength) {
		super(alertId);
		this.minLength = minLength;
	}

	@Override
	public boolean verify(String inputStr) {
		if (minLength == 0) {
			return true;
		}
		if (!StringUtil.isNullOrEmpty(inputStr)) {
			if (inputStr.length() >= minLength) {
				return true;
			}
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
		result = prime * result + minLength;
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
		if (!(obj instanceof MinStringLength))
			return false;
		MinStringLength other = (MinStringLength) obj;
		if (minLength != other.minLength)
			return false;
		return true;
	}

}
