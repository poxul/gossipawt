package gossip.chatview;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.config.FontConstants;
import gossip.data.AwtBroker;
import gossip.data.OperatorSayMessage;
import gossip.data.client.ClientDataModel;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.panel.disposable.JScrollPaneDisposable;
import gossip.lib.util.MyLogger;
import gossip.util.StringValueUtil;

public class JPanelChatView extends JPanelDisposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(JPanelChatView.class);

	private JScrollPaneDisposable scrollPane;

	private JTextPane jPanelTextPane;

	public JPanelChatView() {
		init();
	}

	private ClientDataModel getClientData() {
		return AwtBroker.get().getController().getClientData();
	}

	private void init() {
		// init observation
		getClientData().addModelChangeListener((source, origin, oldValue, newValue) -> {
			if (ClientDataModel.SYNC.equals(origin) || ClientDataModel.CLEAR.equals(origin)) {
				updateClientCount();
				updateClients();
			} else if (ClientDataModel.SELECTION.equals(origin)) {
				updateReceiverCount();
				updateClients();
			} else if (ClientDataModel.SAY.equals(origin)) {
				if (newValue instanceof OperatorSayMessage) {
					addMyMessage((OperatorSayMessage) newValue);
				} else {
					logger.error("invalid say event {}", newValue);
				}
			} else if (ClientDataModel.INCOMMING.equals(origin)) {
				if (newValue instanceof OperatorSayMessage) {
					addIncommingMessage((OperatorSayMessage) newValue);
				} else {
					logger.error("invalid say event {}", newValue);
				}
			} else {
				logger.warn("unhandled event {} {}", origin, newValue);
			}
		});

		buildView();
	}

	private void addMyMessage(OperatorSayMessage m) {
		logger.info("add message: {}", m);
		addMessage(m);
	}

	private void addIncommingMessage(OperatorSayMessage m) {
		logger.info("add incomming message: {}", m);
		addMessage(m);
	}

	public void addMessage(OperatorSayMessage m) {
		StringBuilder sb = new StringBuilder();
		sb.append(StringValueUtil.getName(m.getSender()));
		sb.append("\t");
		sb.append(StringValueUtil.buildTimeStirng(m.getDate()));
		sb.append("\n: >");
		sb.append(m.getText());
		sb.append("<\n");
		append(sb.toString());
	}

	private void append(String s) {
		try {
			Document doc = getEditPane().getDocument();
			doc.insertString(doc.getLength(), s, null);
		} catch (BadLocationException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void updateReceiverCount() {
		// TODO Auto-generated method stub
		logger.info("receiver count changed");
	}

	private void updateClients() {
		// TODO Auto-generated method stub
		logger.info("clients changed");
	}

	private void updateClientCount() {
		// TODO Auto-generated method stub
		logger.info("clients count changed");
	}

	private void buildView() {
		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(ColorConstants.EDIT_VIEW_BACKGROUND);
		add(getScrollPane(), BorderLayout.CENTER);
	}

	private JTextPane getEditPane() {
		if (jPanelTextPane == null) {
			jPanelTextPane = new JTextPane();
			jPanelTextPane.setEditable(false);
			jPanelTextPane.setDragEnabled(false);
			jPanelTextPane.setEnabled(false);
			jPanelTextPane.setFont(FontConstants.CHAT_FONT);
			jPanelTextPane.setDisabledTextColor(ColorConstants.EDIT_VIEW_TEXT);
			DragToScrollListener dtsl = new DragToScrollListener(jPanelTextPane);
			jPanelTextPane.addMouseListener(dtsl);
			jPanelTextPane.addMouseMotionListener(dtsl);
		}
		return jPanelTextPane;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPaneDisposable(getEditPane());
			scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
			scrollPane.setViewportBorder(null);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setOpaque(false);
			scrollPane.setViewportBorder(null);
			scrollPane.getViewport().setOpaque(false);
			scrollPane.getVerticalScrollBar().setBlockIncrement(100);
			scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
				if (!e.getValueIsAdjusting()) {
					updateView();
				}
			});

		}
		return scrollPane;
	}

	protected void updateView() {
		// ADJUSTED
		// TODO Auto-generated method stub

	}
}
