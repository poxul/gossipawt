package gossip.rule;

import java.util.ArrayList;
import java.util.List;

import gossip.lib.panel.txt.StringFormatterUtil;
import gossip.manager.LanguageManager;
import gossip.view.keyboard.input.InputItemId;

public abstract class InputRule {

	public enum SEVERITY {
		OK, // NO_UCD (unused code)
		WARNING, // NO_UCD (unused code)
		BLOCKING
	}

	private final InputItemId itemId;

	private final List<Object> varList = new ArrayList<>();

	public void addVar(Object var) {
		varList.add(var);
	}

	public InputRule(InputItemId alertId) {
		this.itemId = alertId;
	}

	public List<InputItemId> getInputItemIds() {
		List<InputItemId> ids = new ArrayList<>();
		ids.add(itemId);
		for (Object var : varList) {
			if (var instanceof InputItemId) {
				ids.add((InputItemId) var);
			}
		}
		return ids;
	}

	public String getAlertString() {
		return StringFormatterUtil.getFormatedText(LanguageManager.getLocaleText(itemId), varList, false);
	}

	public abstract String getName();

	/**
	 * 
	 * @param inputStr
	 * @return return true if this rue is fulfilled (no problem)
	 */
	public abstract boolean verify(String inputStr);

	/**
	 * 
	 * @return Returns the severitiy if this ruule is not fulfilled
	 */
	public abstract SEVERITY getSeverity();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((varList == null) ? 0 : varList.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof InputRule))
			return false;
		InputRule other = (InputRule) obj;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (varList == null) {
			if (other.varList != null)
				return false;
		} else if (!varList.equals(other.varList))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("InputRule [ItemId=");
		builder.append(itemId);
		builder.append(", varList=");
		builder.append(varList != null ? varList.subList(0, Math.min(varList.size(), maxLen)) : null);
		builder.append("]");
		return builder.toString();
	}

}
