package gossip.view.chatview;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.data.AwtBroker;
import gossip.data.OperatorSayMessage;
import gossip.data.model.MySimpleList;
import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.panel.disposable.JScrollPaneDisposable;
import gossip.lib.util.MyLogger;
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