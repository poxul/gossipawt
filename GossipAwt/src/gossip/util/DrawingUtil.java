package gossip.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class DrawingUtil {

	public static final double kappa = 0.5522847498;

	public static final int STD_ARC = 16;

	private static final int BOTTOM_GAP = 10;
	public static final int MENU_WIDTH = 270;
	public static final int DOCKING_WIDTH = MENU_WIDTH + 10;

	static int MIN_BOTTOM_GAB = BOTTOM_GAP + STD_ARC + STD_ARC;

	private static Color MENU_SELECTED_BACKGROUND = new Color(0xCBCED6);

	public static void drawLoader(final Graphics2D g2, final GeneralPath p, final int height, final Component comp) {
		Color oldColor = g2.getColor();
		Stroke oldStroke = g2.getStroke();

		// Shadow
		drawShaddow(g2, p, height, comp);

		g2.setColor(MENU_SELECTED_BACKGROUND);
		Stroke stroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.fill(p);

		g2.setColor(Color.BLACK);
		g2.fill(stroke.createStrokedShape(p));

		g2.setStroke(oldStroke);
		g2.setColor(oldColor);
	}

	


	private static int X_GAB = 0;

	private static void drawShaddow(final Graphics2D g2, final GeneralPath p, final int height, final Component comp) {
		drawShaddow4(g2, p, SHADOW_COLORS, height);
	}

	private static void drawShaddow4(final Graphics2D g2, final GeneralPath p, final Color[] colors, final int height) {
		AffineTransform at = new AffineTransform();
		for (int i = 0; i < colors.length; i++) {
			g2.setColor(colors[i]);
			Shape s = at.createTransformedShape(p);
			g2.draw(s);
			at.translate(0.8, 0.8);
		}

	}

	static GeneralPath makeBottomLoader(final int x, final int y, final int dockWidth, final int dockHeight, final int width, final int height, int arc) {
		GeneralPath p = new GeneralPath();
		arc /= 2;

		double karc = arc * kappa;

		int x1 = x + arc + X_GAB;
		int x2 = x1 + dockWidth - X_GAB;
		int x3 = x2 + width - arc - arc - X_GAB;

		int y1 = y + arc;
		int y2 = y1 + height - dockHeight;
		int y3 = y1 + height - arc - arc;

		p.setWindingRule(Path2D.WIND_EVEN_ODD);

		p.moveTo(x2, y1 - arc);

		p.lineTo(x3, y1 - arc);// 1
		p.curveTo(x3 + karc, y1 - arc, x3 + arc, y1 - karc, x3 + arc, y1); // Ecke rechts oben
		p.lineTo(x3 + arc, y3);// 2
		p.curveTo(x3 + arc, y3 + karc, x3 + karc, y3 + arc, x3, y3 + arc); // Ecke rechts unten
		p.lineTo(x1, y3 + arc);// 3
		p.curveTo(x1 - karc, y3 + arc, x1 - arc, y3 + karc, x1 - arc, y3); // Ecke unten links
		p.lineTo(x1 - arc, y2);// 4
		p.curveTo(x1 - arc, y2 - karc, x1 - karc, y2 - arc, x1, y2 - arc); // Ecke links docking
		p.lineTo(x2 - arc - arc, y2 - arc);// 5
		p.curveTo(x2 - arc - karc, y2 - arc, x2 - arc, y2 - arc - karc, x2 - arc, y2 - arc - arc);
		p.lineTo(x2 - arc, y1);// 6
		p.curveTo(x2 - arc, y1 - karc, x2 - karc, y1 - arc, x2, y1 - arc);
		p.closePath();
		return p;
	}

	static GeneralPath makeCenterLoader(final int x, final int y, final int dockWidth, final int dockHeight, final int width, final int height, final int dockingPosition,
			int arc) {
		GeneralPath p = new GeneralPath();
		arc /= 2;

		double karc = arc * kappa;

		int x1 = x + arc + X_GAB;
		int x2 = x1 + dockWidth - X_GAB;
		int x3 = x2 + width - arc - arc - X_GAB;

		int y1 = y + arc;
		int y2 = y1 + dockingPosition;
		int y3 = y2 + dockHeight - arc - arc;
		int y4 = y1 + height - arc - arc;

		p.setWindingRule(Path2D.WIND_EVEN_ODD);

		p.moveTo(x2, y1 - arc);

		p.lineTo(x3, y1 - arc);// 1
		p.curveTo(x3 + karc, y1 - arc, x3 + arc, y1 - karc, x3 + arc, y1); // Ecke rechts oben 1
		p.lineTo(x3 + arc, y4);// 2
		p.curveTo(x3 + arc, y4 + karc, x3 + karc, y4 + arc, x3, y4 + arc); // Ecke rechts unten 2
		p.lineTo(x2, y4 + arc);// 3
		p.curveTo(x2 - karc, y4 + arc, x2 - arc, y4 + karc, x2 - arc, y4); // Ecke unten links 3
		p.lineTo(x2 - arc, y3 + arc + arc); // 4
		p.curveTo(x2 - arc, y3 + arc + karc, x2 - arc - karc, y3 + arc, x2 - arc - arc, y3 + arc); // Ecke links docking 4
		p.lineTo(x1, y3 + arc);// 5
		p.curveTo(x1 - karc, y3 + arc, x1 - arc, y3 + karc, x1 - arc, y3); // Ecke Docking oben 5
		p.lineTo(x1 - arc, y2);// 6
		p.curveTo(x1 - arc, y2 - karc, x1 - karc, y2 - arc, x1, y2 - arc); // Ecke Docking oben 6
		p.lineTo(x2 - arc - arc, y2 - arc);// 7
		p.curveTo(x2 - arc - karc, y2 - arc, x2 - arc, y2 - arc - karc, x2 - arc, y2 - arc - arc); // Ecke Docking oben 7
		p.lineTo(x2 - arc, y1);// 8
		p.curveTo(x2 - arc, y1 - karc, x2 - karc, y1 - arc, x2, y1 - arc); // Ecke oben links

		p.closePath();

		return p;
	}

	static GeneralPath makeTopLoader(final int x, final int y, final int dockWidth, final int dockHeight, final int width, final int height, int arc) {
		GeneralPath p = new GeneralPath();
		arc /= 2;

		double karc = arc * kappa;

		int x1 = x + arc + X_GAB;
		int x2 = x1 + dockWidth - X_GAB;
		int x3 = x2 + width - arc - arc - X_GAB;

		int y1 = y + arc;
		int y2 = y1 + dockHeight - arc - arc;
		int y3 = y2 + height;

		p.setWindingRule(Path2D.WIND_EVEN_ODD);

		p.moveTo(x1, y1 - arc);

		p.lineTo(x2, y1 - arc);
		p.lineTo(x3, y1 - arc);
		p.curveTo(x3 + karc, y1 - arc, x3 + arc, y1 - karc, x3 + arc, y1); // curve to
		p.lineTo(x3 + arc, y3);
		p.curveTo(x3 + arc, y3 + karc, x3 + karc, y3 + arc, x3, y3 + arc);
		p.lineTo(x2, y3 + arc);
		p.curveTo(x2 - karc, y3 + arc, x2 - arc, y3 + karc, x2 - arc, y3);
		p.lineTo(x2 - arc, y2 + arc + arc);
		p.curveTo(x2 - arc, y2 + arc + karc, x2 - arc - karc, y2 + arc, x2 - arc - arc, y2 + arc);
		p.lineTo(x1, y2 + arc);
		p.curveTo(x1 - karc, y2 + arc, x1 - arc, y2 + karc, x1 - arc, y2);
		p.lineTo(x1 - arc, y1);
		p.curveTo(x1 - arc, y1 - karc, x1 - karc, y1 - arc, x1, y1 - arc);

		p.closePath();

		return p;
	}

	public static final Color[] SHADOW_COLORS = {
			// new Color(0xC0000000, true),
			new Color(0x80000000, true),
			// new Color(0x70000000, true),
			new Color(0x50000000, true),
			new Color(0x30000000, true),
			new Color(0x10000000, true)

	};

	public static final Color LINE_SHADOW_COLOR = SHADOW_COLORS[2];

	public static final int SHADOW_SIZE = SHADOW_COLORS.length + 1;

	private static final float[] STEP_LIGTHT_FRACTIONS = {
			0.0f,
			0.6f,
			1.0f };

	private static final Color[] COLORS_BLEND = {
			new Color(190, 190, 190, 100),
			new Color(210, 210, 210, 50),
			new Color(220, 220, 220, 20) };

	private static final Color ON_STEP = new Color(0, 220, 0, 100);
	private static final Color OFF_STEP = new Color(128, 128, 128, 100);
	private static final Color TARGET_STEP_OFF = new Color(0, 0, 0, 100);
	private static final Color TARGET_STEP_ON = new Color(0, 100, 0, 100);

	private static final int STEP_MIN_HEIGHT = 8;

	private static final RenderingObserver RENDERING_OBESERVER = new RenderingObeserverConfigured();

	public static Graphics2D getGraphics2d(BufferedImage img) {
		return img == null ? null : getGraphics2d(img.getGraphics());
	}

	public static Graphics2D getGraphics2d(Graphics g) {
		if (g != null) {
			return RENDERING_OBESERVER.getGraphics2d(g);
		} else {
			return null;
		}
	}

	/**
	 * Blend two colors
	 * 
	 * @param c0
	 * @param c1
	 * @return
	 */
	private static Color blend(Color c0, Color c1) {
		double totalAlpha = c0.getAlpha() + c1.getAlpha();
		double weight0 = c0.getAlpha() / totalAlpha;
		double weight1 = c1.getAlpha() / totalAlpha;

		double r = weight0 * c0.getRed() + weight1 * c1.getRed();
		double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
		double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
		double a = Math.max(c0.getAlpha(), c1.getAlpha());

		return new Color((int) r, (int) g, (int) b, (int) a);
	}

	private static void drawStep(Graphics2D g2d, int x, int y, Color c, int width, int height, int imageHeight) {
		int startY = y + imageHeight - height;

		for (int i = 0; i < SHADOW_COLORS.length - 2; i++) {
			g2d.setColor(SHADOW_COLORS[i + 2]);
			g2d.drawRect(x + 1 + i, startY + 1 + i, width - 1, height - 1);
		}

		Color[] cc = {
				c,
				blend(c, Color.lightGray),
				blend(c, Color.white) };

		Paint p = new LinearGradientPaint(new Point(x, startY), new Point(x, startY + height), STEP_LIGTHT_FRACTIONS, cc, CycleMethod.NO_CYCLE);

		g2d.setPaint(p);
		g2d.fillRect(x, startY, width, height);

		if (height > 16) {
			Paint p1 = new LinearGradientPaint(new Point(x, startY), new Point(x, startY + height / 2), STEP_LIGTHT_FRACTIONS, COLORS_BLEND, CycleMethod.NO_CYCLE);
			g2d.setPaint(p1);
			g2d.fillRect(x + 1, startY + 2, width - 2, height / 2 - 4);
		}
	}

	/**
	 * Draw level meter
	 * 
	 * @param g2d
	 *            Graphics environment
	 * @param x
	 *            X Position ( left )
	 * @param y
	 *            Y Position ( top )
	 * @param width
	 *            bar width
	 * @param height
	 *            bar max height
	 * @param imgGap
	 *            gap bedween bars
	 * @param steps
	 *            actual number of steps
	 * @param target
	 *            index of target value step
	 * @param max
	 *            total number of steps
	 */
	public static void drawLevel(Graphics2D g2d, int x, int y, int width, int height, int imgGap, int steps, int target, int max) {
		int h = (height - STEP_MIN_HEIGHT) / max;
		int gab = imgGap + width;
		int i = 0;
		if (steps > 0) {
			for (i = 0; i <= steps; i++) {
				drawStep(g2d, x, y, i == target ? TARGET_STEP_ON : ON_STEP, width, STEP_MIN_HEIGHT + i * h, height);
				x += gab;
			}
		}
		for (; i <= max; i++) {
			drawStep(g2d, x, y, i == target ? TARGET_STEP_OFF : OFF_STEP, width, STEP_MIN_HEIGHT + i * h, height);
			x += gab;
		}
	}

	/**
	 * Create a placeholder image
	 * 
	 * @param w
	 * @param h
	 * @param backgroundColor
	 * @param drawColor
	 * @return
	 */
	public static Image createPlaceholderImage(int w, int h, Color backgroundColor, Color drawColor) {
		if (w <= 0 || h <= 0 || backgroundColor == null || drawColor == null) {
			return null;
		}

		int gapX = w / 20;
		if (gapX < 2) {
			gapX = 2;
		}

		int gapY = h / 20;
		if (gapY < 2) {
			gapY = 2;
		}

		int dw = w - 2 * gapX;
		int dh = h - 2 * gapY;

		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = getGraphics2d(image);
		try {
			g2d.setColor(backgroundColor);
			g2d.fillRect(gapX, gapY, dw, dh);

			g2d.setColor(drawColor);
			g2d.setStroke(new BasicStroke(2f));
			g2d.drawLine(gapX, gapY, dw, dh);
			g2d.drawLine(dw, gapY, gapX, dh);

		} finally {
			g2d.dispose();
		}
		return image;
	}

}
