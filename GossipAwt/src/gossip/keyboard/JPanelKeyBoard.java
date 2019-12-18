package gossip.keyboard;

import java.awt.BorderLayout;
import java.awt.event.HierarchyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.logging.log4j.Logger;

import gossip.config.DimensionConstants;
import gossip.event.KeyBoardEvent.KeyBoardResultType;
import gossip.event.KeyBoardResultEvent;
import gossip.event.KeyBoardResultListener;
import gossip.event.KeyListenerInterface;
import gossip.key.MyKey;
import gossip.keyboard.JPanelKeyLine.InputMode;
import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;
import gossip.lib.panel.DefaultAlertSupport;
import gossip.lib.panel.JPanelAlertBox;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.util.MyLogger;
import gossip.rule.InputRule.SEVERITY;
import gossip.util.DisposableUtil;
import gossip.util.KeyBoardUtil;

public class JPanelKeyBoard extends JPanelDisposable implements InputPanelInterface {

	private static final long serialVersionUID = 6151024246312574475L;

	private InputPanelModel createInputPanelModel() {
		return new DefaultInputPanelModel() {

			private static final long serialVersionUID = 8817498126958050527L;

			@Override
			public void setParameter(InputValue value) {
				super.setParameter(value);
				getInput().setInputText(super.getParameterValueString());
			}

		};
	}

	public static final Logger logger = MyLogger.getLog(JPanelKeyBoard.class);

	

	/*
	 * Buchstaben Info -password -icon (Overlay Name) -Headline Text -Question Text
	 * -Vorgabe String setzen Events KeyBoardResultListener ON_ENTER ON_CANCEL
	 */

	private JPanelDisposable mainPanel;
	private JPanelInputKeyBoard input;
	private final List<KeyBoardResultListener> resultListenerList = new ArrayList<>();
	private final List<JPanelKeyLine> keyLineList = new ArrayList<>();
	private InputPanelModel model;

	private InputMode mode = InputMode.NORMAL;

	private JPanelDisposable keyPanel;

	private Object modeSelected;

	public JPanelKeyBoard() {
		init();
	}

	@Override
	public void addKeyBoardResultListener(KeyBoardResultListener listener) {
		resultListenerList.add(listener);
	}

	private void buildView() {
		setLayout(new BorderLayout(0, 0));
		setBorder(BorderFactory.createEmptyBorder());
		setOpaque(false);
		// init ui
		add(getMainPanel(), BorderLayout.CENTER);
	}

	private void cancel() {
		logger.info("Keyboard cancel");
		fireKeyBoardResultEvent(new KeyBoardResultEvent(KeyBoardResultType.ON_CANCEL, getInput().getInputText()));
		cleanInput();
	}

	@Override
	public void cleanInput() {
		getInput().cleanInputText();
	}

	private void enter() {
		if (!isEnterBlocked) {
			logger.info("Keyboard enter");
			KeyBoardResultEvent event = new KeyBoardResultEvent(KeyBoardResultType.ON_ENTER, getInput().getInputText());
			event.setSelection(getModeSelected());
			fireKeyBoardResultEvent(event);
			cleanInput();
		} else {
			logger.info("Keyboard enter blocked");
		}
		
	}

	private void fireKeyBoardResultEvent(KeyBoardResultEvent event) {
		logger.debug("Fire event keyBoard: {} ", event);
		synchronized (resultListenerList) {
			for (KeyBoardResultListener element : resultListenerList) {
				element.onKeyBoardResult(event);
			}
		}
	}

	private JPanelInputKeyBoard getInput() {
		if (input == null) {
			input = new JPanelInputKeyBoard();
			input.setPreferredSize(DimensionConstants.INPUT_KEYBOARD_SIZE);
			input.addKeyBoardEventListener(event -> {
				switch (event.getType()) {
				case ON_CANCEL:
					cancel();
					break;
				case ON_ENTER:
					enter();
					break;
				default:
					break;
				}

			});

			input.addDocumentListener(new DocumentListener() {
				@Override
				public void changedUpdate(DocumentEvent e) {
					verify();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					verify();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					verify();
				}
			});
		}
		return input;
	}

	protected void verify() {
		// TODO Auto-generated method stub
		logger.info("verify changed text");
	}

