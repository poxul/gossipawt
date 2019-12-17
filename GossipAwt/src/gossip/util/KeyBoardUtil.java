package gossip.util;

import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import gossip.config.ButtonConstants;
import gossip.config.ColorConstants;
import gossip.config.ImageConstants;
import gossip.config.InputItemConstants;
import gossip.key.MyKey;
import gossip.keyboard.KeyBoardDefinition;
import gossip.lib.file.FileNameUtil.PathResource;
import gossip.lib.file.FileService;
import gossip.lib.panel.MyButton;
import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;

public class KeyBoardUtil {

	private static final Logger logger = MyLogger.getLog(KeyBoardUtil.class);

	public static final String BUTTON_FACE_NAME_BUTTON_INPUT_CHAR = "InputCharFace";
	public static final String BUTTON_FACE_NAME_BUTTON_INPUT_CHAR_SHIFT = BUTTON_FACE_NAME_BUTTON_INPUT_CHAR + "Shift";
	public static final String BUTTON_FACE_NAME_BUTTON_INPUT_CHAR_ALTGR = BUTTON_FACE_NAME_BUTTON_INPUT_CHAR + "AltGr";

	public static final String BUTTON_FACE_NAME_BUTTON_ENTER = "InputEnterFace";
	public static final String BUTTON_FACE_NAME_BUTTON_BACKSPACE = "InputBackSpaceFace";
	public static final String BUTTON_FACE_NAME_BUTTON_LEFT = "InputLeftFace";
	public static final String BUTTON_FACE_NAME_BUTTON_RIGHT = "InputRightFace";

	public static final String BUTTON_FACE_NAME_BUTTON_CANCEL = "InputCancelFace";
	public static final String BUTTON_FACE_NAME_BUTTON_SHIFT = "InputShift";
	public static final String BUTTON_FACE_NAME_BUTTON_ALTGR = "InputAltGr";

	public static final String BUTTON_FACE_NAME_BUTTON_SPACE = "SPACE";

	public static final String BUTTON_NAME_BACKSPACE = "#BACKSPACE";
	public static final String BUTTON_NAME_OFF = "#OFF";

	public static final String BUTTON_NAME_SPACE = "#SPACE";
	public static final String BUTTON_NAME_SHIFT = "#SHIFT";
	public static final String BUTTON_NAME_CURSOR_LEFT = "#LEFT";
	public static final String BUTTON_NAME_CURSOR_RIGHT = "#RIGHT";

	public static final String BUTTON_NAME_ALTGR = "#ALTGR";

	public static final String BUTTON_NAME_FUNC_1 = "#FUNC1";
	public static final String BUTTON_NAME_FUNC_2 = "#FUNC2";
	public static final String BUTTON_NAME_FUNC_3 = "#FUNC3";
	public static final String BUTTON_NAME_FUNC_4 = "#FUNC4";
	public static final String BUTTON_NAME_FUNC_5 = "#FUNC5";
	public static final String BUTTON_NAME_FUNC_6 = "#FUNC6";
	public static final String BUTTON_NAME_FUNC_7 = "#FUNC7";
	public static final String BUTTON_NAME_FUNC_8 = "#FUNC8";

	public static final String BUTTON_NAME_INSERT = "#INSERT";
	public static final String BUTTON_NAME_POS1 = "#POS1";
	public static final String BUTTON_NAME_PAGEUP = "#PAGEUP";
	public static final String BUTTON_NAME_TAB = "#TAB";
	public static final String BUTTON_NAME_DELETE = "#DELETE";
	public static final String BUTTON_NAME_END = "#END";
	public static final String BUTTON_NAME_PAGEDOWN = "#PAGEDOWN";
	public static final String BUTTON_NAME_ENTER = "#ENTER";
	public static final String BUTTON_NAME_ESC = "#ESC";
	public static final String BUTTON_NAME_EXIT = "#EXIT";

	public static final String BUTTON_NAME_PAGE_NORMAL = "#PAGE1";
	public static final String BUTTON_NAME_PAGE_SPECIAL = "#PAGE2";

	private static final Dimension DEFAULT_KEY_DIMENSION = new Dimension(70, 60);

	private static final int BUTTON_FACE_SPACE_WIDTH = 500;

	private static final int BUTTON_HEIGHT_RECT_KEYBOARD = 60;

