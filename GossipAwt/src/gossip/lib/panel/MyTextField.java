package gossip.lib.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

import gossip.config.ColorConstants;
import gossip.lib.panel.txt.GraphicsTextRenderer;
import gossip.lib.panel.txt.StringFormatterUtil;
import gossip.lib.util.ObjectUtil;
import gossip.lib.util.StringUtil;
import gossip.util.DrawingUtil;

public class MyTextField extends AbstractMyTextField {

	/**
	 *
	 */
	private static final long serialVersionUID = -8941874147895306288L;

	private String text;
	private GraphicsTextRenderer textRenderer;

	private static final int TEXT_GAP_X = 5;
	private static final int TEXT_GAP_Y = 2;

	private Insets insets = new Insets(TEXT_GAP_Y, TEXT_GAP_X, TEXT_GAP_Y, TEXT_GAP_X);

	private boolean isReCalcSize = true;

	private Dimension calcDimension;

	private boolean isDrawHorizontalLine = false;

	private Paint paint;

	/**
	 * Check width of the text and use smaller font on string width > panel width
	 */
	private boolean isCheckFontWidth = false;

	private String hint;

	private Color hintColor;

	public MyTextField() {
		super();
		init();
	}

	public MyTextField(final String text, final int width, final int height) {
		super();
		this.text = text;
		setPreferredSize(new Dimension(width, height));
		init();
	}

	private void buildView() {
		setOpaque(false);
	}

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		desiredFont = font;
		if (!ObjectUtil.compare(font, getFont())) {
			isReCalcSize = true;
			checkFont();
			super.revalidate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public Dimension getMinimumSize() {
		if (isMinimumSizeSet()) {
			return super.getMinimumSize();
		}
		if (isReCalcSize || calcDimension == null) {
			String str = text;
			if (textRenderer != null && !StringUtil.isNullOrEmpty(str)) {
				Font font = textRenderer.getFont();
				if (font != null) {
					FontMetrics fm = getFontMetrics(font);
					Rectangle2D bounds = fm.getStringBounds(str, getGraphics());
					Insets borderInsets = getInsets();
					int lines;
					if (getWidth() > 0) {
						lines = getUsedLines();
					} else {
						lines = getNumLines();
					}
					int w = bounds.getBounds().width + insets.left + insets.right + borderInsets.left + borderInsets.right + 2 * StringFormatterUtil.TEXT_GAB_X + 10;
					int h = bounds.getBounds().height * lines + insets.top + insets.bottom + borderInsets.top + borderInsets.bottom + 2 * StringFormatterUtil.TEXT_GAB_Y + 2;
					if (h > 0 && w > 0) {
						Dimension dim = new Dimension(w, h);
						calcDimension = dim;
						isReCalcSize = false;
					}
				}
			}
		}
		return calcDimension == null ? super.getMinimumSize() : calcDimension;
	}

	public int getUsedLines() {
		return textRenderer == null ? 1 : textRenderer.getUsedLines();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		isReCalcSize = (getWidth() != width) || (getHeight() != height);
		super.setBounds(x, y, width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public Dimension getPreferredSize() {
		if (isPreferredSizeSet())
			return super.getPreferredSize();
		return getMinimumSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public String getText() {
		return text;
	}

	private void init() {
		textRenderer = new GraphicsTextRenderer(null, getFont());
		setForeground(ColorConstants.DEFAULT_TEXT_COLOR);
		buildView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Graphics)
	 */
	@Override
	public void paintComponent(final Graphics g) {
		Graphics2D g2d = DrawingUtil.getGraphics2d(g);
		try {
			super.paintComponent(g2d);
			text = getText();
			if ((text == null || text.isEmpty()) && hint != null && !hint.isEmpty()) {
				paintText(g2d, hint, getFont(), hintColor);
			} else {
				if (paint != null) {
					paintText(g2d, getText(), getFont(), paint);
				} else {
					paintText(g2d, getText(), getFont(), getForeground());
				}
			}

			if (isDrawHorizontalLine) {
				int y = getHeight() / 2;
				g2d.drawLine(0, y, getWidth(), y);
			}

		} finally {
			g2d.dispose();
		}
	}

	public void setHint(String hint, Color hintColor) {
		this.hint = hint;
		this.hintColor = hintColor;
		repaint();
	}

	private void paintText(final Graphics2D g2d, final String text, final Font f) {
		if (textRenderer != null) {
			textRenderer.setText(text);
			textRenderer.setFont(f);
			Insets borderInsets = getInsets();

			int left = insets.left + borderInsets.left;
			int right = insets.right + borderInsets.right;
			int top = insets.top + borderInsets.top;
			int bottom = insets.bottom + borderInsets.bottom;

			textRenderer.update(g2d, left, top, getWidth() - (left + right), getHeight() - (top + bottom));
		}
	}

	private void paintText(final Graphics2D g2d, final String text, final Font f, final Color tColor) {
		if (StringUtil.isNullOrEmpty(text))
			return;
		g2d.setColor(tColor);
		paintText(g2d, text, f);
	}

	private void paintText(final Graphics2D g2d, final String text, final Font f, final Paint p) {
		if (StringUtil.isNullOrEmpty(text))
			return;
		g2d.setPaint(p);
		paintText(g2d, text, f);
	}

	public void setDrawHorizontalLine(final boolean isDrawHorizontalLine) {
		this.isDrawHorizontalLine = isDrawHorizontalLine;
	}

	public boolean isDrawHorizontalLine() {
		return isDrawHorizontalLine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 */
	@Override
	public void setMargin(final Insets insets) {
		if (!ObjectUtil.compare(this.insets, insets)) {
			this.insets = insets;
			isReCalcSize = true;
		}
	}

	public void setNumLines(final int numLines) {
		textRenderer.setNumLines(numLines);
		if (textRenderer.getNumLines() != numLines) {
			isReCalcSize = true;
		}
	}

	public int getNumLines() {
		return textRenderer.getNumLines();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 */
	@Override
	public void setPaint(final Paint paint) {
		this.paint = paint;
	}

	public void setCheckFontWidth(boolean isCheckFontWidth) {
		this.isCheckFontWidth = isCheckFontWidth;
	}

	private Font desiredFont;

	/**
	 * 
	 * @return
	 */
	private boolean checkFont() {
		boolean rc = false;
		if (isCheckFontWidth) {
			if (getNumLines() == 1 && textRenderer != null) {
				if (desiredFont == null) {
					desiredFont = getFont();
				}
				int pw = textRenderer.getTargetRect().width;
				if (pw > 0) {
					Font font = getFont();
					String infoText = getText();
					int tw = getFontMetrics(desiredFont).stringWidth(infoText) + (2 * StringFormatterUtil.TEXT_GAB_X);
					if (pw < tw) {
						super.setFont(desiredFont.deriveFont(desiredFont.getSize() - 4f));
						rc = true;
					} else {
						if (!desiredFont.equals(font)) {
							super.setFont(desiredFont);
						}
					}
				}
			}
		}
		return rc;
	}

	public void setText(final String infoText, final boolean resize) {
		if (!StringUtil.compare(text, infoText)) {
			text = infoText;
			if (resize) {
				isReCalcSize = true;
				checkFont();
				super.revalidate();
			}
			super.repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 */
	@Override
	public void setText(final String infoText) {
		setText(infoText, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void setTextAlignment(final StringFormatterUtil.Alignment alignment) {
		textRenderer.setTextAlignment(alignment);
	}

}
