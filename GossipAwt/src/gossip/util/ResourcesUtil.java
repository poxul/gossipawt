package gossip.util;

import java.io.File;
import java.util.Locale;

import gossip.lib.file.FileNameUtil.PathResource;
import gossip.lib.file.FileService;

public class ResourcesUtil {

	public static String getDefinitionFileName(String name) {
		return FileService.getPath(PathResource.RESOURCE) + "xml" + File.separatorChar + "keys" + File.separatorChar + name;
	}

	public static String getImageFileName(String name) {
		return FileService.getPath(PathResource.RESOURCE) + "img" + File.separatorChar + name;
	}

	public static String getLanguageFileName(Locale locale) {
		String lang = locale.getLanguage();
		return FileService.getPath(PathResource.RESOURCE) + "lang" + File.separatorChar + "language_" + lang + ".properties";
	}

}
