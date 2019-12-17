package gossip.lib.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import gossip.config.ColorConstants;
import gossip.config.FontConstants;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.panel.txt.GraphicsTextRenderer;
import gossip.lib.util.StringUtil;
import gossip.rule.InputRule.SEVERITY;
import gossip.util.DrawingUtil;


public class JPanelAlertBox extends JPanelDisposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -878026166139510728L;

	private static final int STD_ARC = 3;

	private GraphicsTextRenderer textRenderer;

	private int width = 500;
	private int height = 60;

	private String infoText;

	private SEVERITY severity;

	public String getInfoText() {
		return infoText;
	}

	public void setInfoText(String infoText) {
		if (!StringUtil.compare(infoText, this.infoText)) {
			this.infoText = infoText;
			repaint();
		}
	}

	public JPanelAlertBox(int width, int height) {
		this.width = width;
		this.height = height;
		init();
	}

	private Font font;

	private void init() {
		font = FontConstants.ALERT_BOX_FONT;
		textRenderer = new GraphicsTextRenderer(null, getFont());
		buildView();
		setSeverity(SEVERITY.BLOCKING);
	}

	public void setSeverity(SEVERITY severity) {
		if (this.severity != severity) {
			this.severity = severity;
			Border border = createBorder(severity);
			setBorder(border);
		}
	}

	private static Border createBorder(SEVERITY sev) {
		Color c1;
		Color c2;
		switch (sev) {
			case OK:
				c1 = ColorConstants.ALERT_COLOR_INFO_1;
				c2 = ColorConstants.ALERT_COLOR_INFO_2;
				break;
			case WARNING:
				c1 = ColorConstants.ALERT_COLOR_WARNING_1;
				c2 = ColorConstants.ALERT_COLOR_WARNING_2;
				break;
			case BLOCKING:
			default:
				c1 = ColorConstants.ALERT_COLOR_BLOCKING_1;
				c2 = ColorConstants.ALERT_COLOR_BLOCKING_2;
				break;
		}
		return createBorder(c1, c2);
	}

	private static Border createBorder(Color c1, Color c2) {
		return BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), BorderFactory.createCompoundBorder(new RoundedLineBorder(c1, c2, 1,
				STD_ARC, true, 1), BorderFactory.createEmptyBorder(0, 0, 3, 5)));
	}

	private void buildView() {
		setPreferredSize(new Dimension(width, height));
		setOpaque(false);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = DrawingUtil.getGraphics2d(g);
		try {
			super.paint(g2d);
			int w = getWidth();
			int h = getHeight();
			if (w > 0 && h > 0) {
				paintText(g2d, getInfoText(), font, ColorConstants.ALERT_COLOR_TEXT, w, h);
			}
		} finally {
			g2d.dispose();
		}
	}

	private static final int TEXT_GAP_X = 5;
	private static final int TEXT_GAP_Y = 2;

	private void paintText(Graphics2D g2d, String text, Font f, Color tColor, int width, int height) {
		if (StringUtil.isNullOrEmpty(text)) {
			return;
		}
		g2d.setColor(tColor);
		if (textRenderer != null) {
			textRenderer.setText(text);
			textRenderer.setFont(f);
			textRenderer.update(g2d, TEXT_GAP_X, TEXT_GAP_Y, width - (2 * TEXT_GAP_X), height - (2 * TEXT_GAP_Y));
		}
	}

}
