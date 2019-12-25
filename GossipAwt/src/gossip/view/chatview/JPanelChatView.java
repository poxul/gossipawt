package gossip.view.chatview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.config.ImageConstants;
import gossip.data.AwtBroker;
import gossip.data.OperatorSayMessage;
import gossip.data.model.MySimpleList;
import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.panel.disposable.JScrollPaneDisposable;
import gossip.lib.util.MyLogger;
import gossip.util.DrawingUtil;
import gossip.util.ImageUtil;

public class JPanelChatView extends JPanelDisposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(JPanelChatView.class);

	private static final double BUTTON_DIAMETER = 40;

	private static final double BUTTON_GAP = 5;

	private JScrollPaneDisposable scrollPane;

	private JList<OperatorSayMessage> messageList;
	private MessageListModel listModel;

	private Double hideButtonShape;
	private Double addButtonShape;
	private Image imgHide;
	private Image imgAdd;

	public JPanelChatView() {
		init();
	}

	public void addMessage(OperatorSayMessage m) {
		logger.info("message: {}", m);
		if (isShowing()) {
			clearUnread();
		}
	}

	private void buildView() {
		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(ColorConstants.EDIT_VIEW_BACKGROUND);
		add(getScrollPane(), BorderLayout.CENTER);
	}

	private void clearButtonShapes() {
		hideButtonShape = null;
		addButtonShape = null;
	}

	protected void clearUnread() {
		AwtBroker.get().getData().clearUnread();
	}

	private MessageListModel createMessageListModel() {
		return new MessageListModel(AwtBroker.get().getData().getOsmStack());
	}

	private void drawButton(Graphics2D g2d, Color c, Shape s, Image img) {
		g2d.setColor(c);
		g2d.fill(s);

		Rectangle bnd = s.getBounds();
		int x = bnd.x + ((bnd.width - img.getWidth(null)) / 2);
		int y = bnd.y + ((bnd.height - img.getHeight(null)) / 2);

		g2d.drawImage(img, x, y, null);
	}

	private Image getAddButtonImage() {
		if (imgAdd == null) {
			imgAdd = ImageUtil.getImage(ImageConstants.IMAGE_NAME_BUTTON_ADD_MESSAGE);
		}
		return imgAdd;
	}

	private Shape getAddButtonShape() {
		if (addButtonShape == null) {

			double xGap;
			if (getScrollPane().getVerticalScrollBar().isVisible()) {
				xGap = getWidth() - (BUTTON_GAP + 20.0) - BUTTON_DIAMETER;
			} else {
				xGap = getWidth() - BUTTON_GAP - BUTTON_DIAMETER;
			}
			addButtonShape = new Ellipse2D.Double(xGap, getHeight() - (BUTTON_DIAMETER + BUTTON_GAP), BUTTON_DIAMETER, BUTTON_DIAMETER);
		}
		return addButtonShape;
	}

	private Image getHideButtonImage() {
		if (imgHide == null) {
			imgHide = ImageUtil.getImage(ImageConstants.IMAGE_NAME_BUTTON_HIDE_CHAT);
		}
		return imgHide;
	}

	private Shape getHideButtonShape() {
		if (hideButtonShape == null) {
			double xGap;
			if (getScrollPane().getVerticalScrollBar().isVisible()) {
				xGap = getWidth() - (BUTTON_GAP + 20.0) - BUTTON_DIAMETER;
			} else {
				xGap = getWidth() - BUTTON_GAP - BUTTON_DIAMETER;
			}

			hideButtonShape = new Ellipse2D.Double(xGap, BUTTON_GAP, BUTTON_DIAMETER, BUTTON_DIAMETER);
		}
		return hideButtonShape;
	}

	private JList<OperatorSayMessage> getMessageList() {
		if (messageList == null) {
			messageList = new JList<>(getMessageListModel());
			messageList.setEnabled(false);
			messageList.setOpaque(false);
			messageList.setCellRenderer(new OperatorSayMessageCellRenderer());
		}
		return messageList;
	}

	private MessageListModel getMessageListModel() {
		if (listModel == null) {
			listModel = createMessageListModel();
			listModel.addListDataListener(new ListDataListener() {

				@Override
				public void contentsChanged(ListDataEvent e) {
					// NOP
					scrollToBottom();
				}

				@Override
				public void intervalAdded(ListDataEvent e) {
					// Not fired !
					scrollToBottom();
				}

				@Override
				public void intervalRemoved(ListDataEvent e) {
					// Not Fired!
					scrollToBottom();
				}
			});
		}
		return listModel;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPaneDisposable(getMessageList());
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.getViewport().setOpaque(false);

			scrollPane.getVerticalScrollBar().addHierarchyListener(e -> {
				if (e.getID() == HierarchyEvent.HIERARCHY_CHANGED && (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
					clearButtonShapes();
				}
			});
		}
		return scrollPane;
	}
	private void init() {
		// init observation
		buildView();
		MySimpleList<OperatorSayMessage> messageStack = AwtBroker.get().getData().getOsmStack();
		messageStack.addModelChangeListener((source, origin, oldValue, newValue) -> {
			if (newValue instanceof OperatorSayMessage) {
				addMessage((OperatorSayMessage) newValue);
			}
		});

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				clearButtonShapes();
			}

			@Override
			public void componentShown(ComponentEvent e) {
				clearUnread();
			}

		});
		
		// TODO add button (mouse)  listener
		// TODO add drag to scroll listener
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = DrawingUtil.getGraphics2d(g);
		try {
			drawButton(g2d, ColorConstants.BUTTON_COLOR_HIDE, getHideButtonShape(), getHideButtonImage());
			drawButton(g2d, ColorConstants.BUTTON_COLOR_ADD, getAddButtonShape(), getAddButtonImage());
		} finally {
			g2d.dispose();
		}

	}

	public void scrollToBottom() {
		ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("scroll to bottom") {

			@Override
			public Boolean startJob() {
				JScrollBar bar = scrollPane.getVerticalScrollBar();
				bar.setValue(bar.getMaximum());
				return true;
			}
		});

	}

}