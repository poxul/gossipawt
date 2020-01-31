package gossip.view.chatview;

import static java.awt.Adjustable.VERTICAL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.config.ImageConstants;
import gossip.data.AwtBroker;
import gossip.data.OperatorSayMessage;
import gossip.data.model.MySimpleList;
import gossip.data.model.MySimpleModel;
import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.panel.disposable.JScrollPaneDisposable;
import gossip.lib.panel.flatbutton.DrawableFlatButton;
import gossip.lib.panel.flatbutton.FlatButton;
import gossip.lib.panel.flatbutton.MouseListenerButton;
import gossip.lib.panel.scroll.DragToScrollListener;
import gossip.lib.panel.scroll.MyScrollBar;
import gossip.lib.panel.scroll.MyScrollBarUIFlat;
import gossip.lib.util.MyLogger;
import gossip.util.DrawingUtil;
import gossip.view.ViewController;

public class JPanelChatView extends JPanelDisposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(JPanelChatView.class);

	private JScrollPaneDisposable scrollPane;

	private MessageList messageList;
	private MessageListModel listModel;

	private final ViewController viewController;

	public JPanelChatView(ViewController viewController) {
		this.viewController = viewController;
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

	protected void clearUnread() {
		AwtBroker.get().getData().clearUnread();
	}

	private MessageListModel createMessageListModel() {
		return new MessageListModel(AwtBroker.get().getData().getOsmStack());
	}

	private JList<OperatorSayMessage> getMessageList() {
		if (messageList == null) {
			messageList = new MessageList(getMessageListModel(), viewController);
			messageList.setEnabled(false);
			messageList.setOpaque(false);
			messageList.setCellRenderer(new OperatorSayMessageCellRenderer());

			DragToScrollListener scrollListener = new DragToScrollListener(messageList);
			messageList.addMouseListener(scrollListener);
			messageList.addMouseMotionListener(scrollListener);

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

	private FlatButton buttonHide;
	private FlatButton buttonAdd;

	private MySimpleModel<Boolean> isKeyboard;

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPaneDisposable(getMessageList()) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected JViewport createViewport() {

					return new JViewport() {

						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public void paint(Graphics g) {
							super.paint(g);
							Graphics2D g2d = DrawingUtil.getGraphics2d(g);
							try {
								int xPos = getWidth() - DrawableFlatButton.BUTTON_GAP - DrawableFlatButton.BUTTON_DIAMETER;
								int yPos = DrawableFlatButton.BUTTON_GAP;
								buttonHide.draw(g2d, xPos, yPos);

								if (!isKeyboard.getValue().booleanValue()) {
									xPos = getWidth() - DrawableFlatButton.BUTTON_GAP - DrawableFlatButton.BUTTON_DIAMETER;
									yPos = getHeight() - (DrawableFlatButton.BUTTON_DIAMETER + DrawableFlatButton.BUTTON_GAP);
									buttonAdd.draw(g2d, xPos, yPos);
								}
							} finally {
								g2d.dispose();
							}

						}
					};
				}

			};
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

			JViewport viewPort = scrollPane.getViewport();

			viewPort.setOpaque(false);

			buttonHide = new DrawableFlatButton(0, 0, ImageConstants.IMAGE_NAME_BUTTON_HIDE_CHAT, ColorConstants.BUTTON_COLOR_HIDE);
			buttonAdd = new DrawableFlatButton(0, 0, ImageConstants.IMAGE_NAME_BUTTON_ADD_MESSAGE, ColorConstants.BUTTON_COLOR_ADD);

			getMessageList().addMouseListener(new MouseListenerButton(buttonAdd, viewPort) {

				@Override
				public void onTrigger() {
					functionAdd();
				}
			});

			getMessageList().addMouseListener(new MouseListenerButton(buttonHide, viewPort) {

				@Override
				public void onTrigger() {
					functionHide();
				}
			});

			MyScrollBar b = new MyScrollBar(VERTICAL, new MyScrollBarUIFlat(new Dimension(10, 10)), new Dimension(15, 15));
			b.setOpaque(false);
			b.setBorder(BorderFactory.createEmptyBorder());
			b.setAutoscrolls(false);
			scrollPane.setVerticalScrollBar(b);
		}
		return scrollPane;
	}

	protected void functionAdd() {
		viewController.showKeyboard(true);
	}

	protected void functionHide() {
		viewController.showChat(false);
	}

	private void init() {
		// init observation
		buildView();

		isKeyboard = AwtBroker.get().getData().getShowKeyboardProperty();
		isKeyboard.addModelChangeListener((source, origin, oldValue, newValue) -> repaint());

		MySimpleList<OperatorSayMessage> messageStack = AwtBroker.get().getData().getOsmStack();
		messageStack.addModelChangeListener((source, origin, oldValue, newValue) -> {
			if (newValue instanceof OperatorSayMessage) {
				addMessage((OperatorSayMessage) newValue);
			}
		});

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentShown(ComponentEvent e) {
				clearUnread();
			}

		});

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