package gossip.lib.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;

import javax.swing.border.LineBorder;

import gossip.util.DrawingUtil;

public class RoundedLineBorder extends LineBorder {

	private class SetableRectangle extends Rectangle {

		private static final long serialVersionUID = 1L;

		public boolean setTo(int x, int y, int w, int h) {
			if (this.x != x || this.y != y || this.width != w || this.height != h) {
				setBounds(x, y, w, h);
				return true;
			}
			return false;
		}

	}

	private static final long serialVersionUID = 1L;
	private static final Stroke STROKE_1 = new BasicStroke(1.0f);

	private final int arc;
	private final Color color2;
	private final boolean isFilled;
	private final int shaddowGap;
	private boolean horizontalGradient = false;

	private final SetableRectangle rect = new SetableRectangle();

	private BasicStroke stroke;
	private Paint paint;
	private Float shape;

	private boolean isChangted = true;

	public RoundedLineBorder(Color color1, Color color2, int thickness, int arc) {
		this(color1, color2, thickness, arc, false, 0, false);
	}

	public RoundedLineBorder(Color color1, Color color2, int thickness, int arc, boolean isFilled) {
		this(color1, color2, thickness, arc, isFilled, 0, false);
	}

	public RoundedLineBorder(Color color1, Color color2, int thickness, int arc, boolean isFilled, int shaddowGap) {
		this(color1, color2, thickness, arc, isFilled, shaddowGap, false);
	}

	public RoundedLineBorder(Color color1, Color color2, int thickness, int arc, boolean isFilled, int shaddowGap, boolean horizontalGradient) {
		super(color1, thickness + shaddowGap, true);
		this.arc = arc;
		this.color2 = color2;
		this.isFilled = isFilled;
		this.shaddowGap = shaddowGap;
		this.horizontalGradient = horizontalGradient;
		stroke = new BasicStroke(thickness);
	}

	private Paint createPaint(float x, float y, float width, float height) {
		Paint p;
		if (horizontalGradient) {
			p = new GradientPaint(x, y, lineColor, x + width, y, color2, false);
		} else {
			p = new GradientPaint(x, y, lineColor, x, y + height, color2, false);
		}
		return p;
	}

	private void drawContent(Graphics2D g2d) {

		Shape s = getShape();
		g2d.setStroke(STROKE_1);

		int num = Math.min(DrawingUtil.SHADOW_COLORS.length, shaddowGap);

		for (int i = 0; i < num; i++) {
			g2d.setColor(DrawingUtil.SHADOW_COLORS[i]);
			g2d.drawRoundRect(i, i, rect.width - thickness, rect.height - thickness, arc, arc);
		}

		g2d.setStroke(stroke);
		g2d.setPaint(getPaint());

		if (isFilled) {
			g2d.fill(s);
		} else {
			g2d.draw(s);
		}

		if (shaddowGap > 0) {
			g2d.setStroke(STROKE_1);
			g2d.setColor(Color.GRAY);
			g2d.draw(s);
		}
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		super.getBorderInsets(c, insets);
		insets.bottom += shaddowGap;
		insets.right += shaddowGap;
		return insets;
	}

	private Paint getPaint() {
		if (paint == null || isChangted) {
			if (lineColor.equals(color2)) {
				paint = lineColor;
			} else {
				paint = createPaint(0, 0, rect.width, rect.height);
			}
		}
		return paint;
	}

	private Shape getShape() {
		if (isChangted || shape == null) {
			shape = new RoundRectangle2D.Float(0, 0, rect.width, rect.height, arc, arc);
		}
		return shape;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (width <= 0 || height <= 0) {
			return;
		}
		isChangted = (rect.setTo(0, 0, width, height));
		Graphics2D g2d = DrawingUtil.getGraphics2d(g);
		try {
			AffineTransform tx = new AffineTransform(g2d.getTransform());
			tx.translate(x, y);
			g2d.setTransform(tx);
			drawContent(g2d);
		} finally {
			g2d.dispose();
		}
	}

}