	private static final int BUTTON_FACE_SPACE_HEIGHT = BUTTON_HEIGHT_RECT_KEYBOARD;
	private static final Dimension BUTTON_FACE_DIMENSION_CURSOR = DEFAULT_KEY_DIMENSION;

	private static final Dimension BUTTON_FACE_DIMENSION_SHIFT = DEFAULT_KEY_DIMENSION;
	private static final Dimension BUTTON_FACE_DIMENSION_BACKSPACE = new Dimension(86, 80);

	public static boolean isInputChar(String faceName) {
		if (faceName == null) {
			return false;
		} else {
			return faceName.startsWith(BUTTON_FACE_NAME_BUTTON_INPUT_CHAR);
		}
	}

	/**
	 * Creates a button that looks like a red backspace button
	 * 
	 * @param dataBroker is used to get the image
	 * @param comp       is used to load the image
	 * @return
	 */
	public static MyButton createBackspaceButton(Component comp) {

//		DefaultButtonFace buttonFace = new DrawButtonFace(ButtonStyle.RECT, ColorConstants.BUTTON_COLOR_RED, ButtonPainterUtil.BUTTON_WIDTH_RECT_KEYBOARD
//				- ButtonPainterUtil.BUTTON_IMAGE_GAP_X, ButtonPainterUtil.BUTTON_HEIGHT_RECT_KEYBOARD - ButtonPainterUtil.BUTTON_IMAGE_GAP_Y, BUTTON_FACE_NAME_BUTTON_BACKSPACE);

		MyButton button = new MyButton();
//		button.setOverlay(getImage(ImageConstants.IMAGE_NAME_BUTTON_OVERLAY_BACKSPACE, comp));
		button.setPreferredSize(BUTTON_FACE_DIMENSION_BACKSPACE);
//		button.setCenterImage(true);
//		button.addFace(buttonFace);
		button.setForeground(ColorConstants.TEXT_COLOR);
		return button;
	}

	public static MyButton createCursorLeftButton(Component comp) {
//		DefaultButtonFace buttonFace = new DrawButtonFace(ButtonStyle.RECT, ColorConstants.BUTTON_COLOR_GRAY, ButtonPainterUtil.BUTTON_WIDTH_RECT_KEYBOARD
//				- ButtonPainterUtil.BUTTON_IMAGE_GAP_X, ButtonPainterUtil.BUTTON_HEIGHT_RECT_KEYBOARD - ButtonPainterUtil.BUTTON_IMAGE_GAP_Y, BUTTON_FACE_NAME_BUTTON_LEFT);

		MyButton button = new MyButton();
//		button.setOverlay(getImage(ImageConstants.IMAGE_NAME_BUTTON_OVERLAY_CURSOR_LEFT, comp));
		button.setPreferredSize(BUTTON_FACE_DIMENSION_CURSOR);
//		button.setCenterImage(true);
//		button.addFace(buttonFace);
		button.setForeground(ColorConstants.TEXT_COLOR);
		return button;
	}

	public static MyButton createCursorRightButton(Component comp) {
//		DefaultButtonFace buttonFace = new DrawButtonFace(ButtonStyle.RECT, ColorConstants.BUTTON_COLOR_GRAY, ButtonPainterUtil.BUTTON_WIDTH_RECT_KEYBOARD
//				- ButtonPainterUtil.BUTTON_IMAGE_GAP_X, ButtonPainterUtil.BUTTON_HEIGHT_RECT_KEYBOARD - ButtonPainterUtil.BUTTON_IMAGE_GAP_Y, BUTTON_FACE_NAME_BUTTON_RIGHT);

		MyButton button = new MyButton();
//		button.setOverlay(getImage(ImageConstants.IMAGE_NAME_BUTTON_OVERLAY_CURSOR_RIGHT, comp));
		button.setPreferredSize(BUTTON_FACE_DIMENSION_CURSOR);
//		button.setCenterImage(true);
//		button.addFace(buttonFace);
		button.setForeground(ColorConstants.TEXT_COLOR);
		return button;
	}

	/**
	 * Normal Char
	 * 
	 * @param dataBroker
	 * @param comp
	 * @param padText
	 * @return
	 */
	public static MyButton createPadButton(Component comp, String text, String shiftText, String altGrText) {
		return createPadButton(comp, ButtonConstants.BUTTON_WIDTH_RECT_KEYBOARD, text, shiftText, altGrText);
	}

