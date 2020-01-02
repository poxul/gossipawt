package gossip.view;

import java.awt.BorderLayout;
import java.util.Locale;

import javax.swing.JDialog;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.config.DimensionConstants;
import gossip.lib.util.MyLogger;
import gossip.util.KeyBoardUtil;
import gossip.util.KeyBoardUtil.KeyBoardType;
import gossip.view.keyboard.JPanelKeyBoard;

public class KeyboardDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(KeyboardDialog.class);

	public static KeyboardDialog createDialogKeyBoard(ViewController viewController) {
		return new KeyboardDialog(viewController);
	}

	private JPanelKeyBoard keyBoardPanel;

	private ViewController viewController;

	public KeyboardDialog(ViewController viewController) {
		this.viewController = viewController;
		init();
	}

	private void init() {
		setUndecorated(true);
		setLayout(new BorderLayout());
		setPreferredSize(DimensionConstants.KEYBOARD_DIALOG_DIMENSION);
		add(getKeyBoardView(), BorderLayout.CENTER);
		setBackground(ColorConstants.KEYBOARD_BACKGROUND);
		pack();
	}

	private JPanelKeyBoard getKeyBoardView() {
		if (keyBoardPanel == null) {
			keyBoardPanel = createKeyBoard();
		}
		return keyBoardPanel;
	}

	private JPanelKeyBoard createKeyBoard() {
		JPanelKeyBoard panel = new JPanelKeyBoard(viewController);
		panel.setKeyBoardDefinition(KeyBoardUtil.getKeyBoardDefinition(KeyBoardType.GENERAL, Locale.GERMAN));
		panel.addKeyBoardResultListener(event -> {
			logger.info("keyboard end result: {}", event);
			viewController.onKeyboard(event);
		});
		return panel;
	}

}
