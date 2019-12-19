package gossip.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Property;
import org.w3c.dom.Document;

import gossip.data.LanguageMap;
import gossip.lib.file.FileUtil;
import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;
import gossip.manager.DocumentManagerUtil;
import gossip.util.xml.XmlHelper;
import gossip.view.keyboard.input.InputItemId;

public class FileLoaderUtil {

	private static final Logger logger = MyLogger.getLog(FileLoaderUtil.class);

	private static final String IMG_TYPE_PNG = "png";

	public static Document getDocFromXml(String name) {
		return getDocFromXml(name, false);
	}

	/**
	 * Looks for the document in the CUSTOM XML path
	 * 
	 * ( for XML path look at getDocFromXml(String) )
	 * 
	 * @param filename
	 * @return
	 */
	public static Document getDocFromXml(String name, boolean isForceReload) {
		Document document = null;
		if (!isForceReload) {
			document = DocumentManagerUtil.getDocumentManager().get(name);
		} else {
			DocumentManagerUtil.getDocumentManager().remove(name);
		}
		if (document == null) {
			String xmlString = FileUtil.readText(name);
			if (!StringUtil.isNullOrEmpty(xmlString)) {
				document = XmlHelper.parseXmlDocument(xmlString);
				DocumentManagerUtil.getDocumentManager().put(name, document);
			} else {
				logger.error("invalid or empty file : {}", name);
			}
		} else {
			logger.error("can not read document: {}", name);
		}
		return document;
	}

	public static String createName(String path, String s) {
		StringBuilder builder = new StringBuilder();
		if (!StringUtil.isNullOrEmpty(path)) {
			builder.append(path);
			if (!path.endsWith("/")) {
				builder.append('/');
			}
		}
		if (!StringUtil.isNullOrEmpty(s)) {
			builder.append(s);
		}
		return builder.toString();
	}

	public static BufferedImage readBufferedImage(InputStream input, String imgType) {
		BufferedImage image = null;
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imgType);
		ImageReader imageReader = readers.next();
		if (imageReader != null) {
			try (ImageInputStream iis = ImageIO.createImageInputStream(input)) {
				imageReader.setInput(iis, true);
				image = imageReader.read(0);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return null;
			} finally {
				imageReader.dispose();
			}
		} else {
			logger.error("no reader for image type ={}", imgType);
		}
		return image;
	}

	public static BufferedImage readBufferedImage(String filename, String imgType) {
		logger.info("read image: <{}> {}", filename, imgType);
		try (InputStream is = new FileInputStream(filename)) {
			DataInputStream stream = new DataInputStream(new BufferedInputStream(is, 8 * 1024));
			return readBufferedImage(stream, imgType);
		} catch (IOException e) {
			logger.error("File not found: {}", filename);
			return null;
		}
	}

	public static BufferedImage readBufferedPngImage(String name) {
		return readBufferedImage(name, IMG_TYPE_PNG);
	}

	public static LanguageMap languageMap(String name) {
		LanguageMap map = new LanguageMap();
		Properties properties = new Properties();
		try (InputStream is = new BufferedInputStream(new FileInputStream(name))) {
			properties.load(is);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		Enumeration<Object> enumerator = properties.elements();
		while (enumerator.hasMoreElements()) {
			Property p = (Property) enumerator.nextElement();
			map.put(new InputItemId(p.getName()), p.getValue());
		}
		return map;
	}

}