	private KeyListenerInterface keyBoardListener = (key, text) -> {
		logger.info("Key pressed={} text={}", key, text);
		if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_SHIFT.equals(key)) {
			toggleShift();
		} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_ALTGR.equals(key)) {
			toggleAltGr();
		} else if (KeyBoardUtil.isInputChar(key)) {
			input.insertText(text);
			if (!mode.equals(InputMode.SHIFT_LOCK)) {
				setInputMode(InputMode.NORMAL);
			}
		} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_SPACE.equals(key)) {
			input.insertText(" ");
		} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_BACKSPACE.equals(key)) {
			input.backspace();
		} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_LEFT.equals(key)) {
			input.cursorMove(-1);
		} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_RIGHT.equals(key)) {
			input.cursorMove(1);
		} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_CANCEL.equals(key)) {
			cancel();
		} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_ENTER.equals(key)) {
			enter();
		} else if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_QUIT.equals(key)) {
			cancel();
		}
	};

	private KeyBoardDefinition curDef;

	private JPanelDisposable getKeyPanel() {
		if (keyPanel == null) {
			keyPanel = new JPanelDisposable();
			keyPanel.setLayout(new BorderLayout());
			keyPanel.setOpaque(false);
		}
		return keyPanel;
	}

	private void toggleAltGr() {
		if (InputMode.ALTGR.equals(mode)) {
			setInputMode(InputMode.NORMAL);
		} else {
			setInputMode(InputMode.ALTGR);
		}
	}

	private void toggleShift() {
		if (InputMode.SHIFT.equals(mode)) {
			setInputMode(InputMode.SHIFT_LOCK);
		} else if (InputMode.SHIFT_LOCK.equals(mode)) {
			setInputMode(InputMode.NORMAL);
		} else {
			setInputMode(InputMode.SHIFT);
		}
	}

	public Object getModeSelected() {
		return modeSelected;
	}

	private JPanelDisposable getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanelDisposable();
			mainPanel.setLayout(new BorderLayout());
			mainPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			mainPanel.setOpaque(false);

			JPanel p0 = new JPanelDisposable();

			p0.setLayout(new BorderLayout());
			p0.setOpaque(false);
			p0.add(getInput(), BorderLayout.CENTER);
			p0.add(getAlertPanel(), BorderLayout.NORTH);

			mainPanel.add(p0, BorderLayout.NORTH);
			mainPanel.add(getKeyPanel(), BorderLayout.CENTER);
		}
		return mainPanel;
	}

	@Override
	public InputValue getParameter() {
		return model != null ? model.getParameter() : null;
	}

	@Override
	public String getParameterValueString() {
		return getParameter() != null ? getParameter().getValueString() : "";
	}

	/**
	 * @return the quetionText
	 */
	@Override
	public String getInputLabelText() {
		return getInput().getName();
	}

	private void init() {
		model = createInputPanelModel();
		buildView();
		postInit();
		addHierarchyListener(e -> {
			if ((HierarchyEvent.SHOWING_CHANGED & e.getChangeFlags()) != 0 && isShowing()) {
				requestTextInputFocus();
			}
		});
	}

	private void requestTextInputFocus() {
		ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("request focus") {
			@Override
			public Boolean startJob() {
				if (getInput().isPassword()) {
					cleanInput();
				}
				getInput().requestTextInputFocus();
				return true;
			}
		});
	}

	private void postInit() {
		setAlertPanelVisible(false);
	}

	private void setInputMode(InputMode mode) {
		this.mode = mode;
		synchronized (keyLineList) {
			for (JPanelKeyLine jPanelKeyLine : keyLineList) {
				jPanelKeyLine.setInputMode(mode);
			}
		}
		repaint();
	}

	/**
	 * 
	 * @param def
	 */
	public synchronized void setKeyBoardDefinition(KeyBoardDefinition def) {
		if (def != null && !def.compareTo(curDef)) {
			logger.info("keyboard definition changed ={}", def);
			curDef = def;
			setDefinition(def);
		}
	}

	/**
	 * 
	 * @param def
	 */
	private void setDefinition(KeyBoardDefinition def) {
		getInput().setPassword(def.isPassword());
		JPanelDisposable p;
		synchronized (keyLineList) {
			keyLineList.clear();
			p = createKeysPanel(def);
		}

		JPanelDisposable kPanel = getKeyPanel();
		DisposableUtil.disposeContainerContent(kPanel);
		kPanel.add(p, BorderLayout.SOUTH);
		kPanel.add(Box.createVerticalBox(), BorderLayout.CENTER);
	}

	private JPanelDisposable createKeysPanel(KeyBoardDefinition definition) {
		JPanelDisposable pKeys = new JPanelDisposable();
		pKeys.setLayout(new BoxLayout(pKeys, BoxLayout.PAGE_AXIS));
		pKeys.setOpaque(false);

		if (definition != null) {
			for (int i = 0; i < definition.getKeyList().size(); i++) {
				List<MyKey> keys = definition.getKeyList().get(i);
				JPanelKeyLine jPanelKeyLine = new JPanelKeyLine(keys);
				jPanelKeyLine.setPassword(definition.isPassword());
				pKeys.add(jPanelKeyLine);
				keyLineList.add(jPanelKeyLine);
				jPanelKeyLine.addKeyListener(keyBoardListener);
			}
		}
		return pKeys;
	}

	@Override
	public void setParameter(InputValue value) {
		if (model != null) {
			model.setParameter(value);
		}
	}

	/**
	 * @param quetionText the quetionText to set
	 */
	@Override
	public void setInputLabelText(String quetionText) {
		getInput().setInputName(quetionText);
	}

	private boolean isEnterBlocked = false;

	public void setEnterBlocked(boolean isEnterBlocked) {
		if (this.isEnterBlocked != isEnterBlocked) {
			this.isEnterBlocked = isEnterBlocked;
		}
	}

	private JPanelAlertBox getAlertPanel() {
		return alertSupport.getAlertPanel();
	}

	private final DefaultAlertSupport alertSupport = new DefaultAlertSupport(this);

	public void setAlertPanelVisible(boolean mode) {
		alertSupport.setAlertPanelVisibleRequested(mode);
	}

	public void setAlertText(String text) {
		alertSupport.setAlertText(text);
	}

	public void setAlertSeverity(SEVERITY severity) {
		alertSupport.setAlertSeverity(severity);
	}

	@Override
	public void dispose() {
		super.dispose();
		synchronized (keyLineList) {
			for (JPanelKeyLine line : keyLineList) {
				line.dispose();
			}
			keyLineList.clear();
		}
		alertSupport.dispose();
	}

}