	public static MyButton createPadButton(Component comp, int width, String text, String buttonFaceName) {
		return createPadButton(comp, width, ButtonConstants.BUTTON_HEIGHT_RECT_KEYBOARD, text, text, text, buttonFaceName, buttonFaceName, buttonFaceName);
	}

	private static MyButton createPadButton(Component comp, int width, String text, String shiftText, String altGrText) {
		return createPadButton(comp, width, ButtonConstants.BUTTON_HEIGHT_RECT_KEYBOARD, text, shiftText, altGrText, BUTTON_FACE_NAME_BUTTON_INPUT_CHAR,
				BUTTON_FACE_NAME_BUTTON_INPUT_CHAR_SHIFT, BUTTON_FACE_NAME_BUTTON_INPUT_CHAR_ALTGR);
	}

	private static MyButton createPadButton(Component comp, int width, int height, String text, String shiftText, String altGrText, String buttonFaceName,
			String buttonFaceNameShift, String buttonFaceNameGr) {
		MyButton button = new MyButton();
		if (!StringUtil.isNullOrEmpty(buttonFaceName)) {
//			DefaultButtonFace buttonFace = new DrawButtonFace(ButtonStyle.RECT, ColorConstants.BUTTON_COLOR_DEFAULT, width - ButtonPainterUtil.BUTTON_IMAGE_GAP_X, height
//					- ButtonPainterUtil.BUTTON_IMAGE_GAP_Y, buttonFaceName);
//			buttonFace.setFixedText(text);
//			button.addFace(buttonFace);
		}
		if (!StringUtil.isNullOrEmpty(buttonFaceNameShift)) {
//			DefaultButtonFace buttonFaceShift = new DrawButtonFace(ButtonStyle.RECT, ColorConstants.BUTTON_COLOR_DEFAULT, width - ButtonPainterUtil.BUTTON_IMAGE_GAP_X, height
//					- ButtonPainterUtil.BUTTON_IMAGE_GAP_Y, buttonFaceNameShift);
//			buttonFaceShift.setFixedText(shiftText);
//			button.addFace(buttonFaceShift);
		}

		if (!StringUtil.isNullOrEmpty(buttonFaceNameGr)) {
//			DefaultButtonFace buttonFaceAltGr = new DrawButtonFace(ButtonStyle.RECT, ColorConstants.BUTTON_COLOR_DEFAULT, width - ButtonPainterUtil.BUTTON_IMAGE_GAP_X, height
//					- ButtonPainterUtil.BUTTON_IMAGE_GAP_Y, buttonFaceNameGr);
//			buttonFaceAltGr.setFixedText(altGrText);
//			button.addFace(buttonFaceAltGr);
		}

//		button.setFont((FontSupport.KEYBOARD_PAD_FONT));
		button.setForeground(ColorConstants.TEXT_COLOR);
//		button.setCenterImage(true);
//		button.setCurrentFace(buttonFaceName);
		button.setPreferredSize(new Dimension(width - 16, height - 5));
//		button.setNumTextLines(1);
		return button;
	}

	public static MyButton createSpaceButton(Component comp) {
//		DefaultButtonFace buttonFace = new DrawButtonFace(ButtonStyle.RECT, ColorConstants.BUTTON_COLOR_DEFAULT, BUTTON_FACE_SPACE_WIDTH - ButtonPainterUtil.BUTTON_IMAGE_GAP_X,
//				BUTTON_FACE_SPACE_HEIGHT - ButtonPainterUtil.BUTTON_IMAGE_GAP_Y, BUTTON_FACE_NAME_BUTTON_SPACE);
		MyButton button = new MyButton();
//		button.setFont((FontSupport.KEYBOARD_FONT));
		button.setForeground(ColorConstants.TEXT_COLOR);
//		button.setCenterImage(true);
//		button.addFace(buttonFace);
		button.setPreferredSize(new Dimension(BUTTON_FACE_SPACE_WIDTH, BUTTON_FACE_SPACE_HEIGHT));
		return button;
	}

