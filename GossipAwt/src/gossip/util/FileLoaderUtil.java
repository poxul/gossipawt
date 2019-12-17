package gossip.util;

import org.w3c.dom.Document;

import gossip.lib.file.FileUtil;
import gossip.lib.util.StringUtil;
import gossip.manager.DocumentManagerUtil;
import gossip.util.xml.XmlHelper;

public class FileLoaderUtil {

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
			}
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

}
