package gossip.util.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;

/**
 * THIS will not work for nested nodes with the same name !!!
 * 
 * It will not work propplerly for escape sequences!!
 * 
 * @author mila
 * 
 */
public class XmlHelper {

	private static final Logger logger = MyLogger.getLog(XmlHelper.class);

	public static class XmlHelperResult {

		private int startIndex;
		private int endIndex;
		private String str;
		private final int startIndexAttribute;
		private final int endIndexAttribute;

		/**
		 * @return the startIndex
		 */
		public int getStartIndex() {
			return startIndex;
		}

		/**
		 * @param startIndex
		 *            the startIndex to set
		 */
		public void setStartIndex(int startIndex) {
			this.startIndex = startIndex;
		}

		/**
		 * @return the endIndex
		 */
		public int getEndIndex() {
			return endIndex;
		}

		/**
		 * @param endIndex
		 *            the endIndex to set
		 */
		public void setEndIndex(int endIndex) {
			this.endIndex = endIndex;
		}

		/**
		 * @return the str
		 */
		public String getStr() {
			return str;
		}

		/**
		 * @param str
		 *            the str to set
		 */
		public void setStr(String str) {
			this.str = str;
		}

		public String getResultString() {
			if (str != null && startIndex >= 0 && endIndex >= 0 && endIndex < str.length() && startIndex <= endIndex) {
				return str.substring(startIndex, endIndex).trim();
			}
			return null;

		}

		public String getAttribute(String name) {
			int idx = getAttributes() == null ? -1 : getAttributes().indexOf(name);
			if (idx >= 0) {
				int idx2 = getAttributes().substring(idx).indexOf('=');
				if (idx2 >= 0) {
					idx += idx2 + 1;
					idx2 = getAttributes().substring(idx).indexOf('"');
					if (idx2 >= 0) {
						idx += idx2 + 1;
						idx2 = getAttributes().substring(idx).indexOf('"');
						if (idx2 >= 0) {
							idx2 += idx;
							return getAttributes().substring(idx, idx2);
						}
					}
				}
			}
			return null;
		}

		private String attributes;

		public String getAttributes() {
			if (attributes == null) {
				if (startIndexAttribute < 0) {
					return null;
				}
				if (endIndexAttribute < 0) {
					return null;
				}
				if (endIndexAttribute <= startIndexAttribute) {
					return null;
				}
				if (endIndexAttribute >= str.length()) {
					return null;
				}
				attributes = str.substring(startIndexAttribute, endIndexAttribute);
			}
			return attributes;
		}

		private XmlHelperResult(int startIndex, int endIndex, String str, int attributeStart, int attributeEnd) {
			super();
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.str = str;
			this.startIndexAttribute = attributeStart;
			this.endIndexAttribute = attributeEnd;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "XmlHelperResult [endIndex=" + endIndex + ", startIndex=" + startIndex + ", str=" + str + "]";
		}

	}

	private static String formatNodeNameEnd(String nodeName) {
		if (nodeName != null) {
			StringBuilder buff = new StringBuilder(nodeName.length() + 2);
			buff.append('<');
			buff.append('/');
			buff.append(nodeName);
			buff.append('>');
			return buff.toString();
		} else {
			return null;
		}
	}

	private static XmlHelperResult getNodeContent(String xmlStr, String nodeName) {
		if (!StringUtil.isNullOrEmpty(xmlStr)) {
			int first = xmlStr.indexOf("<" + nodeName);
			if (first >= 0) {
				first += nodeName.length() + 1;
				if (first < xmlStr.length()) {
					int attributeStart = first;
					int firstEnd = xmlStr.substring(first).indexOf('>') + 1;
					if (firstEnd > 0) {
						first += firstEnd;
						if (xmlStr.charAt(first - 2) == '/') {
							int end = first - 2;
							return new XmlHelperResult(first, end, xmlStr, attributeStart, end);
						} else {
							// attribute end
							int attributeEnd = first;
							int end = xmlStr.substring(first).indexOf(formatNodeNameEnd(nodeName));
							if (end > 0) {
								end += first;
								if (end <= xmlStr.length()) {
									return new XmlHelperResult(first, end, xmlStr, attributeStart, attributeEnd);
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static List<XmlHelperResult> getNodes(String xmlStr, String nodeName) {
		List<XmlHelperResult> resList = new ArrayList<XmlHelperResult>();
		int end = 0;
		while (end < xmlStr.length() && end >= 0) {
			XmlHelperResult xmlHelperResult = getNodeContent(xmlStr.substring(end, xmlStr.length()), nodeName);
			if (xmlHelperResult != null) {
				resList.add(xmlHelperResult);
				end += xmlHelperResult.getEndIndex();
			} else {
				break;
			}
		}
		return resList;
	}

	public static String getNodeValue(String xmlStr, String nodeName) {
		XmlHelperResult res = getNodeContent(xmlStr, nodeName);
		if (res != null) {
			String resStr = res.getResultString();
			if (resStr != null) {
				return resStr.trim();
			}
		}
		return null;
	}

	/**
	 * Rückgabe des ersten Knotens in der nächsten Ebene mit dem namen "elementName"
	 * 
	 */
	public static Node getFirstChildNodeOf(String elementName, Node n) {
		if (elementName != null && n != null) {
			NodeList nodes = n.getChildNodes();
			for (int r = 0; r < nodes.getLength(); r++) {
				Node resultNode = nodes.item(r);
				String resultNodeName = resultNode.getNodeName();
				if (resultNodeName.equalsIgnoreCase(elementName)) {
					return resultNode;
				}
			}
		}
		return null;
	}

	/**
	 * Rückgabe des ersten Knotens in der nächsten Ebene mit dem Namen "elementName"
	 * 
	 */
	public static Node getFirstChildNodeOf(String elementName, String attributeName, String attributeValue, Node n) {
		if (attributeValue != null && attributeName != null && elementName != null && n != null) {
			NodeList nodes = n.getChildNodes();
			for (int r = 0; r < nodes.getLength(); r++) {
				Node resultNode = nodes.item(r);
				if (StringUtil.compare(elementName, resultNode.getNodeName())) {
					if (StringUtil.compare(XmlHelper.getAttributeValueByName(resultNode, attributeName), attributeValue)) {
						return resultNode;
					}
				}
			}
		}
		return null;
	}

	public static String getAttributeValueByName(Node node, String name) {
		if (node != null && name != null) {
			NamedNodeMap nodeMap = node.getAttributes();
			if (nodeMap != null && nodeMap.getNamedItem(name) != null) {
				return nodeMap.getNamedItem(name).getTextContent();
			}
		}
		return null;
	}

	public static Document parseXmlDocument(String xml) {
		Document document = null;
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		try {
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			InputStream bais = new ByteArrayInputStream(xml.getBytes());
			document = builder.parse(bais);
		} catch (Exception e) {
			MyLogger.printExecption(logger, e);
			logger.error("Message ={}", xml);
		}
		return document;
	}

	static XPathExpression createExpression(String path) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		xpath.setNamespaceContext(new MyNamespaceContext());
		XPathExpression exp = null;
		try {
			exp = xpath.compile(path);
		} catch (XPathExpressionException e) {
			MyLogger.printExecption(logger, e);
		}
		return exp;
	}

	static boolean hasNode(String xmlStr, String nodeName) {
		if (!StringUtil.isNullOrEmpty(xmlStr)) {
			int first = xmlStr.indexOf("<" + nodeName);
			if (first >= 0) {
				return true;
			}
		}
		return false;
	}

}
