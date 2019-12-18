package gossip.util;

import java.io.File;

import gossip.lib.file.FileNameUtil.PathResource;
import gossip.lib.file.FileService;

public class ResourcesUtil {

	public static String getDefinitionFileName(String name) {
		return FileService.getPath(PathResource.RESOURCE) + "xml" + File.separatorChar + "keys" + File.separatorChar + name;
	}

	public static String getImageFileName(String name) {
		return FileService.getPath(PathResource.RESOURCE) + "img" + File.separatorChar + name;
	}

}
