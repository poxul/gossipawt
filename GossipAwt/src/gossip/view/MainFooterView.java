package gossip.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import org.apache.logging.log4j.Logger;

import gossip.config.DimensionConstants;
import gossip.config.ImageConstants;
import gossip.config.InputItemConstants;
import gossip.data.AwtBroker;
import gossip.lib.panel.button.ButtonFaceListener;
import gossip.lib.panel.button.MyButton;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;
import gossip.util.MyButtonUtil;

public class MainFooterView extends JPanelDisposable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(MainFooterView.class);

	private JPanelDisposable emotesPanel;

	private ButtonFaceListener emoteListener = new ButtonFaceAdapter() {

		@Override
		public void onButtonFaceChanged(boolean isReleased, String name, String text) {
			if (isReleased) {
				send(text);
			}
		}
	};

	private ButtonFaceListener functionListener = new ButtonFaceAdapter() {

		@Override
		public void onButtonFaceChanged(boolean isReleased, String name, String text) {
			if (isReleased) {
				function(name);
			}
		}

	};

	private JPanelDisposable dialogsPanel;

	public MainFooterView() {
		init();
	}

	protected void function(String name) {
		if (StringUtil.compare(name, InputItemConstants.ITEM_DICTIONARY.nameValue())) {
			showDictionary(true);
		} else if (StringUtil.compare(name, InputItemConstants.ITEM_KEYBOARD.nameValue())) {
			showKeyboard(true);
		} else {
			logger.error("unknown function: {}", name);
		}

	}

	private void showKeyboard(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private void showDictionary(boolean b) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Emotes panel , cancel (hide) button , show dictionary button, show keyboard
	 * button
	 */
	private void buildView() {
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder());
		setPreferredSize(new Dimension(100, DimensionConstants.FOOTER_HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(getPanelEmotes());
		add(getPanelDialogs());
	}

	/**
	 * Dictionary and Keyboard
	 * 
	 * @return
	 */
	private JPanelDisposable getPanelDialogs() {
		if (dialogsPanel == null) {
			dialogsPanel = new JPanelDisposable();
			dialogsPanel.setLayout(new FlowLayout());
			dialogsPanel.setPreferredSize(new Dimension(150, DimensionConstants.FOOTER_HEIGHT));
			dialogsPanel.setOpaque(false);
			MyButton button = MyButtonUtil.createSimpleButton(InputItemConstants.ITEM_DICTIONARY, ImageConstants.IMAGE_NAME_BUTTON_DICTIONARY,
					functionListener);
			dialogsPanel.add(button);
			button = MyButtonUtil.createSimpleButton(InputItemConstants.ITEM_KEYBOARD, ImageConstants.IMAGE_NAME_BUTTON_KEYBOARD, functionListener);
			dialogsPanel.add(button);
		}
		return dialogsPanel;
	}

	private void init() {
		buildView();
	}

	/**
	 * yes, no, ok, ko, bye
	 * 
	 * @return
	 */
	private JPanelDisposable getPanelEmotes() {
		if (emotesPanel == null) {
			emotesPanel = new JPanelDisposable();
			emotesPanel.setLayout(new FlowLayout());
			emotesPanel.setPreferredSize(new Dimension(350, DimensionConstants.FOOTER_HEIGHT));
			emotesPanel.setOpaque(false);
			MyButton button = MyButtonUtil.createSimpleButton(InputItemConstants.ITEM_EMOTE_YES, ImageConstants.IMAGE_NAME_BUTTON_EMOTE_YES, emoteListener);
			emotesPanel.add(button);
			button = MyButtonUtil.createSimpleButton(InputItemConstants.ITEM_EMOTE_NO, ImageConstants.IMAGE_NAME_BUTTON_EMOTE_NO, emoteListener);
			emotesPanel.add(button);
			button = MyButtonUtil.createSimpleButton(InputItemConstants.ITEM_EMOTE_OK, ImageConstants.IMAGE_NAME_BUTTON_EMOTE_OK, emoteListener);
			emotesPanel.add(button);
			button = MyButtonUtil.createSimpleButton(InputItemConstants.ITEM_EMOTE_KO, ImageConstants.IMAGE_NAME_BUTTON_EMOTE_KO, emoteListener);
			emotesPanel.add(button);
		}
		return emotesPanel;
	}

	protected void send(String txt) {
		logger.info("send: {}", txt);
		/**
		 * transmit message
		 */
		AwtBroker.get().getController().say(txt);
	}

}
