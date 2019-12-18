package gossip.lib.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import org.apache.logging.log4j.Logger;

import gossip.config.FontConstants;
import gossip.event.KeyBoardEvent;
import gossip.event.KeyBoardEvent.KeyBoardResultType;
import gossip.event.KeyBoardListener;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.panel.disposable.JTextFieldDisposable;
import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;


public abstract class JPanelInputField extends JPanelDisposable implements InputInterface {

	private static final Color INPUT_SELECTION_COLOR = Color.LIGHT_GRAY;
	private static final Color INPUT_TEXT_COLOR = Color.BLACK;
	
	private static final String PASSWORD_INPUT = "PasswordInput";
	private static final String NORMAL_INPUT = "NormalInput";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isPassword = false;

	private JTextFieldDisposable textFieldNormal;
	private JPasswordFieldDisposeable textFieldPassword;

	private static final Logger logger = MyLogger.getLog(JPanelInputField.class);

	/*
	 * We have to jump through some hoops to avoid trying to print non-printing
	 * characters such as Shift. (Not only do they not print, but if you put them in
	 * a String, the characters afterward won't show up in the text area.)
	 */

	// KeyBoardEvent
	private final List<KeyBoardListener> keyBoardEventlistenerList = new ArrayList<>();

	public JPanelInputField() {
		super();
		init();
	}

	public void setPassword(boolean isPassword) {
		if (this.isPassword != isPassword) {
			this.isPassword = isPassword;
			if (isPassword) {
				switchTo(PASSWORD_INPUT);
			} else {
				switchTo(NORMAL_INPUT);
			}
		}
		if (this.isPassword) {
			cleanInputText();
		}
	}

	public void addDocumentListener(DocumentListener listener) {
		getJTextInputPassword().getDocument().addDocumentListener(listener);
		getJTextInputNormal().getDocument().addDocumentListener(listener);
	}

	public void addKeyBoardEventListener(KeyBoardListener listener) {
		synchronized (keyBoardEventlistenerList) {
			keyBoardEventlistenerList.add(listener);
		}
	}