	public static MyButton createShiftButton(Component comp) {
		MyButton button = new MyButton();
//		DefaultButtonFace buttonFace = new DrawButtonFace(ButtonStyle.RECT, ColorConstants.BUTTON_COLOR_DEFAULT, ButtonPainterUtil.BUTTON_WIDTH_RECT_KEYBOARD
//				- ButtonPainterUtil.BUTTON_IMAGE_GAP_X, ButtonPainterUtil.BUTTON_HEIGHT_RECT_KEYBOARD - ButtonPainterUtil.BUTTON_IMAGE_GAP_Y, BUTTON_FACE_NAME_BUTTON_SHIFT);
//		button.setFont((FontSupport.KEYBOARD_FONT));
		button.setForeground(ColorConstants.TEXT_COLOR);
//		button.setCenterImage(true);
//		button.addFace(buttonFace);
//		button.setOverlay(getImage(ImageConstants.OVERLAY_SHIFT, comp));
		button.setPreferredSize(BUTTON_FACE_DIMENSION_SHIFT);
		return button;
	}

	public static MyButton createLockedButton(Component comp, int width, int height, String text, String buttonFaceName) {
		MyButton button = new MyButton();
//		DefaultButtonFace buttonFace = new DrawButtonFace(ButtonStyle.RECT, ColorConstants.BUTTON_COLOR_DEFAULT, width - ButtonPainterUtil.BUTTON_IMAGE_GAP_X, height
//				- ButtonPainterUtil.BUTTON_IMAGE_GAP_Y, buttonFaceName);
//		buttonFace.setFixedText(text);
//		buttonFace.setMode(ActivationMode.NORMAL);
//		button.setFont((FontSupport.KEYBOARD_SPECIAL_KEY_FONT));
		button.setForeground(ColorConstants.TEXT_COLOR);
//		button.setCenterImage(true);
//		button.addFace(buttonFace);
		button.setPreferredSize(new Dimension(width - 16, height - 2));
		return button;
	}

	public static MyButton createAltGrButton(Component comp) {
		return createLockedButton(comp, ButtonConstants.BUTTON_WIDTH_RECT_KEYBOARD, ButtonConstants.BUTTON_HEIGHT_RECT_KEYBOARD, "Alt", BUTTON_FACE_NAME_BUTTON_ALTGR);
	}

	private static final Locale LOCALE_DEFAULT = Locale.ENGLISH;

	private static final KeyLineListsCache KEY_LINE_LISTS_CACHE = new KeyLineListsCache();

	/**
	 * Standard keys english
	 * 
	 * @return
	 */
	public static List<List<MyKey>> generateKeys(Locale locale) {
		List<List<MyKey>> keyLineList = KEY_LINE_LISTS_CACHE.getKeyLineList(locale);
		if (keyLineList == null) {
			keyLineList = new ArrayList<>();
			Document xml = getKeyBoardDefinitionDocument(locale);
			if (xml == null) {
				xml = getKeyBoardDefinitionDocument(LOCALE_DEFAULT);
			}
			if (xml != null) {
				if (!LanguageUtil.parseKeyBoard(xml, keyLineList)) {
					logger.error("Error generating alphanum keys (parsing)");
				}
			} else {
				logger.error("Error generating alphanum keys (reading)");
			}
			KEY_LINE_LISTS_CACHE.putKeyLineList(locale, keyLineList);
		}
		return keyLineList;
	}

	public static Document getKeyBoardDefinitionDocument(Locale locale) {
		String fileName = getKeyBoardDefinitionFileName(locale);
		Document doc = null;
		if (!StringUtil.isNullOrEmpty(fileName)) {
			doc = FileLoaderUtil.getDocFromXml(fileName);
		}
		if (doc == null) {
			fileName = getKeyBoardDefinitionFileName(LOCALE_DEFAULT);
			doc = FileLoaderUtil.getDocFromXml(fileName);
		}
		return doc;
	}

	private static final Map<Locale, String> alphaNumDefinitionFiles = new HashMap<Locale, String>(16);
	private static final Map<Locale, Map<String, String>> numDefinitionFiles = new HashMap<Locale, Map<String, String>>(6);

	public static final String BUTTON_VALUE_OFF = "0";

	private static String getKeyBoardDefinitionFileName(Locale locale) {
		if (alphaNumDefinitionFiles.isEmpty()) {
			String path = getDefinitionFile("KeyBoards.xml");
			Document doc = FileLoaderUtil.getDocFromXml(path);
			LanguageUtil.parseKeyBoardDefinitionFiles(doc, alphaNumDefinitionFiles);
		}

		String name = alphaNumDefinitionFiles.get(locale);
		return getDefinitionFile(name);
	}

