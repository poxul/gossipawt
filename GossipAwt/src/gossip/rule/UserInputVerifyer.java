package gossip.rule;

import java.util.ArrayList;
import java.util.List;

import gossip.keyboard.input.InputItemId;
import gossip.keyboard.input.MultiItemHolder;

public class UserInputVerifyer extends MultiItemHolder {

	private final List<InputRule> ruleList = new ArrayList<>();

	public UserInputVerifyer() {
		super();
	}

	public UserInputVerifyer(InputRule... rules) {
		super();
		for (int i = 0; i < rules.length; i++) {
			InputRule r = rules[i];
			if (r != null) {
				addRule(r);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ruleList == null) ? 0 : ruleList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInputVerifyer other = (UserInputVerifyer) obj;
		if (ruleList == null) {
			if (other.ruleList != null)
				return false;
		} else if (!ruleList.equals(other.ruleList))
			return false;
		return true;
	}

	public void addRule(InputRule rule) {
		synchronized (ruleList) {
			ruleList.add(rule);
			List<InputItemId> ids = rule.getInputItemIds();
			for (InputItemId id : ids) {
				addItem(id);
			}
		}
	}

	public InputRule verify(String inputStr) {
		InputRule rc = null;
		synchronized (ruleList) {
			for (InputRule rule : ruleList) {
				if (!rule.verify(inputStr)) {
					rc = rule;
					break;
				}
			}
		}
		return rc;
	}

}
