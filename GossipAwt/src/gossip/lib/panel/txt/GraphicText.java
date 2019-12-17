/**
 * 
 */
package gossip.lib.panel.txt;

import java.awt.Font;
import java.awt.Rectangle;

import gossip.lib.util.StringUtil;


public class GraphicText {

	private String text;
	private Font font;
	private Rectangle rect;

	public GraphicText(String text) {
		this.text = text;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font
	 *            the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * @return the startPoint
	 */
	public Rectangle getRectangle() {
		return rect;
	}

	/**
	 * @param startPoint
	 *            the startPoint to set
	 */
	public void setRectangle(Rectangle rect) {
		this.rect = rect;
	}

	public void setRectangle(int x, int y, int width, int height) {
		this.rect = new Rectangle(x, y, width, height);
	}

	public boolean isEmpty() {
		return StringUtil.isNullOrEmpty(text);
	}

}