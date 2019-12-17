package gossip.keyboard;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import gossip.config.ColorConstants;
import gossip.config.DimensionConstants;
import gossip.config.FontConstants;
import gossip.key.MyKey;
import gossip.lib.panel.JPanelInputField;
import gossip.lib.panel.MyTextField;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.util.KeyBoardUtil;

public class JPanelInputKeyBoard extends JPanelInputField {

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
			jPanelEditBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanelEditBox.add(getJPanelInputCard());
		}
		return jPanelEditBox;
	}

	protected void buildView() {
		setLayout(new BorderLayout());
		setOpaque(false);

		List<MyKey> keys = new ArrayList<>();
		keys.add(new MyKey(KeyBoardUtil.BUTTON_NAME_BACKSPACE, KeyBoardUtil.BUTTON_NAME_BACKSPACE, KeyBoardUtil.BUTTON_NAME_BACKSPACE));

		JPanelKeyLine jPanelKeyLine = new JPanelKeyLine(keys);
		jPanelKeyLine.setPreferredSize(DimensionConstants.PREFERRED_SIZE_BACKSPACE_PANEL);
		jPanelKeyLine.setPassword(isPassword());
		jPanelKeyLine.addKeyListener((key, text) -> {
			if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_BACKSPACE.equals(key)) {
				backspace();
			}
		});

		jPanelKeyLine.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(getJLabelInputName(), BorderLayout.WEST);
		add(getJPanelEditBox(), BorderLayout.CENTER);
		add(jPanelKeyLine, BorderLayout.EAST);
	}

	private MyTextField getJLabelInputName() {
		if (jLabelInputName == null) {
			jLabelInputName = new MyTextField("###", DimensionConstants.INPUT_LABEL_WIDTH, DimensionConstants.INPUT_LABEL_HEIGHT);
			jLabelInputName.setOpaque(false);
			jLabelInputName.setFont(FontConstants.INPUTPANEL_NAME_FONT);
			jLabelInputName.setForeground(ColorConstants.TEXT_COLOR);
		}
		return jLabelInputName;
	}

	public void setInputName(String text) {
		getJLabelInputName().setText(text + " :");
	}

}
