package gossip.view.chatview;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JList;
import javax.swing.ListModel;

import gossip.config.ColorConstants;
import gossip.config.ImageConstants;
import gossip.data.OperatorSayMessage;
import gossip.lib.panel.flatbutton.DrawableFlatButton;
import gossip.lib.panel.flatbutton.FlatButton;
import gossip.lib.panel.flatbutton.MouseListenerButton;
import gossip.util.DrawingUtil;
import gossip.view.ViewController;

class MessageList extends JList<OperatorSayMessage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ViewController viewController;

	private FlatButton buttonHide;
	private FlatButton buttonAdd;

	public MessageList(ListModel<OperatorSayMessage> model, ViewController viewController) {
		super(model);
		this.viewController = viewController;
		init();
	}

	protected void functionAdd() {
		viewController.showKeyboard(true);
	}

	protected void functionHide() {
		viewController.showChat(false);
	}

	private void init() {

		buttonHide = new DrawableFlatButton(0, 0, ImageConstants.IMAGE_NAME_BUTTON_HIDE_CHAT, ColorConstants.BUTTON_COLOR_HIDE);
		buttonAdd = new DrawableFlatButton(0, 0, ImageConstants.IMAGE_NAME_BUTTON_ADD_MESSAGE, ColorConstants.BUTTON_COLOR_ADD);

		addMouseListener(new MouseListenerButton(buttonAdd) {

			@Override
			public void onTrigger() {
				functionAdd();
			}
		});

		addMouseListener(new MouseListenerButton(buttonHide) {

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

			xPos = getWidth() - DrawableFlatButton.BUTTON_GAP - DrawableFlatButton.BUTTON_DIAMETER;
			yPos = getHeight() - (DrawableFlatButton.BUTTON_DIAMETER + DrawableFlatButton.BUTTON_GAP);
			buttonAdd.draw(g2d, xPos, yPos);
		} finally {
			g2d.dispose();
		}

	}
}