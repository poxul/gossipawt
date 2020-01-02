/**
 *
 */
package gossip.lib.panel.scroll;

import java.awt.Adjustable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

import gossip.config.ColorConstants;
import gossip.util.DrawingUtil;

public class MyScrollBarUIFlat extends BasicScrollBarUI {

	private static final Color DARK_COLOR = ColorConstants.BACKGROUND_COLOR;
	private static final Color LIGHT_COLOR = ColorConstants.INFO_BACKGROUND_1;
	private static final Color FRAME_COLOR_THUMP = ColorConstants.BUTTON_COLOR_THUMP;

	private final Dimension minThumbSize;

	public MyScrollBarUIFlat() {
		this(new Dimension(10, 3));
	}

	public MyScrollBarUIFlat(final Dimension minThumbSize) {
		super();
		this.minThumbSize = minThumbSize;
	}

	private JButton createButton(final int orientation) {
		JButton b = new BasicArrowButton(orientation, ColorConstants.BUTTON_COLOR_DEFAULT, DARK_COLOR, LIGHT_COLOR, LIGHT_COLOR) {

			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(40, 40);
			}

		};
		return b;
	}

	@Override
	protected JButton createDecreaseButton(final int orientation) {
		return createButton(orientation);
	}

	@Override
	protected JButton createIncreaseButton(final int orientation) {
		return createButton(orientation);
	}

	private void drawHighlight(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(LIGHT_COLOR);
		g2d.fillRect(x, y, w, h);
		g2d.setStroke(oldStroke);
	}

	@Override
	protected Dimension getMinimumThumbSize() {
		return minThumbSize;
	}

	@Override
	public void paint(final Graphics g, final JComponent c) {
		Graphics2D g2d = DrawingUtil.getGraphics2d(g);
		try {
			super.paint(g2d, c);
		} finally {
			g2d.dispose();
		}
	}

	@Override
	protected void paintDecreaseHighlight(final Graphics g) {
		Rectangle thumbR = getThumbBounds();

		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
			// paint the distance between the start of the track and top of
			// the thumb
			x = trackRect.x;
			y = trackRect.y;
			w = trackRect.width;
			h = thumbR.y - y;
		} else {
			// fill the area between the start of the track and the left edge of the thumb
			x = trackRect.x;
			y = trackRect.y;
			w = thumbR.x - x;
			h = trackRect.height;
		}
		drawHighlight((Graphics2D) g, x, y, w, h);
	}

	@Override
	protected void paintIncreaseHighlight(final Graphics g) {
		Rectangle thumbR = getThumbBounds();
		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
			// fill the area between the bottom of the thumb and the end of
			// the track.
			x = trackRect.x;
			y = thumbR.y + thumbR.height;
			w = trackRect.width;
			h = trackRect.height - (y - trackRect.y);
		} else {
			// fill the area between the right edge of the thumb and the end of
			// the track.
			x = thumbR.x + thumbR.width;
			y = trackRect.y;
			w = trackRect.width - x;
			h = trackRect.height;
		}
		drawHighlight((Graphics2D) g, x, y, w, h);
	}

	private int paintSlider(final Graphics2D g2d, final int x, final int y, final int width, final int height) {
		g2d.setColor(LIGHT_COLOR);
		g2d.fillRect(x + width - 2, y, 2, height);
		g2d.fillRect(x, y, 2, height);
		g2d.setColor(DARK_COLOR);
		g2d.fillRect(x + 2, y, width - 4, height);
		g2d.setColor(FRAME_COLOR_THUMP);
		g2d.drawRoundRect(x + 2, y, width - 5, height - 1, 2, 2);
		return height;
	}

	@Override
	protected void paintThumb(final Graphics g, final JComponent c, final Rectangle thumbBounds) {
		if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
			return;
		}
		Graphics2D g2d = (Graphics2D) g;
		if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
			paintSlider(g2d, thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
		} else {
			// TODO fix bounds
			paintSlider(g2d, thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
		}
	}

	@Override
	protected void paintTrack(final Graphics g, final JComponent c, final Rectangle trackBounds) {
		paintDecreaseHighlight(g);
		paintIncreaseHighlight(g);
	}
}