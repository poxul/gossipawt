package gossip.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;
import gossip.util.xml.ParseXmlUtil;
import gossip.view.keyboard.key.MyKey;
import gossip.view.keyboard.key.MyKeyBlackList;

public class LanguageUtil {
	private static final Logger logger = MyLogger.getLog(LanguageUtil.class);

	private static final String ELEMENT_KEYBOARDS = "gs:KeyBoards";
	private static final String ELEMENT_BLACKLISTS = "gs:BlackLists";
	private static final String ELEMENT_ALPHANUM = "gs:Alphanum";
	private static final String ELEMENT_NUMPAD = "gs:NumPad";
	private static final String ELEMENT_FILENAME = "gs:FileName";
	private static final String ATTRIBUTE_LANGUAGE = "language";
	private static final String ATTRIBUTE_TYPE = "type";
	private static final String ELEMENT_KEY_BOARD = "gs:KeyBoard";
	private static final String ELEMENT_BLACKLIST = "gs:BlackList";
	private static final String ELEMENT_KEY_VALUE = "gs:Value";
	private static final String ELEMENT_KEY_SIZE = "gs:Size";
	private static final String ELEMENT_KEY_IS_LOCKED = "gs:Locked";
	private static final String ELEMENT_KEY_COLOR = "gs:Color";
	private static final String ELEMENT_KEY_DRAW_COLOR = "gs:DrawColor";
	private static final String ELEMENT_KEY = "gs:Key";
	private static final String ELEMENT_KEY_LINE = "gs:KeyLine";
	private static final String ELEMENT_KEY_SET = "gs:KeySet";
	private static final String ATTRIBUTE_KEY_ID = "id";
	private static final String UPLUS_ENCODE_STRING = "#U+";

