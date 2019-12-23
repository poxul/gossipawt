package gossip.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;

import org.apache.logging.log4j.Logger;

import gossip.config.DimensionConstants;
import gossip.config.ImageConstants;
import gossip.config.InputItemConstants;
import gossip.config.LocationUtil;
import gossip.config.LocationUtil.ViewId;
import gossip.data.AwtBroker;
import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;
import gossip.lib.panel.button.ButtonFaceListener;
import gossip.lib.panel.button.MyButton;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;
import gossip.util.MyButtonUtil;
import gossip.view.MainView.ViewController;

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
	private JDialog dialogKeyboard;

	private final ViewController viewController;

	public MainFooterView(ViewController viewController) {
		this.viewController = viewController;
		init();
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

	// TOD Keyboard dialog class
	private JDialog createDialogKeyBoard() {
		return KeyboardDialog.createDialogKeyBoard();
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

	public JDialog getDialogKeyboard() {
		if (dialogKeyboard == null) {
			dialogKeyboard = createDialogKeyBoard();
		}
		return dialogKeyboard;
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

	private void init() {
		buildView();
	}

	protected void send(String txt) {
		logger.info("send: {}", txt);
		/**
		 * transmit message
		 */
		AwtBroker.get().getController().say(txt);
	}

	private void showDictionary(boolean b) {
		if (b) {
			viewController.showDictionary();
		} else {
			viewController.showChat();
		}
	}

	private void showKeyboard(boolean mode) {
		ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("view dialog: " + mode) {

			@Override
			public Boolean startJob() {
				JDialog dialog = getDialogKeyboard();
				dialog.setVisible(mode);
				dialog.setLocation(LocationUtil.getLocation(ViewId.KEYBOARD, dialog.getBounds()));
				return true;
			}
		});
	}

}
