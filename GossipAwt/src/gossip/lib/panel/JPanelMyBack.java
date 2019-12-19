package gossip.lib.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.util.DrawingUtil;

public class JPanelMyBack extends JPanelDisposable {

	private static final long serialVersionUID = 1L;

	private static final int STD_ARC = 3;

	private Color topColor;
	private Color bottomColor;

	private Border outBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
	private Border border;

	private boolean isShaddow;

	public JPanelMyBack(Color topColor, Color bottomColor) {
		this(topColor, bottomColor, BorderFactory.createEmptyBorder(0, 0, 0, 0), true);
	}

	public JPanelMyBack(Color topColor, Color bottomColor, Border border) {
		this(topColor, bottomColor, border, true);
	}

	public JPanelMyBack(Color topColor, Color bottomColor, Border border, boolean isShaddow) {
		super();
		this.topColor = topColor;
		this.bottomColor = bottomColor;
		this.isShaddow = isShaddow;
		setBorder(border);
		setOpaque(false);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = DrawingUtil.getGraphics2d(g);
		try {
			super.paint(g2d);
		} finally {
			g2d.dispose();
		}
	}

	private Border createBorder() {
		return BorderFactory.createCompoundBorder(outBorder,
				BorderFactory.createCompoundBorder(new RoundedLineBorder(this.topColor, this.bottomColor, 1, STD_ARC, true, isShaddow ? 5 : 0), border));
	}

	@Override
	public void setBorder(Border border) {
		this.border = border;
		super.setBorder(createBorder());
	}

	public void setColor(Color c1, Color c2) {
		this.topColor = c1;
		this.bottomColor = c2;
		super.setBorder(createBorder());
	}

	public void setOuterBorder(Border outBorder) {
		this.outBorder = outBorder;
		setBorder(border);
	}

}
