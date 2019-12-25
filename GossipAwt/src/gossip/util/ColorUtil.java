package gossip.util;

import java.awt.Color;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.lib.util.MyLogger;
import gossip.lib.util.NumberUtil;
import gossip.lib.util.StringUtil;

public class ColorUtil {

	private static final Logger logger = MyLogger.getLog(ColorUtil.class);
	
	public static Color parseColor(final String str) {
		if (StringUtil.isNullOrEmpty(str) || !str.startsWith("#") || str.length() < 7) {
			logger.error( "invalid color: {}", str);
			return null;
		}
		boolean hasAlpha = str.length() > 7;
		return parseColor(str.substring(1), hasAlpha);
	}

	public static Color parseColor(final String str, final boolean hasAlpha) {
		int cNum = (int) NumberUtil.parseHexNumber(str, ColorConstants.DEFAULT_MASK_COLOR_RGB);
		return new Color(cNum, hasAlpha);
	}

	public static int parseAlphaMask(final String str) {
		return (int) NumberUtil.parseHexNumber(str, ColorConstants.DEFAULT_MASK_ALPHA_MASK) & 0xFF;
	}

	public static Color getDarkerColor(final Color c, final double factor) {
		return new Color(Math.max((int) (c.getRed() * factor), 0), Math.max((int) (c.getGreen() * factor), 0), Math.max((int) (c.getBlue() * factor), 0), c.getAlpha());
	}

}
