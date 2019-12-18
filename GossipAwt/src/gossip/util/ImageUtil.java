package gossip.util;

import java.awt.Image;

public class ImageUtil {

	public static Image getImage(String name) {
		String imageName = ResourcesUtil.getImageFileName(name);
		return FileLoaderUtil.readBufferedPngImage(imageName);
	}

}
