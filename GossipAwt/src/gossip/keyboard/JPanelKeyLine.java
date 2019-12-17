package gossip.keyboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import gossip.button.ButtonFaceListener;
import gossip.button.DefaultButtonFace.ActivationMode;
import gossip.config.ButtonConstants;
import gossip.config.FontConstants;
import gossip.config.InputItemConstants;
import gossip.event.KeyListenerInterface;
import gossip.inputelement.InputItemId;
import gossip.key.MyKey;
import gossip.lib.panel.MyButton;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.manager.LanguageManager;
import gossip.util.KeyBoardUtil;


public class JPanelKeyLine extends JPanelDisposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3222575391084072967L;

	private final List<MyKey> keys;

	private boolean isPassword = false;

	/**
	 * @param isPassword
	 *            the isPassword to set
	 */
	public void setPassword(boolean isPassword) {
		if (this.isPassword != isPassword) {
			this.isPassword = isPassword;
			for (MyButton b : buttonList) {
				b.setBlocked(isPassword);
			}
		}
	}

	private final List<KeyListenerInterface> keyListenerList = new ArrayList<>();
	private final List<MyButton> buttonList = new ArrayList<>();

	public JPanelKeyLine(List<MyKey> keys) {
		this.keys = keys;
		init();
	}

	public void addKeyListener(KeyListenerInterface listener) {
		keyListenerList.add(listener);
	}

	private void fireKeyEvent(String key, String text) {
		synchronized (keyListenerList) {
			for (KeyListenerInterface element : keyListenerList) {
				element.onKeyPressed(key, text);
			}
		}
	}

	private void init() {
		buildView();
	}

	@Override
	public void dispose() {
		super.dispose();
		for (MyButton b : buttonList) {
			b.dispose();
		}
		buttonList.clear();
		keyListenerList.clear();
	}

	private void buildView() {
		setLayout(new FlowLayout(SwingConstants.TOP, 0, 0));
		setBorder(BorderFactory.createEmptyBorder());
		setOpaque(true);
		setBackground(Color.green);
		setPreferredSize(new Dimension(10,10));
		
		for (int i = 0; i < keys.size(); i++) {
			MyKey keyElement = keys.get(i);
			String k = keyElement.getKeyText();
			String sk = keyElement.getShiftKeyText();
			String ak = keyElement.getAltKeyText();
			int size = keyElement.getSize();
			boolean isLocked = keyElement.isLocked();
			MyButton button = null;
			if (k.equals(KeyBoardUtil.BUTTON_NAME_BACKSPACE)) {
				button = KeyBoardUtil.createBackspaceButton(this);
			} else if (k.equals(KeyBoardUtil.BUTTON_NAME_SHIFT)) {
				button = KeyBoardUtil.createShiftButton(this);
			} else if (k.equals(KeyBoardUtil.BUTTON_NAME_ALTGR)) {
				button = KeyBoardUtil.createAltGrButton(this);
			} else if (k.equals(KeyBoardUtil.BUTTON_NAME_SPACE)) {
				button = KeyBoardUtil.createSpaceButton(this);
			} else if (k.equals(KeyBoardUtil.BUTTON_NAME_CURSOR_LEFT)) {
				button = KeyBoardUtil.createCursorLeftButton(this);
			} else if (k.equals(KeyBoardUtil.BUTTON_NAME_CURSOR_RIGHT)) {
				button = KeyBoardUtil.createCursorRightButton(this);
			} else if (k.equals(KeyBoardUtil.BUTTON_NAME_OFF)) {
				button = KeyBoardUtil.createPadButton(this, size, null, k);
				button.setFont(FontConstants.KEYBOARD_SPECIAL_KEY_FONT);
				LanguageManager.setSingleButtonFaceItem(button, InputItemConstants.ITEM_OFF);
			} else if (k.startsWith("#") && k.length() > 1) {
				if (isLocked) {
					button = KeyBoardUtil.createLockedButton(this, size, ButtonConstants.BUTTON_HEIGHT_RECT_KEYBOARD, null, k);
				} else {
					button = KeyBoardUtil.createPadButton(this, size, null, k);
				}
				button.setFont(FontConstants.KEYBOARD_SPECIAL_KEY_FONT);
				InputItemId id = LanguageManager.createKeyId(k);
				LanguageManager.setSingleButtonFaceItem(button, id);
			} else {
				button = KeyBoardUtil.createPadButton(this, k, sk, ak);
			}

			for (String key : button.getButtonFaceKeySet()) {
				button.getButtonFace(key).addButtonFaceListener(new ButtonFaceListener() {
					@Override
					public void onButtonFaceChanged(boolean isPressed, String name, String text) {
						if (isPressed) {
							fireKeyEvent(name, text);
						}
					}

					private final UUID objectId = UUID.randomUUID();

					@Override
					public UUID getUuid() {
						return objectId;
					}

				});
			}
			button.setBlocked(isPassword);
			add(button);
			buttonList.add(button);
		}
		setInputMode(InputMode.NORMAL);
	}

	public enum InputMode {
		NORMAL,
		SHIFT,
		SHIFT_LOCK,
		ALTGR
	}

	public void setInputMode(InputMode mode) {
		switch (mode) {
			case SHIFT:
				for (MyButton button : buttonList) {
					if (KeyBoardUtil.isInputChar(button.getCurFaceKey())) {
						button.setCurrentFace(KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_INPUT_CHAR_SHIFT);
					} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_SHIFT.equals(button.getCurFaceKey())) {
						button.getCurFace().setMode(ActivationMode.ACTIVE);
					} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_ALTGR.equals(button.getCurFaceKey())) {
						button.getCurFace().setMode(ActivationMode.NORMAL);
					}
				}
				break;

			case SHIFT_LOCK:
				for (MyButton button : buttonList) {
					if (KeyBoardUtil.isInputChar(button.getCurFaceKey())) {
						button.setCurrentFace(KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_INPUT_CHAR_SHIFT);
					} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_SHIFT.equals(button.getCurFaceKey())) {
						button.getCurFace().setMode(ActivationMode.LOCKED);
					} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_ALTGR.equals(button.getCurFaceKey())) {
						button.getCurFace().setMode(ActivationMode.NORMAL);
					}
				}
				break;

			case ALTGR:
				for (MyButton button : buttonList) {
					if (KeyBoardUtil.isInputChar(button.getCurFaceKey())) {
						button.setCurrentFace(KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_INPUT_CHAR_ALTGR);
					} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_SHIFT.equals(button.getCurFaceKey())) {
						button.getCurFace().setMode(ActivationMode.NORMAL);
					} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_ALTGR.equals(button.getCurFaceKey())) {
						button.getCurFace().setMode(ActivationMode.ACTIVE);
					}
				}
				break;

			case NORMAL:
			default:
				for (MyButton button : buttonList) {
					if (KeyBoardUtil.isInputChar(button.getCurFaceKey())) {
						button.setCurrentFace(KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_INPUT_CHAR);
					} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_SHIFT.equals(button.getCurFaceKey())) {
						button.getCurFace().setMode(ActivationMode.NORMAL);
					} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_ALTGR.equals(button.getCurFaceKey())) {
						button.getCurFace().setMode(ActivationMode.NORMAL);
					}
				}
				break;
		}

	}
}
