package gossip.keyboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import gossip.event.KeyListenerInterface;
import gossip.key.MyKey;
import gossip.lib.panel.JPanelMyBack;
import gossip.lib.panel.JPanelInputField;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.util.KeyBoardUtil;

public class JPanelInputNumPad extends JPanelInputField {

	private JPanelDisposable jPanelBackGround;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1753982072363493031L;

	private JPanelDisposable keyActionPanel;
	private JPanelKeyLine jPanelKeyLine;

	public JPanelInputNumPad() {
		super();
	}

	@Override
	protected void buildView() {
		setLayout(new BorderLayout(0, 0));
		JPanel p0 = new JPanelDisposable();
		p0.setLayout(new BoxLayout(p0, BoxLayout.PAGE_AXIS));
		p0.setOpaque(false);
		p0.add(getKeyActionPanel());
		p0.setBorder(BorderFactory.createEmptyBorder());

		getJPanelBackground().add(p0, BorderLayout.CENTER);
		getJPanelBackground().add(getJPanelKeyLine(), BorderLayout.EAST);

		add(getJPanelBackground(), BorderLayout.CENTER);
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder());
	}

	private static final Color INPUT_FIELD_COLOR_1 = new Color(0xe8e8e8);
	private static final Color INPUT_FIELD_COLOR_2 = INPUT_FIELD_COLOR_1;

	private JPanelDisposable getJPanelBackground() {
		if (jPanelBackGround == null) {
			jPanelBackGround = new JPanelMyBack(INPUT_FIELD_COLOR_1, INPUT_FIELD_COLOR_2);
			jPanelBackGround.setLayout(new BorderLayout(0, 0));
			jPanelBackGround.setOpaque(false);
			jPanelBackGround.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 0));

		}
		return jPanelBackGround;
	}

	private JPanelKeyLine getJPanelKeyLine() {
		if (jPanelKeyLine == null) {

			List<MyKey> keys = new ArrayList<>();
			keys.add(new MyKey(KeyBoardUtil.BUTTON_NAME_BACKSPACE, KeyBoardUtil.BUTTON_NAME_BACKSPACE, KeyBoardUtil.BUTTON_NAME_BACKSPACE));

			jPanelKeyLine = new JPanelKeyLine(keys);
			jPanelKeyLine.setPassword(isPassword());
			jPanelKeyLine.addKeyListener(new KeyListenerInterface() {

				@Override
				public void onKeyPressed(String key, String text) {
					if (KeyBoardUtil.BUTTON_FACE_NAME_BUTTON_BACKSPACE.equals(key)) {
						backspace();
					}
				}
			});
			jPanelKeyLine.setOpaque(false);
			jPanelKeyLine.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		}
		return jPanelKeyLine;
	}

	private JPanelDisposable getKeyActionPanel() {
		if (keyActionPanel == null) {
			keyActionPanel = new JPanelDisposable();
			keyActionPanel.setOpaque(false);
			keyActionPanel.setLayout(new BorderLayout(0, 0));
			keyActionPanel.add(getJPanelInputCard(), BorderLayout.CENTER);
			keyActionPanel.setBorder(BorderFactory.createEmptyBorder());
		}
		return keyActionPanel;
	}

}
