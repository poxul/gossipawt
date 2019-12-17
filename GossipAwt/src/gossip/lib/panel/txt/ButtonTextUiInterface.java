package gossip.lib.panel.txt;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public interface ButtonTextUiInterface {

	void setFont(Font font);

	void update(Graphics2D g2d, int x, int y, int width, int height);

	Font getFont();

	void setText(String infoText);

	void dispose();

	int getLineHeight();

	void setFixPositionStartPoint(Point fixTextPosition);

	void setTextAlignment(final StringFormatterUtil.Alignment alignment);

}