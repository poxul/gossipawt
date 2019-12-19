/**
 * 
 */
package gossip.view.keyboard.key;

import java.awt.Color;

import gossip.lib.util.StringUtil;

public class MyKey {

	private String keyText;
	private String shiftKeyText;
	private String altKeyText;

	private String overlayName;

	private int size;

	private boolean isLocked;

	private Color color;

	private Color drawColor;

	public MyKey(MyKey src) {
		this(src.keyText, src.shiftKeyText, src.altKeyText, src.size, src.isLocked);
	}

	public MyKey(String keyText, String shiftKeyText, String altKeyText) {
		this(keyText, shiftKeyText, altKeyText, 0, false);
	}

	public MyKey(String keyText, String shiftKeyText, String altKeyText, int size, boolean isLocked) {
		super();
		this.keyText = keyText;
		this.shiftKeyText = shiftKeyText;
		this.altKeyText = altKeyText;
		this.size = size;
		this.isLocked = isLocked;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public int getSize() {
		return size;
	}

	/**
	 * @return the keyText
	 */
	public String getKeyText() {
		return keyText;
	}

	/**
	 * @param keyText the keyText to set
	 */
	public void setKeyText(String keyText) {
		this.keyText = keyText;
	}

	/**
	 * @return the shiftKeyText
	 */
	public String getShiftKeyText() {
		return shiftKeyText;
	}

	/**
	 * @param shiftKeyText the shiftKeyText to set
	 */
	public void setShiftKeyText(String shiftKeyText) {
		this.shiftKeyText = shiftKeyText;
	}

	/**
	 * @return the altKeyText
	 */
	public String getAltKeyText() {
		return altKeyText;
	}

	/**
	 * @param altKeyText the altKeyText to set
	 */
	public void setAltKeyText(String altKeyText) {
		this.altKeyText = altKeyText;
	}

	/**
	 * @return the overlayName
	 */
	public String getOverlayName() {
		return overlayName;
	}

	/**
	 * @param overlayName the overlayName to set
	 */
	public void setOverlayName(String overlayName) {
		this.overlayName = overlayName;
	}

	/**
	 * Compare text, alt-text and shift-text to str.
	 * 
	 * @param str String to compare.
	 * @return True if one of the strings for the key is equal to the input string
	 */
	public boolean containsString(String str) {
		boolean result = false;
		if (StringUtil.compare(str, keyText) || StringUtil.compare(str, altKeyText) || StringUtil.compare(str, shiftKeyText)) {
			result = true;
		}
		return result;
	}

	@Override
	public String toString() {
		return "MyKey [keyText=" + keyText + ", shiftKeyText=" + shiftKeyText + ", altKeyText=" + altKeyText + ", overlayName=" + overlayName + ", size=" + size + ", isLocked="
				+ isLocked + "]";
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setDrawColor(Color drawColor) {
		this.drawColor = drawColor;
	}

	public Color getDrawColor() {
		return drawColor;
	}

}
