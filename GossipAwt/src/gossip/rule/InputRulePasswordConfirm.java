package gossip.rule;

import gossip.keyboard.input.InputItemId;
import gossip.lib.util.StringUtil;

public class InputRulePasswordConfirm extends InputRule {

	public static final String RULE_NAME = "PASSWORD_CONFIRM";

	private String tempPassword;

	public InputRulePasswordConfirm(InputItemId alertId) {
		super(alertId);
	}

	@Override
	public SEVERITY getSeverity() {
		return SEVERITY.BLOCKING;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	@Override
	public boolean verify(String inputStr) {
		return StringUtil.compare(inputStr, tempPassword);
	}

	@Override
	public String getName() {
		return RULE_NAME;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((tempPassword == null) ? 0 : tempPassword.hashCode());
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
		if (!(obj instanceof InputRulePasswordConfirm))
			return false;
		InputRulePasswordConfirm other = (InputRulePasswordConfirm) obj;
		if (tempPassword == null) {
			if (other.tempPassword != null)
				return false;
		} else if (!tempPassword.equals(other.tempPassword))
			return false;
		return true;
	}

}