package gossip.keyboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import gossip.config.FontConstants;
import gossip.key.MyKey;
import gossip.lib.panel.MyTextField;
import gossip.lib.panel.JPanelInputField;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.util.KeyBoardUtil;

public class JPanelInputKeyBoard extends JPanelInputField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3222575391084072967L;

	private MyTextField jLabelInputName;

	public JPanelInputKeyBoard() {
		super();
	}

	private JPanelDisposable jPanelEditBox;

	private JPanelDisposable getJPanelEditBox() {
		if (jPanelEditBox == null) {
			jPanelEditBox = new JPanelDisposable();
			jPanelEditBox.setLayout(new BoxLayout(jPanelEditBox, BoxLayout.PAGE_AXIS));
			jPanelEditBox.setOpaque(false);
			jPanelEditBox.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
			jPanelEditBox.add(getJPanelInputCard());
		}
		return jPanelEditBox;
	}

	protected void buildView() {
		setLayout(new BorderLayout(5, 0));
		setOpaque(false);

		List<MyKey> keys = new ArrayList<>();
		keys.add(new MyKey(KeyBoardUtil.BUTTON_NAME_BACKSPACE, KeyBoardUtil.BUTTON_NAME_BACKSPACE, KeyBoardUtil.BUTTON_NAME_BACKSPACE));

		JPanelKeyLine jPanelKeyLine = new JPanelKeyLine(keys);
		jPanelKeyLine.setPassword(isPassword());
		jPanelKeyLine.addKeyListener((key, text) -> {
			if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_BACKSPACE.equals(key)) {
				backspace();
			}
		});

		jPanelKeyLine.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		add(getJLabelInputName(), BorderLayout.WEST);
		add(getJPanelEditBox(), BorderLayout.CENTER);
		add(jPanelKeyLine, BorderLayout.EAST);
	}

	private MyTextField getJLabelInputName() {
		if (jLabelInputName == null) {
			jLabelInputName = new MyTextField("###", 300, 90);
			jLabelInputName.setOpaque(false);
			jLabelInputName.setFont(FontConstants.INPUTPANEL_NAME_FONT);
			jLabelInputName.setForeground(Color.BLACK);
		}
		return jLabelInputName;
	}

	public void setInputName(String text) {
		getJLabelInputName().setText(text + " :");
	}

}
