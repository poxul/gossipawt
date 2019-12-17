/**
 * 
 */
package gossip.lib.panel.txt;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.SwingConstants;

import gossip.lib.panel.txt.StringFormatterUtil.Alignment;
import gossip.lib.util.StringUtil;
import gossip.util.ObjectUtil;

public class GraphicsTextRenderer implements ButtonTextUiInterface {

	private static final int BACKGROUND_ARC = 3;

	/**
	 * Translate SwingConstants to Alignment
	 * 
	 * @param alingn
	 * @return
	 */
	public static Alignment translateAlignment(final int alingn) {
		switch (alingn) {
		case SwingConstants.CENTER:
			return Alignment.CENTER;
		case SwingConstants.LEFT:
		case SwingConstants.LEADING:
			return Alignment.LEFT;
		case SwingConstants.RIGHT:
		case SwingConstants.TRAILING:
			return Alignment.RIGHT;
		default:
			return Alignment.LEFT;
		}
	}

	private Font font;
	private int numLines = 2;
	private String text;
	private boolean isChanged;
	private Rectangle targetRect;
	private Point fixPositionStartPoint;
	private GraphicText[] texts;
	private StringFormatterUtil.Alignment alignment = Alignment.CENTER;
	private boolean isDrawTargetRect = false; // DEBUG !
	private Color textBackgroundColor = Color.WHITE;

	public GraphicsTextRenderer(final String text, final Font bigFont) {
		targetRect = new Rectangle();
		this.text = text;
		font = bigFont;
		isChanged = true;
	}

	/**
	 * @return the fixPositionStartPoint
	 */
	public Point getFixPositionStartPoint() {
		return fixPositionStartPoint;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	public int getLineHeight() {
		int height = 0;
		if (font != null) {
			height = font.getSize();
		}
		height += 2; // gap
		return height * numLines;
	}

	private int usedLines = 1;

	public int getUsedLines() {
		return usedLines;
	}

	/**
	 * @return the numLines
	 */
	public int getNumLines() {
		return numLines;
	}

	/**
	 * @return the targetRect
	 */
	public Rectangle getTargetRect() {
		return targetRect;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	@Override
	public void setTextAlignment(final Alignment alignment) {
		this.alignment = alignment;
	}

	public void setDrawTargetRect(final boolean isDrawTargetRect) {
		this.isDrawTargetRect = isDrawTargetRect;
	}

	/**
	 * @param fixPositionStartPoint the fixPositionStartPoint to set
	 */
	public void setFixPositionStartPoint(final Point fixPositionStartPoint) {
		this.fixPositionStartPoint = fixPositionStartPoint;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(final Font newFont) {
		if (newFont != null && !newFont.equals(font)) {
			font = newFont;
			isChanged = true;
		}
	}

	/**
	 * @param numLines the numLines to set
	 */
	public void setNumLines(final int numLines) {
		if (this.numLines != numLines) {
			this.numLines = numLines;
			isChanged = true;
		}
	}

	/**
	 * @param targetRect the targetRect to set
	 */
	public void setTargetRect(final Rectangle targetRect) {
		if (!ObjectUtil.compare(targetRect, this.targetRect)) {
			this.targetRect = targetRect;
			isChanged = true;
		}
	}

	/**
	 * @param text the text to set
	 */
	public void setText(final String text) {
		if (!StringUtil.compare(this.text, text)) {
			this.text = text;
			isChanged = true;
		}
	}

	public void setTextBackgroundColor(final Color textBackgroundColor) {
		this.textBackgroundColor = textBackgroundColor;
	}

	public void update(final Graphics2D g2d, final int x, final int y, final int width, final int height) {
		if (g2d != null) {
			if (targetRect.width != width || targetRect.height != height || targetRect.x != x || targetRect.y != y) {
				targetRect.height = height;
				targetRect.width = width;
				targetRect.x = x;
				targetRect.y = y;
				isChanged = true;
			}
			if (isChanged) {
				if (!StringUtil.isNullOrEmpty(text) && numLines > 0) {
					int lh = targetRect.height / ((usedLines < 1) ? 1 : usedLines);

					/*
					 * Püfung ob die gewünschte Anzahl an Zeilen mit der gewünschten Textgröße
					 * überhaupt auf den Bereich passen, der zur Verfügung steht. Wenn das nicht der
					 * Fall ist wird die Textgröße um 2 PX verringert!
					 */
					Font usedFont;
					FontMetrics fm = g2d.getFontMetrics(font);
					int diff = lh - fm.getAscent();
					if (diff < -2) {
						usedFont = font.deriveFont(font.getSize() - 2f);
					} else {
						usedFont = font;
					}
					g2d.setFont(usedFont);

					if (fixPositionStartPoint == null) {
						texts = StringFormatterUtil.formatsGraphics(g2d, text, targetRect, usedFont, numLines, alignment);
					} else {
						texts = StringFormatterUtil.formatsGraphicsFixedPosition(g2d, text, fixPositionStartPoint, usedFont, numLines);
					}
					usedLines = calcUsedLines();
					isChanged = false;
				} else {
					texts = null;
				}
			}
			if (isDrawTargetRect) {
				Color oldColor = g2d.getColor();
				g2d.setColor(textBackgroundColor);
				g2d.fillRoundRect(targetRect.x, targetRect.y, targetRect.width, targetRect.height, BACKGROUND_ARC, BACKGROUND_ARC);
				g2d.setColor(Color.BLACK);
				g2d.drawRoundRect(targetRect.x, targetRect.y, targetRect.width, targetRect.height, BACKGROUND_ARC, BACKGROUND_ARC);
				g2d.setColor(oldColor);
			}
			if (texts != null) {
				StringFormatterUtil.drawGraphicTexts(g2d, texts);
			}

		}
	}

	private int calcUsedLines() {
		int count = 0;
		for (GraphicText graphicText : texts) {
			if (graphicText != null && !graphicText.isEmpty()) {
				count++;
			}
		}
		return count;
	}

	@Override
	public void dispose() {
		// NOP
	}

}