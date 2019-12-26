package gossip.lib.panel.flatbutton;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import gossip.util.ImageUtil;

public class DrawableFlatButton implements FlatButton {

	private Shape buttonShape;

	private Image img;

	private int xPos;
	private int yPos;

	private final String imageName;
	private Color backgroundColor;

	public static final int BUTTON_DIAMETER = 30;
	public static final int BUTTON_GAP = 5;

	public DrawableFlatButton(int xPos, int yPos, String imageName, Color backgroundColor) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.imageName = imageName;
		this.backgroundColor = backgroundColor;
	}

	private Shape getShape(int x, int y) {
		if (buttonShape == null || x != xPos || y != yPos) {
			xPos = x;
			yPos = y;
			buttonShape = new Ellipse2D.Double(xPos, yPos, BUTTON_DIAMETER, BUTTON_DIAMETER);
		}
		return buttonShape;
	}

	@Override
	public boolean contains(Point p) {
		if (p != null && buttonShape != null) {
			return buttonShape.contains(p);
		} else {
			return false;
		}
	}

	@Override
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	@Override
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	private Image getImage() {
		if (img == null) {
			img = ImageUtil.getImage(imageName);
		}
		return img;
	}

	@Override
	public void draw(Graphics2D g2d, int x, int y) {
		draw(g2d, backgroundColor, getShape(x, y), getImage());
	}

	private void draw(Graphics2D g2d, Color c, Shape s, Image img) {
		g2d.setColor(c);
		g2d.fill(s);

		Rectangle bnd = s.getBounds();
		int x = bnd.x + ((bnd.width - img.getWidth(null)) / 2);
		int y = bnd.y + ((bnd.height - img.getHeight(null)) / 2);

		g2d.drawImage(img, x, y, null);
	}

}