	@Override
	public void cursorMove(int step) {
		JTextComponent tf = getJTextInput();
		String textString = tf.getText();
		int length = textString.length();
		if (length > 0) {
			int caretPosition = tf.getCaretPosition();

			int selStart = tf.getSelectionStart();
			int selEnd = tf.getSelectionEnd();
			if (selStart != selEnd) {
				caretPosition = selEnd;
			}
			caretPosition += step;

			if (caretPosition < 0) {
				caretPosition = 0;
			} else if (caretPosition > length) {
				caretPosition = length;
			}
			tf.setCaretPosition(caretPosition);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void backspace() {
		JTextComponent tf = getJTextInput();
		String textString = tf.getText();

		int length = textString.length();
		if (length > 0 && !cleanSelected()) {
			int start = tf.getCaretPosition();
			int end = start;
			if (start > 0) {
				start--;
			}
			tf.setText(textString.substring(0, start) + textString.substring(end, length));
			tf.setCaretPosition(start);
		}

	}

	protected abstract void buildView();

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void cleanInputText() {
		setInputText("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public boolean cleanSelected() {
		JTextComponent tf = getJTextInput();
		String textString = tf.getText();
		int length = textString.length();
		if (length > 0) {
			int selStart = tf.getSelectionStart();
			int selEnd = tf.getSelectionEnd();
			if (selStart != selEnd) {
				tf.setText(textString.substring(0, selStart) + textString.substring(selEnd, length));
				tf.setCaretPosition(selStart);
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public String getInputText() {
		return getJTextInput().getText();
	}

	private final CardLayout layout = new CardLayout(0, 0);

	private JPanelMyBack cardPanel;
	private KeyListener inputKeyListener = new KeyListener() {

		@Override
		public void keyPressed(KeyEvent e) {
			if (logger.isDebugEnabled()) {
				displayInfo(e, "KEY RELEASED: ");
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (logger.isDebugEnabled()) {
				displayInfo(e, "KEY PRESSED: ");
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				fireKeyBoardEvent(new KeyBoardEvent(KeyBoardResultType.ON_ENTER));
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				fireKeyBoardEvent(new KeyBoardEvent(KeyBoardResultType.ON_CANCEL));
			}
		}

		private void fireKeyBoardEvent(KeyBoardEvent event) {
			synchronized (keyBoardEventlistenerList) {
				for (KeyBoardListener listener : keyBoardEventlistenerList) {
					listener.onKeyBoardResult(event);
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if (logger.isDebugEnabled()) {
				displayInfo(e, "KEY TYPED: ");
			}
		}

		private void displayInfo(KeyEvent e, String keyStatus) {
			// You should only rely on the key char if the event
			// is a key typed event.
			int id = e.getID();
			String keyString;
			if (id == KeyEvent.KEY_TYPED) {
				char c = e.getKeyChar();
				keyString = "key character = '" + c + "'";
			} else {
				int keyCode = e.getKeyCode();
				keyString = "key code = " + keyCode + " (" + KeyEvent.getKeyText(keyCode) + ")";
			}

			int modifiersEx = e.getModifiersEx();
			String modString = "extended modifiers = " + modifiersEx;
			String tmpString = InputEvent.getModifiersExText(modifiersEx);
			if (tmpString.length() > 0) {
				modString += " (" + tmpString + ")";
			} else {
				modString += " (no extended modifiers)";
			}

			String actionString = "action key? ";
			if (e.isActionKey()) {
				actionString += "YES";
			} else {
				actionString += "NO";
			}

			String locationString = "key location: ";
			int location = e.getKeyLocation();
			if (location == KeyEvent.KEY_LOCATION_STANDARD) {
				locationString += "standard";
			} else if (location == KeyEvent.KEY_LOCATION_LEFT) {
				locationString += "left";
			} else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
				locationString += "right";
			} else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
				locationString += "numpad";
			} else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
				locationString += "unknown";
			}

			logger.info(keyStatus + "    " + keyString + "    " + modString + "    " + actionString + "    " + locationString);

		}
	};

	private void switchTo(String panelName) {
		logger.info("input set to panel ={}", panelName);
		layout.show(getJPanelInputCard(), panelName);
		revalidate();
		repaint();
	}

	protected JPanelMyBack getJPanelInputCard() {
		if (cardPanel == null) {
			cardPanel = new JPanelMyBack(Color.WHITE, Color.WHITE);
			cardPanel.setLayout(layout);
			cardPanel.add(getJTextInputNormal(), NORMAL_INPUT);
			cardPanel.add(getJTextInputPassword(), PASSWORD_INPUT);
			cardPanel.setOuterBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
			cardPanel.setOpaque(false);
		}
		return cardPanel;
	}

	private JTextComponent getJTextInput() {
		if (isPassword) {
			return getJTextInputPassword();
		} else {
			return getJTextInputNormal();
		}
	}

	private JTextComponent getJTextInputNormal() {
		if (textFieldNormal == null) {
			textFieldNormal = new JTextFieldDisposable();
			textFieldNormal.setOpaque(true);
			textFieldNormal.setForeground(INPUT_TEXT_COLOR);
			textFieldNormal.setFont(FontConstants.INPUTPANEL_NUMPAD_INPUT_FONT);
			textFieldNormal.setHorizontalAlignment(SwingConstants.RIGHT);
			textFieldNormal.setBorder(BorderFactory.createEmptyBorder());
			textFieldNormal.setSelectionColor(INPUT_SELECTION_COLOR);
			textFieldNormal.addKeyListener(inputKeyListener);
		}
		return textFieldNormal;
	}

	private JTextComponent getJTextInputPassword() {
		if (textFieldPassword == null) {
			textFieldPassword = new JPasswordFieldDisposeable();
			textFieldPassword.setOpaque(true);
			textFieldPassword.setForeground(INPUT_TEXT_COLOR);
			textFieldPassword.setFont(FontConstants.INPUTPANEL_NUMPAD_INPUT_FONT);
			textFieldPassword.setHorizontalAlignment(SwingConstants.RIGHT);
			textFieldPassword.setBorder(BorderFactory.createEmptyBorder());
			textFieldPassword.setSelectionColor(INPUT_SELECTION_COLOR);
			textFieldPassword.addKeyListener(inputKeyListener);
		}
		return textFieldPassword;
	}

	@Override
	public void dispose() {
		super.dispose();
		keyBoardEventlistenerList.clear();
	}

	private void init() {
		buildView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void insertText(String text) {
		cleanSelected();
		JTextComponent tf = getJTextInput();
		String textString = tf.getText();

		int length = textString.length();
		if (length > 0) {
			int start = tf.getCaretPosition();
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append(textString.substring(0, start));
			strBuilder.append(text);
			strBuilder.append(textString.substring(start, length));
			tf.setText(strBuilder.toString());
			tf.setCaretPosition(start + text.length());
		} else {
			tf.setText(text);
			tf.setCaretPosition(text.length());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public boolean isPassword() {
		return isPassword;
	}

	public void requestTextInputFocus() {
		if (getJTextInput().hasFocus()) {
			logger.debug("Text input has focus");
		} else {
			boolean rc = getJTextInput().requestFocusInWindow();
			logger.debug("Request text input focus ={}", rc);
		}
		selectAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void setInputText(String inputText) {
		if (inputText == null) {
			inputText = "";
		}
		String old = getJTextInput().getText();
		if (!StringUtil.compare(inputText, old)) {
			logger.info("input text changed ={}", inputText);
			getJTextInput().setText(inputText);
		}
		selectAll();
	}

	private void selectAll() {
		JTextComponent input = getJTextInput();
		String inputText = input.getText();
		if (!StringUtil.isNullOrEmpty(inputText)) {
			logger.debug("select all =" + inputText);
			getJTextInput().setCaretPosition(inputText.length());
			getJTextInput().setSelectionStart(0);
			getJTextInput().setSelectionEnd(inputText.length());
		}
	}

}