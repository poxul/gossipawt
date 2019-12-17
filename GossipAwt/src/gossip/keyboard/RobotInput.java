package gossip.keyboard;

import java.awt.AWTException;
import java.awt.AWTKeyStroke;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.apache.logging.log4j.Logger;

import gossip.lib.panel.InputInterface;
import gossip.lib.util.MyLogger;

class RobotInput implements InputInterface {

	private static final Logger logger = MyLogger.getLog(RobotInput.class);

	private Robot robot;
	private Clipboard clippy;

	public RobotInput() {
		init();
	}

	private void init() {
		try {
			robot = new Robot();
			robot.setAutoDelay(10);
		} catch (AWTException e) {
			MyLogger.printExecption(logger, e);
		}
		clippy = Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	@Override
	public void backspace() {
		type(KeyEvent.VK_BACK_SPACE);
	}

	public void insertKeyEvent(int keycode) {
		type(keycode);
	}

	@Override
	public void cursorMove(int step) {
		if (step > 0) {
			type(KeyEvent.VK_LEFT);
		} else {
			type(KeyEvent.VK_RIGHT);
		}
	}

	@Override
	public void cleanInputText() {
		// NOP
	}

	@Override
	public boolean cleanSelected() {
		return false;
	}

	@Override
	public String getInputText() {
		return null;
	}

	@Override
	public void insertText(String text) {
		type(text);
	}

	@Override
	public boolean isPassword() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInputText(String inputText) {
		type(inputText);
	}

	private void press(int keyCode) {
		robot.keyPress(keyCode);
	}

	private void release(int keyCode) {
		robot.keyRelease(keyCode);
	}

	public void setModifier(int keyCode, boolean isSet) {
		if (isSet) {
			press(keyCode);
		} else {
			release(keyCode);
		}
	}

	private void type(int keyCode1, int keyCode2) {
		press(keyCode1);
		press(keyCode2);
		release(keyCode2);
		release(keyCode1);
	}

	private void type(int keyCode) {
		press(keyCode);
		release(keyCode);
	}

	private boolean isAsciiLetter(int c) {
		if (c >= 0x30 && c <= 0x39) { // DIGIT
			return true;
		} else if (c >= 0x41 && c <= 0x5A) { // LETTER upper case
			return true;
		} else if (c >= 0x61 && c <= 0x7A) { // LETTER lower case
			return true;
		} else {
			return false;
		}
	}

	private void type(char c) {
		int kCode = AWTKeyStroke.getAWTKeyStroke(c).getKeyCode();
		if (kCode != KeyEvent.VK_UNDEFINED) {
			type(kCode);
		} else if (isAsciiLetter(c)) {
			try {
				int keyCode = getKeyCode1(c);
				type(keyCode);
			} catch (Exception e) {
				MyLogger.printExecption(logger, e);
			}
		} else {
			try {
				typeKeyCode2(c);
			} catch (Exception e) {
				typeKeyCode3(c);
			}
		}
	}

	private static int getKeyCode1(Character c) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		return Integer.valueOf(Character.toUpperCase(c));
	}

	private void type(String s) {
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			type(chars[i]);
		}

	}

	private void typeKeyCode3(int code) {
		// Transferable clippysContent = clippy.getContents(null);
		try {
			StringSelection selection = new StringSelection(new String(new char[] {
					(char) code
			}));
			clippy.setContents(selection, selection);
			// drückt STRG+V == einfügen
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			// oder wenn das keine Exception wirft
			// robot.keyPress(KeyEvent.VK_PASTE);
			// robot.keyRelease(KeyEvent.VK_PASTE);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// clippy.setContents(clippysContent, null); // zurücksetzen vom alten Kontext
		// gute Ide aber durch das Delay des Robots werden die Daten zu schnell
		// zurueckgesetzt!
	}

	private void typeKeyCode2(char character) {
		switch (character) {
		case '`':
			type(KeyEvent.VK_BACK_QUOTE);
			break;
		case '-':
			type(KeyEvent.VK_MINUS);
			break;
		case '=':
			type(KeyEvent.VK_EQUALS);
			break;
		case '!':
			type(KeyEvent.VK_EXCLAMATION_MARK);
			break;
		case '@':
			type(KeyEvent.VK_AT);
			break;
		case '#':
			type(KeyEvent.VK_NUMBER_SIGN);
			break;
		case '$':
			type(KeyEvent.VK_DOLLAR);
			break;
		case '€':
			type(KeyEvent.VK_EURO_SIGN);
			break;
		case '%':
			type(KeyEvent.VK_SHIFT, KeyEvent.VK_5);
			break;
		case '^':
			type(KeyEvent.VK_CIRCUMFLEX);
			break;
		case '&':
			type(KeyEvent.VK_AMPERSAND);
			break;
		case '*':
			type(KeyEvent.VK_ASTERISK);
			break;
		case '(':
			type(KeyEvent.VK_LEFT_PARENTHESIS);
			break;
		case ')':
			type(KeyEvent.VK_RIGHT_PARENTHESIS);
			break;
		case '_':
			type(KeyEvent.VK_UNDERSCORE);
			break;
		case '+':
			type(KeyEvent.VK_PLUS);
			break;
		case '\t':
			type(KeyEvent.VK_TAB);
			break;
		case '\n':
			type(KeyEvent.VK_ENTER);
			break;
		case '[':
			type(KeyEvent.VK_OPEN_BRACKET);
			break;
		case ']':
			type(KeyEvent.VK_CLOSE_BRACKET);
			break;
		case '\\':
			type(KeyEvent.VK_BACK_SLASH);
			break;
		case '{':
			type(KeyEvent.VK_SHIFT, KeyEvent.VK_OPEN_BRACKET);
			break;
		case '}':
			type(KeyEvent.VK_SHIFT, KeyEvent.VK_CLOSE_BRACKET);
			break;
		case ';':
			type(KeyEvent.VK_SEMICOLON);
			break;
		case ':':
			type(KeyEvent.VK_COLON);
			break;
		case '\'':
			type(KeyEvent.VK_QUOTE);
			break;
		case '"':
			type(KeyEvent.VK_QUOTEDBL);
			break;
		case ',':
			type(KeyEvent.VK_COMMA);
			break;
		case '<':
			type(KeyEvent.VK_LESS);
			break;
		case '.':
			type(KeyEvent.VK_PERIOD);
			break;
		case '>':
			type(KeyEvent.VK_GREATER);
			break;
		case '/':
			type(KeyEvent.VK_SLASH);
			break;
		case ' ':
			type(KeyEvent.VK_SPACE);
			break;
		default:
			throw new IllegalArgumentException("Cannot type character " + character);
		}
	}

}