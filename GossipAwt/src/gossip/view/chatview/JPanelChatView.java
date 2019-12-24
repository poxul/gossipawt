package gossip.view.chatview;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.data.AwtBroker;
import gossip.data.OperatorSayMessage;
import gossip.data.model.MySimpleList;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.panel.disposable.JScrollPaneDisposable;
import gossip.lib.util.MyLogger;

public class JPanelChatView extends JPanelDisposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(JPanelChatView.class);

	private JScrollPaneDisposable scrollPane;

	private JList<OperatorSayMessage> messageList;

	public JPanelChatView() {
		init();
	}

	public void addMessage(OperatorSayMessage m) {
		logger.info("message: {}", m);
		if (isShowing()) {
			clearUnread();
			// TODO SCROLL TO BOTTOM
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

	private JList<OperatorSayMessage> getMessageList() {
		if (messageList == null) {
			messageList = new JList<>(getMessageListModel());
			messageList.setEnabled(false);
			messageList.setCellRenderer(new OperatorSayMessageCellRenderer());
		}
		return messageList;
	}

	private MessageListModel getMessageListModel() {
		return new MessageListModel(AwtBroker.get().getData().getOsmStack());
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPaneDisposable(getMessageList());
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
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
			public void componentShown(ComponentEvent e) {
				clearUnread();
			}

		});
	}

	@Override
	public void paint(Graphics g) {
		// TODO Draw hide and add button
		super.paint(g);
	}

}