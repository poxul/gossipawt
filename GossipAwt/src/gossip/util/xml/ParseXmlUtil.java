package gossip.util.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gossip.lib.util.StringUtil;


public class ParseXmlUtil {

	/**
	 * Walks through the tree and find the first node with this name
	 * 
	 * @param inNode
	 *            Start node
	 * @param nodeName
	 *            the node name to find
	 * @return The node with the requested name or null
	 */
	public static Node getNode(Node inNode, String nodeName) {
		Node resultNode = null;
		if (inNode != null && !StringUtil.isNullOrEmpty(nodeName)) {
			if (!isPaddingNode(inNode.getNodeName())) {
				if (inNode.getNodeName().equals(nodeName)) {
					resultNode = inNode;
				} else {
					NodeList nodes = inNode.getChildNodes();
					for (int i = 0; i < nodes.getLength(); ++i) {
						resultNode = getNode(nodes.item(i), nodeName);
						if (resultNode != null) {
							break;
						}
					}
				}
			}
		}
		return resultNode;
	}

	public static String getNodeAttribbuteValue(Node node, String name) {
		String type = null;
		if (node != null) {
			Node thisNode = node.getAttributes().getNamedItem(name);
			if (thisNode != null) {
				type = thisNode.getTextContent();
			}
		}
		return type;
	}

	public static boolean isPaddingNode(String nodeName) {
		if (StringUtil.isNullOrEmpty(nodeName)) {
			return true;
		}
		return nodeName.equalsIgnoreCase("#text") || nodeName.equalsIgnoreCase("#comment");
	}

	/**
	 * Avoid null pointer exception at calling equals.
	 * 
	 * @param o1
	 * @param o2
	 * @return Returns true if name1 equals name2 or both are null.
	 */
	public static boolean compareNodeName(String name1, String name2) {
		return StringUtil.compare(name1, name2);
	}

}
