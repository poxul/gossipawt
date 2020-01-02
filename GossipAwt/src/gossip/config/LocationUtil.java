
package gossip.config;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class LocationUtil {

	public enum ViewId {
		TOAST, CHAT, KEYBOARD
	}

	public static Point getLocation(ViewId id, Rectangle bounds) {

		Point p = null;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		switch (id) {
		case TOAST:
			p = new Point(screenSize.width - 100 - bounds.width, 0);
			break;
		case CHAT:
			p = new Point(screenSize.width - 10 - bounds.width, 40);
			break;
		case KEYBOARD:
			p = new Point(screenSize.width - bounds.width, screenSize.height - bounds.height);
			break;
		default:
			p = new Point(screenSize.width - bounds.width, screenSize.height - bounds.height);
			break;
		}
		return p;
	}

}