	private static String getDefinitionFile(String name) {
		return FileService.getPath(PathResource.RESOURCE) + "xml" + File.separatorChar + "keys" + File.separatorChar + name;
	}

	private static String getNumPadDefinitionFileName(Locale locale, String type) {
		if (numDefinitionFiles.isEmpty()) {
			String path = getDefinitionFile("NumPads.xml");
			Document doc = FileLoaderUtil.getDocFromXml(path);
			LanguageUtil.parseNumPadDefinitionFiles(doc, numDefinitionFiles);
		}
		Map<String, String> resultMap = numDefinitionFiles.get(locale);
		if (resultMap == null) {
			logger.info(MyLogger.OBSERVER_MARKER, "try to use default definition file locale={} type={}", locale, type);
			resultMap = numDefinitionFiles.get(LOCALE_DEFAULT);
		}
		if (resultMap != null) {
			return resultMap.get(type);
		} else {
			logger.error("Invalid num pad definition={} Type={}", locale, type);
			return null;
		}
	}

	public static Document getNumPadDefinitionDocument(Locale locale, String type) {
		String fileName = getNumPadDefinitionFileName(locale, type);
		Document doc = null;
		if (!StringUtil.isNullOrEmpty(fileName)) {
			doc = FileLoaderUtil.getDocFromXml(fileName);
		}
		return doc;
	}

	/**
	 * NumPad keys type INT
	 * 
	 * @return
	 */
	private static List<List<MyKey>> generateNumPadKeys(Locale locale, String type) {
		List<List<MyKey>> numKeyLineList = new ArrayList<List<MyKey>>();
		Document xmlNum = getNumPadDefinitionDocument(locale, type);
		if (xmlNum != null) {
			if (!LanguageUtil.parseKeyBoard(xmlNum, numKeyLineList)) {
				logger.error("Error generating num keys (parsing)");
			}
		} else {
			logger.error("Error generating num keys (reading)");
		}
		return numKeyLineList;
	}

	public enum KeyBoardType {
		PASSWORD,
		USER,
		SAVE,
		GENERAL,
		NUMPAD,
		SEARCH,
		COMMIT
	}

	public static KeyBoardDefinition getKeyBoardDefinition(KeyBoardType type, Locale locale) {
		KeyBoardDefinition def = new KeyBoardDefinition(type.toString(), locale);
		switch (type) {
		case PASSWORD:
			def.setPassword(true);
			def.setOverlayName(ImageConstants.IMAGE_OVERLAY_NAME_BUTTON_LOGIN);
			def.setInputItem(InputItemConstants.ITEM_OK);
			def.setKeyList(KeyBoardUtil.generateKeys(locale));
			break;
		case USER:
			def.setPassword(false);
			def.setOverlayName(ImageConstants.IMAGE_OVERLAY_NAME_BUTTON_LOGIN);
			def.setInputItem(InputItemConstants.ITEM_KEYBOARD_LOGIN);
			def.setKeyList(KeyBoardUtil.generateKeys(locale));
			break;
		case COMMIT:
			def.setPassword(false);
			def.setOverlayName(ImageConstants.IMAGE_OVERLAY_NAME_COMMIT);
			def.setInputItem(InputItemConstants.ITEM_KEYBOARD_COMMIT);
			def.setKeyList(KeyBoardUtil.generateKeys(locale));
			break;
		case SEARCH:
			def.setPassword(false);
			def.setOverlayName(ImageConstants.IMAGE_OVERLAY_NAME_BUTTON_SEARCH);
			def.setInputItem(InputItemConstants.ITEM_OK);
			def.setKeyList(KeyBoardUtil.generateKeys(locale));
			break;
		case SAVE:
		case GENERAL:
		default:
			def.setPassword(false);
			def.setOverlayName(ImageConstants.IMAGE_OVERLAY_NAME_EDIT);
			def.setInputItem(InputItemConstants.ITEM_KEYBOARD_SAVE);
			def.setKeyList(KeyBoardUtil.generateKeys(locale));
			break;
		}

		return def;
	}

	public static KeyBoardDefinition getNumPadDefinition(Locale locale, String type) {
		KeyBoardDefinition def = new KeyBoardDefinition(KeyBoardType.NUMPAD.toString() + type, locale);
		def.setPassword(false);
		def.setInputItem(InputItemConstants.ITEM_DEFAULT);
		def.setKeyList(KeyBoardUtil.generateNumPadKeys(locale, type));
		return def;
	}

}
