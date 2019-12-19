/**
 * 
 */
package gossip.view.keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gossip.lib.util.StringUtil;
import gossip.util.ObjectUtil;
import gossip.view.keyboard.input.InputItemId;
import gossip.view.keyboard.key.MyKey;

public class KeyBoardDefinition {

	private final String identifier;

	/**
	 * Password is displayed as dots. Buttons are bocked
	 */
	private boolean isPassword = false;

	/**
	 * Overlay is displayed on the Enter/Commit button
	 */
	private String overlayName;

	/**
	 * Defines the text for the commit button
	 */
	private InputItemId itemId;

	private final Locale locale;

	/**
	 * The keys to be displayed
	 */
	private List<List<MyKey>> keyList = new ArrayList<>();

	public KeyBoardDefinition(final String identifier, final Locale locale) {
		super();
		this.identifier = identifier;
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public boolean compareTo(KeyBoardDefinition def) {
		if (def == null) {
			return false;
		}
		if (!StringUtil.compare(identifier, def.identifier)) {
			return false;
		}
		if (!StringUtil.compare(overlayName, def.overlayName)) {
			return false;
		}
		if (!ObjectUtil.compare(locale, def.locale)) {
			return false;
		}
		if (!ObjectUtil.compare(itemId, def.itemId)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyBoardDefinition other = (KeyBoardDefinition) obj;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (isPassword != other.isPassword)
			return false;
		if (overlayName == null) {
			if (other.overlayName != null)
				return false;
		} else if (!overlayName.equals(other.overlayName))
			return false;
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public InputItemId getItemId() {
		return itemId;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return the keyList
	 */
	public List<List<MyKey>> getKeyList() {
		return keyList;
	}

	/**
	 * @return the overlayName
	 */
	public String getOverlayName() {
		return overlayName;
	}

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
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + (isPassword ? 1231 : 1237);
		result = prime * result + ((overlayName == null) ? 0 : overlayName.hashCode());
		return result;
	}

	/**
	 * @return the isPassword
	 */
	public boolean isPassword() {
		return isPassword;
	}

	/**
	 * @param itemId the id to set
	 */
	public void setInputItem(final InputItemId itemId) {
		this.itemId = itemId;
	}

	/**
	 * @param keyList the keyList to set
	 */
	public void setKeyList(final List<List<MyKey>> keyList) {
		this.keyList = keyList;
	}

	/**
	 * @param overlayName the overlayName to set
	 */
	public void setOverlayName(final String overlayName) {
		this.overlayName = overlayName;
	}

	/**
	 * @param isPassword the isPassword to set
	 */
	public void setPassword(final boolean isPassword) {
		this.isPassword = isPassword;
	}

	@Override
	public String toString() {
		return "KeyBoardDefinition [identifier=" + identifier + ", isPassword=" + isPassword + ", overlayName=" + overlayName + ", itemId=" + itemId + ", locale=" + locale
				+ ", keyList=" + keyList + "]";
	}

}