package gossip.lib.panel;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

import gossip.config.FontConstants;
import gossip.lib.file.FileService;
import gossip.lib.file.PropertiesUtil;
import gossip.lib.file.FileNameUtil.PathResource;
import gossip.lib.util.MyLogger;

public class FontSupportUtil {

	private static final Logger logger = MyLogger.getLog(FontConstants.class);

	private static String standardFontFamilyName;

	private static String PREFERED_FONT_2 = "Liberation Sans"; // Sans
	private static String PREFERED_FONT_3 = "FreeSans";
	private static String PREFERED_FONT_1 = "Arial";

	public static String getFontPropertiesFileName() {
		return FileService.getPath(PathResource.RESOURCE) + "lang" + File.separatorChar + "font.properties";
	}

	public static String getStandardFontFamily() {
		if (standardFontFamilyName == null) {
			File file = new File(getFontPropertiesFileName());
			Properties properties = PropertiesUtil.loadProperties(file, new Properties());

			if (properties != null && !properties.isEmpty()) {
				String[] preferedFont = {
						properties.getProperty("Font1", PREFERED_FONT_1),
						properties.getProperty("Font2", PREFERED_FONT_2),
						properties.getProperty("Font3", PREFERED_FONT_3)
				};

				for (int i = 0; i < preferedFont.length; i++) {
					logger.info(MyLogger.OBSERVER_MARKER, "prefered font {}={}", i, preferedFont[i]);
				}

				standardFontFamilyName = checkFont(preferedFont, getSystemFontNames());
				if (standardFontFamilyName == null) {
					standardFontFamilyName = checkFont(preferedFont, getSystemFontFamilyNames());
					if (standardFontFamilyName == null) {
						standardFontFamilyName = preferedFont[2];
					}
				}
			} else {
				standardFontFamilyName = "Arial";
			}

			logger.info(MyLogger.OBSERVER_MARKER, "standard font ={}", standardFontFamilyName);
		}
		return standardFontFamilyName;
	}

	private static String[] getSystemFontFamilyNames() {
		Font[] fonts = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		List<String> fontNames = new ArrayList<>();
		logger.info(MyLogger.OBSERVER_MARKER, "check system fonts2 ");
		for (int i = 0; i < fonts.length; i++) {
			String name = fonts[i].getFamily();

			if (!fontNames.contains(name)) {
				fontNames.add(name);
			}
		}
		return fontNames.toArray(new String[fontNames.size()]);
	}

	private static String[] getSystemFontNames() {
		Font[] fonts = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		List<String> fontNames = new ArrayList<>();
		logger.info(MyLogger.OBSERVER_MARKER, "check system fonts2 ");
		for (int i = 0; i < fonts.length; i++) {
			String name = fonts[i].getFontName();

			if (!fontNames.contains(name)) {
				fontNames.add(name);
			}
		}
		return fontNames.toArray(new String[fontNames.size()]);
	}

	private static String checkFont(String[] family, String[] names) {

		for (int i = 0; i < names.length; i++) {
			logger.info(MyLogger.OBSERVER_MARKER, "Font name ={}", names[i]);
		}

		for (int k = 0; k < family.length; k++) {
			for (int i = 0; i < names.length; i++) {
				if (names[i].equalsIgnoreCase(family[k])) {
					return names[i];
				}
			}
		}
		return null;
	}
}
