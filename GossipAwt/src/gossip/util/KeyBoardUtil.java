package gossip.util;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import gossip.button.DefaultButtonFace;
import gossip.button.DefaultButtonFace.ActivationMode;
import gossip.config.ColorConstants;
import gossip.config.DimensionConstants;
import gossip.config.FontConstants;
import gossip.config.ImageConstants;
import gossip.config.InputItemConstants;
import gossip.inputelement.InputItemId;
import gossip.key.MyKey;
import gossip.keyboard.KeyBoardDefinition;
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

	public static boolean isInputChar(String faceName) {
		if (faceName == null) {
			return false;
		} else {
			return faceName.startsWith(BUTTON_FACE_NAME_BUTTON_INPUT_CHAR);
		}
	}

	/**
	 * 
	 */
	public static MyButton createBackspaceButton() {
		DefaultButtonFace buttonFace = DefaultButtonFace.createButtonFace(BUTTON_FACE_NAME_BUTTON_BACKSPACE);
		buttonFace.setOverlay(ImageUtil.getImage(ImageConstants.IMAGE_NAME_BUTTON_OVERLAY_BACKSPACE));
		MyButton button = KeyBoardUtil.createMyButton();
		button.setPreferredSize(DimensionConstants.BUTTON_FACE_DIMENSION_BACKSPACE);
		button.setCenterImage(true);
		button.addFace(buttonFace);
		return button;
	}

	public static MyButton createCursorLeftButton() {
		DefaultButtonFace buttonFace = DefaultButtonFace.createButtonFace(BUTTON_FACE_NAME_BUTTON_LEFT);
		buttonFace.setOverlay(ImageUtil.getImage(ImageConstants.IMAGE_NAME_BUTTON_OVERLAY_CURSOR_LEFT));

		MyButton button = KeyBoardUtil.createMyButton();
		button.setPreferredSize(DimensionConstants.BUTTON_FACE_DIMENSION_CURSOR);
		button.setCenterImage(true);
		button.addFace(buttonFace);
		button.setForeground(ColorConstants.TEXT_COLOR);
		return button;
	}

	public static MyButton createCursorRightButton() {
		DefaultButtonFace buttonFace = DefaultButtonFace.createButtonFace(BUTTON_FACE_NAME_BUTTON_RIGHT);
		buttonFace.setOverlay(ImageUtil.getImage(ImageConstants.IMAGE_NAME_BUTTON_OVERLAY_CURSOR_RIGHT));

		MyButton button = KeyBoardUtil.createMyButton();
		button.setPreferredSize(DimensionConstants.BUTTON_FACE_DIMENSION_CURSOR);
		button.setCenterImage(true);
		button.addFace(buttonFace);
		button.setForeground(ColorConstants.TEXT_COLOR);
		return button;
	}

	public static MyButton createPadButton(String text, String buttonFaceName) {
		return createPadButton(text, text, text, buttonFaceName, buttonFaceName, buttonFaceName);
	}

	public static MyButton createPadButton(String text, String shiftText, String altGrText) {
		return createPadButton(text, shiftText, altGrText, BUTTON_FACE_NAME_BUTTON_INPUT_CHAR, BUTTON_FACE_NAME_BUTTON_INPUT_CHAR_SHIFT, BUTTON_FACE_NAME_BUTTON_INPUT_CHAR_ALTGR);
	}

	private static MyButton createPadButton(String text, String shiftText, String altGrText, String buttonFaceName, String buttonFaceNameShift, String buttonFaceNameGr) {
		MyButton button = KeyBoardUtil.createMyButton();
		if (!StringUtil.isNullOrEmpty(buttonFaceName)) {
			DefaultButtonFace buttonFace = DefaultButtonFace.createButtonFace(buttonFaceName);
			buttonFace.setFixedText(text);
			button.addFace(buttonFace);
		}
		if (!StringUtil.isNullOrEmpty(buttonFaceNameShift)) {
			DefaultButtonFace buttonFaceShift = DefaultButtonFace.createButtonFace(buttonFaceNameShift);
			buttonFaceShift.setFixedText(shiftText);
			button.addFace(buttonFaceShift);
		}

		if (!StringUtil.isNullOrEmpty(buttonFaceNameGr)) {
			DefaultButtonFace buttonFaceAltGr = DefaultButtonFace.createButtonFace(buttonFaceNameGr);
			buttonFaceAltGr.setFixedText(altGrText);
			button.addFace(buttonFaceAltGr);
		}

		button.setCenterImage(true);
		button.setCurrentFace(buttonFaceName);
		return button;
	}

	public static MyButton createSpaceButton() {
		DefaultButtonFace buttonFace = DefaultButtonFace.createButtonFace(BUTTON_FACE_NAME_BUTTON_SPACE);
		MyButton button = KeyBoardUtil.createMyButton();
		button.setCenterImage(true);
		button.addFace(buttonFace);
		button.setPreferredSize(new Dimension(DimensionConstants.BUTTON_FACE_SPACE_WIDTH, DimensionConstants.BUTTON_FACE_SPACE_HEIGHT));
		return button;
	}

	public static MyButton createShiftButton() {
		MyButton button = KeyBoardUtil.createMyButton();
		DefaultButtonFace buttonFace = DefaultButtonFace.createButtonFace(BUTTON_FACE_NAME_BUTTON_SHIFT);
		buttonFace.setOverlay(ImageUtil.getImage(ImageConstants.OVERLAY_SHIFT));
		button.setCenterImage(true);
		button.addFace(buttonFace);
		button.setPreferredSize(DimensionConstants.BUTTON_FACE_DIMENSION_SHIFT);
		return button;
	}

	public static MyButton createLockedButton(String text, String buttonFaceName) {
		MyButton button = KeyBoardUtil.createMyButton();
		DefaultButtonFace buttonFace = DefaultButtonFace.createButtonFace(buttonFaceName);
		buttonFace.setFixedText(text);
		buttonFace.setMode(ActivationMode.NORMAL);
		button.setCenterImage(true);
		button.addFace(buttonFace);
		return button;
	}

	public static MyButton createAltGrButton() {
		return createLockedButton("Alt", BUTTON_FACE_NAME_BUTTON_ALTGR);
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

	private static final Map<Locale, String> alphaNumDefinitionFiles = new HashMap<>(16);
	private static final Map<Locale, Map<String, String>> numDefinitionFiles = new HashMap<>(6);

	public static final String BUTTON_VALUE_OFF = "0";

	private static String getKeyBoardDefinitionFileName(Locale locale) {
		if (alphaNumDefinitionFiles.isEmpty()) {
			String path = ResourcesUtil.getDefinitionFileName("KeyBoards.xml");
			Document doc = FileLoaderUtil.getDocFromXml(path);
			LanguageUtil.parseKeyBoardDefinitionFiles(doc, alphaNumDefinitionFiles);
		}

		String name = alphaNumDefinitionFiles.get(locale);
		return ResourcesUtil.getDefinitionFileName(name);
	}

	private static String getNumPadDefinitionFileName(Locale locale, String type) {
		if (numDefinitionFiles.isEmpty()) {
			String path = ResourcesUtil.getDefinitionFileName("NumPads.xml");
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
		List<List<MyKey>> numKeyLineList = new ArrayList<>();
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

	public static void setSingleButtonFaceItem(MyButton button, InputItemId id) {
		Set<String> set = button.getButtonFaceKeySet();
		for (String string : set) {
			button.getButtonFace(string).setInputItemId(id);
		}
	}

	public static MyButton createMyButton() {
		MyButton b = new MyButton();
		b.setPreferredSize(DimensionConstants.DEFAULT_KEY_DIMENSION);
		b.setForeground(ColorConstants.TEXT_COLOR);
		b.setBackground(ColorConstants.BUTTON_COLOR_DEFAULT);
		b.setFont(FontConstants.KEYBOARD_FONT);
		return b;
	}
}
