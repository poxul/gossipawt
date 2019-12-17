package gossip.util;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import org.apache.logging.log4j.Logger;

import gossip.lib.util.MyLogger;

public class DisplayUtil {

	private static final Logger logger = MyLogger.getLog(DisplayUtil.class);

	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_HEIGHT = 768;

	private static Dimension bounds;

	public static Dimension getScreenDimensions() {
		int width = DEFAULT_WIDTH;
		int height = DEFAULT_HEIGHT;
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			logger.info(MyLogger.OBSERVER_MARKER, "graphics environment ={}", ge);
			if (ge == null) {
				logger.error("invalid graphics environment --> exit");
				System.exit(-1);
			}

			GraphicsDevice graphicsDevice = ge.getDefaultScreenDevice();
			logger.info(MyLogger.OBSERVER_MARKER, "default graphics device ={}", graphicsDevice);

			if (graphicsDevice == null) {
				logger.warn(MyLogger.OBSERVER_MARKER, "try to repair graphics device");
				GraphicsDevice[] devices = ge.getScreenDevices();

				if (devices != null && devices.length > 0) {
					for (int i = 0; i < devices.length; i++) {
						DisplayMode dm = devices[i].getDisplayMode();
						logger.info(MyLogger.OBSERVER_MARKER, "display mode {} ={}", i, dm);
					}
					graphicsDevice = devices[0];
					logger.warn(MyLogger.OBSERVER_MARKER, "graphics device set to ={}", graphicsDevice);
				}
			}

			if (graphicsDevice != null) {
				DisplayMode[] displayModes = graphicsDevice.getDisplayModes();
				for (int i = 0; i < displayModes.length; i++) {
					logger.info(MyLogger.OBSERVER_MARKER, "display mode ={}x{}", displayModes[i].getWidth(), displayModes[i].getHeight());
				}

				DisplayMode mode = graphicsDevice.getDisplayMode();

				if (mode == null && displayModes.length > 0) {
					logger.error(MyLogger.OBSERVER_MARKER, "current displaymode is null ... use first one");
					mode = displayModes[0];
				}

				if (mode != null) {
					width = mode.getWidth();
					height = mode.getHeight();
				} else {
					logger.error(MyLogger.OBSERVER_MARKER, "use display default bounds");
					width = DEFAULT_WIDTH;
					height = DEFAULT_HEIGHT;
				}
			}
		} catch (Exception e) {
			logger.error(MyLogger.OBSERVER_MARKER, e.getMessage(), e);
			width = DEFAULT_WIDTH;
			height = DEFAULT_HEIGHT;
			logger.error(MyLogger.OBSERVER_MARKER, "set defalult dimensions");
		}
		logger.info(MyLogger.OBSERVER_MARKER, "Dimensions set to w={}, h={}", width, height);
		return new Dimension(width, height);
	}

	public static Dimension getBounds() {
		return bounds;
	}

	public static Dimension testDisplay(Rectangle maxBounds) {
		if (bounds == null) {
			Dimension screenBounds = getScreenDimensions();
			if (maxBounds != null) {
				int height = Math.min(screenBounds.height, maxBounds.height);
				int width = Math.min(screenBounds.width, maxBounds.width);
				bounds = new Dimension(width, height);
			} else {
				bounds = screenBounds;
			}
			logger.info(MyLogger.OBSERVER_MARKER, "Dimensions set to ={}", bounds);
		}
		return bounds;
	}
}
