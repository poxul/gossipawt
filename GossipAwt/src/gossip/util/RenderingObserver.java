package gossip.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import gossip.lib.panel.disposable.Disposable;
import gossip.lib.util.MyLogger;

public class RenderingObserver implements Disposable {

	private static final Logger logger = MyLogger.getLog(RenderingObeserverConfigured.class);

	private Map<RenderingHints.Key, Object> renderingHintsMap;
	private int level = 2;

	private static void initMap(Map<RenderingHints.Key, Object> map, int level) {
		switch (level) {
		case 2:
		case 3:
			map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			map.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			map.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
			map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			map.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
			map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
			map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			map.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			map.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
			break;

		case 4:
		case 5:
			map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			map.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			map.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			map.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
			map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			map.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			map.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
			break;

		case 0:
		case 1:
		default:
			map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			map.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			map.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
			map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			map.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
			map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
			map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			map.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
			map.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			break;
		}
	}

	protected void init() {
		// NOP
	}

	public void setLevel(int level) {
		if (this.level != level) {
			this.level = level;
			renderingHintsMap = null;
			logger.info("rendering changed to ={}", level);
		}
	}

	public RenderingObserver(int level) {
		this();
		setLevel(level);
	}

	public RenderingObserver() {
		super();
		init();
	}

	@SuppressWarnings("unchecked")
	private Map<RenderingHints.Key, Object> getGraphicsRenderingHints() {
		if (renderingHintsMap == null) {
			Toolkit tk = Toolkit.getDefaultToolkit();
			renderingHintsMap = (Map<RenderingHints.Key, Object>) (tk.getDesktopProperty("awt.font.desktophints"));
			if (renderingHintsMap == null) {
				renderingHintsMap = new HashMap<>();
			}
			initMap(renderingHintsMap, level);
		}
		return renderingHintsMap;

	}

	public Graphics2D getGraphics2d(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g.create();
		graphics2D.setRenderingHints(getGraphicsRenderingHints());
		return graphics2D;
	}

	@Override
	public void dispose() {
		// NOP
	}
}