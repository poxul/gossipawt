package gossip.util;

import java.awt.Image;

public class ImageUtil {

	public static Image getImage(String name) {
		String imageName = ResourcesUtil.getImageFileName(name);
		return FileLoaderUtil.readBufferedPngImage(imageName);
	}

	public static Image scaleImage(Image img, int maxWidth, int maxHeight) {
		if (img == null || maxWidth <= 0 || maxHeight <= 0) {
			return null;
		}
		double width = img.getWidth(null);
		double height = img.getHeight(null);
		if (width > maxWidth || height > maxHeight) {
			double dim;
			if (width > maxWidth) {
				dim = maxWidth / width;
				width = maxWidth;
				height *= dim;
			}

			if (height > maxHeight) {
				dim = maxHeight / height;
				height = maxHeight;
				width *= dim;
			}
			return img.getScaledInstance((int) Math.round(width), (int) Math.round(height), Image.SCALE_REPLICATE);
		}
		return img;
	}
}
