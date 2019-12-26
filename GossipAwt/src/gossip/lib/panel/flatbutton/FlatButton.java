package gossip.lib.panel.flatbutton;

import java.awt.Graphics2D;
import java.awt.Point;

public interface FlatButton {

	boolean contains(Point p);

	void setxPos(int xPos);

	void setyPos(int yPos);

	void draw(Graphics2D g2d, int x, int y);

}