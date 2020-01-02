package gossip.view.keyboard;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;

import gossip.config.ColorConstants;
import gossip.config.DimensionConstants;
import gossip.config.FontConstants;
import gossip.config.ImageConstants;
import gossip.data.AwtBroker;
import gossip.data.MyProfileId;
import gossip.data.ObservableClientProfile;
import gossip.data.model.MySimpleSet;
import gossip.lib.panel.JPanelInputField;
import gossip.lib.panel.MyTextField;
import gossip.lib.panel.flatbutton.DrawableFlatButton;
import gossip.lib.panel.flatbutton.MouseListenerButton;
import gossip.util.DrawingUtil;
import gossip.util.KeyBoardUtil;
import gossip.util.StringValueUtil;
import gossip.view.ViewController;
import gossip.view.keyboard.key.MyKey;

public class JPanelInputKeyBoard extends JPanelInputField {

	private static final long serialVersionUID = 3222575391084072967L;

	private MyTextField jLabelInputName;

	private DrawableFlatButton buttonHide;

	private final ViewController viewController;

	public JPanelInputKeyBoard(ViewController viewController) {
		super();
		this.viewController = viewController;
		initObserver();
	}

	private void initObserver() {
		MySimpleSet<MyProfileId> selected = AwtBroker.get().getData().getSelected();
		selected.addModelChangeListener((source, origin, oldValue, newValue) -> updateInputLabel(selected.values()));
		updateInputLabel(selected.values());
	}

	protected void updateInputLabel(Collection<MyProfileId> values) {
		if (values != null && !values.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (MyProfileId myProfileId : values) {
				ObservableClientProfile p = AwtBroker.get().getData().getClients().get(myProfileId);
				sb.append(StringValueUtil.buildProfileString(p));
				sb.append(" ");
			}
			setInputName(sb.toString());
		} else {
			setInputName("-");
		}
	}

	@Override
	protected void buildView() {
		buttonHide = new DrawableFlatButton(0, 0, ImageConstants.IMAGE_NAME_BUTTON_HIDE_CHAT, ColorConstants.BUTTON_COLOR_HIDE);

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

		jPanelKeyLine.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 55));
		add(getJLabelInputName(), BorderLayout.WEST);
		add(getJPanelInputCard(), BorderLayout.CENTER);
		add(jPanelKeyLine, BorderLayout.EAST);

		addMouseListener(new MouseListenerButton(buttonHide, this) {

			@Override
			public void onTrigger() {
				functionHide();
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = DrawingUtil.getGraphics2d(g);
		try {
			int xPos = getWidth() - DrawableFlatButton.BUTTON_GAP - DrawableFlatButton.BUTTON_DIAMETER;
			int yPos = DrawableFlatButton.BUTTON_GAP;
			buttonHide.draw(g2d, xPos, yPos);

		} finally {
			g2d.dispose();
		}
	}

	protected void functionHide() {
		viewController.showKeyboard(false);
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