	public synchronized static boolean parseBlackList(Document doc, final MyKeyBlackList keyLineList) {
		Node node = ParseXmlUtil.getNode(doc, ELEMENT_BLACKLIST);
		if (node == null) {
			logger.error("Error at parse blacklist files");
			return false;
		}

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			if (subNode != null && !ParseXmlUtil.isPaddingNode(subNode.getNodeName()) && StringUtil.compare(subNode.getNodeName(), ELEMENT_KEY_SET)) {
				return parseKeySet(subNode, keyLineList);
			}
		}
		return false;
	}

	public static boolean parseBlackListFiles(Document doc, final Map<Locale, Map<String, String>> map) {
		Node node = ParseXmlUtil.getNode(doc, ELEMENT_BLACKLISTS);
		if (node == null) {
			logger.error("Error at parse blacklists files");
			return false;
		}

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			if (subNode != null) {
				if (!ParseXmlUtil.isPaddingNode(subNode.getNodeName())) {
					if (StringUtil.compare(subNode.getNodeName(), ELEMENT_ALPHANUM)) {
						String language = ParseXmlUtil.getNodeAttribbuteValue(subNode, ATTRIBUTE_LANGUAGE);
						String type = ParseXmlUtil.getNodeAttribbuteValue(subNode, ATTRIBUTE_TYPE);
						NodeList fileNodes = subNode.getChildNodes();
						for (int j = 0; j < fileNodes.getLength(); j++) {
							Node fileNode = fileNodes.item(j);
							if (!ParseXmlUtil.isPaddingNode(fileNode.getNodeName()) && StringUtil.compare(fileNode.getNodeName(), ELEMENT_FILENAME)) {
								String filename = fileNode.getTextContent();
								if (!StringUtil.isNullOrEmpty(language) && !StringUtil.isNullOrEmpty(type)) {
									Locale locale = new Locale(language);
									Map<String, String> typeMap = map.get(locale);
									if (typeMap == null) {
										typeMap = new HashMap<>();
										map.put(locale, typeMap);
									}
									typeMap.put(type, filename);
								} else {
									logger.error("invalid element name {}", subNode.getNodeName());
								}
							}
						}
					} else {
						logger.error("Unknown element name ={}", subNode.getNodeName());
					}
				}
			}
		}
		return true;

	}

	/**
	 * Expecting an Node with the name <code>ELEMENT_KEY</code>
	 * 
	 * @param node
	 * @return
	 */
	private static MyKey parseKey(Node node) {
		if (node == null) {
			logger.error("Error at parse keyboard KeyLine");
			return null;
		}
		String[] keyStrings = new String[3];
		int size = 0;
		boolean isLocked = false;
		NodeList nodeList = node.getChildNodes();
		Color color = null;
		Color drawColor = null;
		// parse
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			if (subNode != null) {
				if (!ParseXmlUtil.isPaddingNode(subNode.getNodeName())) {
					if (StringUtil.compare(subNode.getNodeName(), ELEMENT_KEY_VALUE)) {
						String idString = ParseXmlUtil.getNodeAttribbuteValue(subNode, ATTRIBUTE_KEY_ID);
						if (!StringUtil.isNullOrEmpty(idString)) {
							try {
								int id = Integer.parseInt(idString);
								if (id < keyStrings.length) {
									keyStrings[id] = subNode.getTextContent();
								} else {
									logger.error("Invalid key id value={}", id);
									return null;
								}
							} catch (NumberFormatException e) {
								logger.error("Invalid key id={}", idString);
								MyLogger.printExecption(logger, e);
								return null;
							}
						}
					} else if (StringUtil.compare(subNode.getNodeName(), ELEMENT_KEY_SIZE)) {
						String sizeStr = subNode.getTextContent();
						try {
							size = Integer.valueOf(sizeStr);
						} catch (NumberFormatException e) {
							MyLogger.printExecption(logger, e);
						}
					} else if (StringUtil.compare(subNode.getNodeName(), ELEMENT_KEY_IS_LOCKED)) {
						String lockedStr = subNode.getTextContent();
						try {
							isLocked = Boolean.valueOf(lockedStr);
						} catch (Exception e) {
							MyLogger.printExecption(logger, e);
						}
					} else if (StringUtil.compare(subNode.getNodeName(), ELEMENT_KEY_COLOR)) {
						String colorStr = subNode.getTextContent();
						try {
							color = ColorUtil.parseColor(colorStr);
						} catch (Exception e) {
							MyLogger.printExecption(logger, e);
						}
					} else if (StringUtil.compare(subNode.getNodeName(), ELEMENT_KEY_DRAW_COLOR)) {
						String colorStr = subNode.getTextContent();
						try {
							drawColor = ColorUtil.parseColor(colorStr);
						} catch (Exception e) {
							MyLogger.printExecption(logger, e);
						}
					}
				}
			}
		}
		MyKey myKey = null;
		if (!StringUtil.isNullOrEmpty(keyStrings[0])) {
			myKey = new MyKey(toKeyString(keyStrings[0]), toKeyString(keyStrings[1]), toKeyString(keyStrings[2]), size, isLocked);
			myKey.setColor(color);
			myKey.setDrawColor(drawColor);
		}
		return myKey;
	}

	public synchronized static boolean parseKeyBoard(Document doc, final List<List<MyKey>> keyLineList) {
		Node node = ParseXmlUtil.getNode(doc, ELEMENT_KEY_BOARD);
		if (node == null) {
			logger.error("Error at parse keyboard files");
			return false;
		}

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			if (subNode != null) {
				if (!ParseXmlUtil.isPaddingNode(subNode.getNodeName())) {
					if (StringUtil.compare(subNode.getNodeName(), ELEMENT_KEY_SET)) {
						return parseKeySet(subNode, keyLineList);
					}
				}
			}
		}
		return false;
	}

	static boolean parseKeyBoardDefinitionFiles(Document doc, final Map<Locale, String> map) {
		Node node = ParseXmlUtil.getNode(doc, ELEMENT_KEYBOARDS);
		if (node == null) {
			logger.error("Error au parse keyboard files");
			return false;
		}

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			if (subNode != null) {
				if (!ParseXmlUtil.isPaddingNode(subNode.getNodeName())) {
					if (StringUtil.compare(subNode.getNodeName(), ELEMENT_ALPHANUM)) {
						String language = ParseXmlUtil.getNodeAttribbuteValue(subNode, ATTRIBUTE_LANGUAGE);
						NodeList fileNodes = subNode.getChildNodes();
						for (int j = 0; j < fileNodes.getLength(); j++) {
							Node fileNode = fileNodes.item(j);
							if (!ParseXmlUtil.isPaddingNode(fileNode.getNodeName())) {
								if (StringUtil.compare(fileNode.getNodeName(), ELEMENT_FILENAME)) {
									String filename = fileNode.getTextContent();
									if (!StringUtil.isNullOrEmpty(language)) {
										Locale locale = new Locale(language);
										map.put(locale, filename);
									} else {
										logger.error("Language attribute missing {}", subNode.getNodeName());
									}
								}
							}
						}
					} else {
						logger.error("Unknown ELEMENT={}", subNode.getNodeName());
					}
				}
			}
		}
		return true;
	}

	private static List<MyKey> parseKeyLine(Node node) {
		if (node == null) {
			logger.error("Error at parse keyboard KeyLine");
			return null;
		}
		List<MyKey> keys = new ArrayList<MyKey>();
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			if (subNode != null) {
				if (!ParseXmlUtil.isPaddingNode(subNode.getNodeName())) {
					if (StringUtil.compare(subNode.getNodeName(), ELEMENT_KEY)) {
						MyKey key = parseKey(subNode);
						if (key != null) {
							keys.add(key);
						} else {
							logger.error("Stop parsing keys");
							return null;
						}
					}
				}
			}
		}
		return keys;
	}

	private static boolean parseKeySet(Node node, final List<List<MyKey>> keyLineList) {
		if (node == null) {
			logger.error("Error at parse keyboard KeySet");
			return false;
		}
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			if (subNode != null) {
				if (!ParseXmlUtil.isPaddingNode(subNode.getNodeName())) {
					if (StringUtil.compare(subNode.getNodeName(), ELEMENT_KEY_LINE)) {
						List<MyKey> keyLine = parseKeyLine(subNode);
						if (keyLine != null) {
							keyLineList.add(keyLine);
						} else {
							logger.error("Stop parse KeyLines");
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	static boolean parseNumPadDefinitionFiles(Document doc, final Map<Locale, Map<String, String>> map) {
		Node node = ParseXmlUtil.getNode(doc, ELEMENT_KEYBOARDS);
		if (node == null) {
			logger.error("Error at parse keyboards files");
			return false;
		}

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			if (subNode != null) {
				if (!ParseXmlUtil.isPaddingNode(subNode.getNodeName())) {
					if (StringUtil.compare(subNode.getNodeName(), ELEMENT_NUMPAD)) {
						String language = ParseXmlUtil.getNodeAttribbuteValue(subNode, ATTRIBUTE_LANGUAGE);
						String type = ParseXmlUtil.getNodeAttribbuteValue(subNode, ATTRIBUTE_TYPE);
						NodeList fileNodes = subNode.getChildNodes();
						for (int j = 0; j < fileNodes.getLength(); j++) {
							Node fileNode = fileNodes.item(j);
							if (!ParseXmlUtil.isPaddingNode(fileNode.getNodeName())) {
								if (StringUtil.compare(fileNode.getNodeName(), ELEMENT_FILENAME)) {
									String filename = fileNode.getTextContent();
									if (!StringUtil.isNullOrEmpty(language) && !StringUtil.isNullOrEmpty(type)) {
										Locale locale = new Locale(language);
										Map<String, String> typeMap = map.get(locale);
										if (typeMap == null) {
											typeMap = new HashMap<String, String>();
											map.put(locale, typeMap);
										}
										typeMap.put(type, filename);
									} else {
										logger.error("Language attribute missing {}", subNode.getNodeName());
									}
								}
							}
						}
					} else {
						logger.error("Unknown ELEMENT={}", subNode.getNodeName());
					}
				}
			}
		}
		return true;
	}

	public static Locale parseParameterLanguage(String localeText) {
		Locale locale = null;
		if (!StringUtil.isNullOrEmpty(localeText)) {
			localeText = localeText.toUpperCase();
			if (localeText.equals("DE")) {
				locale = Locale.GERMAN;
			} else if (localeText.equals("UK")) {
				locale = Locale.ENGLISH;
			} else if (localeText.equals("EN")) {
				locale = Locale.ENGLISH;
			} else if (localeText.equals("US")) {
				locale = Locale.ENGLISH;
			} else if (localeText.equals("FR")) {
				locale = Locale.FRENCH;
			} else if (localeText.equals("IT")) {
				locale = Locale.ITALIAN;
			} else if (localeText.equals("PL")) {
				locale = new Locale("pl", "");
			} else {
				locale = new Locale(localeText.toLowerCase());
			}
		}

		if (locale == null) {
			locale = Locale.getDefault();
		}
		return locale;
	}

	public static String toKeyString(String inStr) {
		String outStr = null;
		if (!StringUtil.isNullOrEmpty(inStr)) {
			if (inStr.startsWith(UPLUS_ENCODE_STRING)) {
				if (inStr.length() > UPLUS_ENCODE_STRING.length()) {
					String numberString = inStr.substring(UPLUS_ENCODE_STRING.length());
					if (!StringUtil.isNullOrEmpty(numberString)) {
						try {
							char num = (char) Integer.valueOf(numberString.trim(), 16).intValue();
							outStr = "" + Character.valueOf(num);
						} catch (NumberFormatException e) {
							MyLogger.printExecption(logger, e);
						}
					}
				}

			} else {
				outStr = inStr;
			}
		}
		if (outStr == null) {
			outStr = "";
		}
		return outStr;

	}

	public static String getPropertyName(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
