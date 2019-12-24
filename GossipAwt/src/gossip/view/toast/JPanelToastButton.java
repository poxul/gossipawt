package gossip.view.toast;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.data.AwtBroker;
import gossip.lib.panel.JPanelMyBack;
import gossip.lib.panel.MyTextField;
import gossip.lib.panel.RoundedLineBorder;
import gossip.lib.util.MyLogger;
import gossip.util.DrawingUtil;
import gossip.view.toast.ActuatedListener.ActuationState;

public class JPanelToastButton extends JPanelMyBack {

	private static final long serialVersionUID = 1L;

	private static Logger logger = MyLogger.getLog(JPanelToastButton.class);

	private MyTextField textField;

	private final List<ActuatedListener> actuatedListenerList = new ArrayList<>();

	private ActuationState state = ActuationState.UNKNOWN;

	/**
	 * State of server connection
	 */
	private boolean isConnected;

	/**
	 * unread messages
	 */
	private int messageCount = 0;

	public JPanelToastButton() {
		super(ColorConstants.COLOR_UNSELECTED_1, ColorConstants.COLOR_UNSELECTED_2, BorderFactory.createEmptyBorder(0, 0, 0, 0), false);
		init();
	}

	private void actuated(final ActuationState state) {
		logger.info("actuated change to; {}", state);
		synchronized (actuatedListenerList) {
			for (ActuatedListener listener : actuatedListenerList) {
				listener.onActuated(state);
			}
		}
	}

	public void addActuatedListener(ActuatedListener listener) {
		synchronized (actuatedListenerList) {
			actuatedListenerList.add(listener);
		}
	}

	private void buildView() {
		setMinimumSize(new Dimension(100, 100));
		setLayout(new BorderLayout());
		add(getTextField(), BorderLayout.CENTER);
	}

	private MyTextField getTextField() {
		if (textField == null) {
			textField = new MyTextField();
		}
		return textField;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = DrawingUtil.getGraphics2d(g);
		try {
			if (isConnected) {
				if (messageCount > 0) {
					g2d.setColor(ColorConstants.TOAST_BUTTON_CIRCLE_COLOR_CONNECTED);
					g2d.setStroke(new BasicStroke(2));
					g2d.drawOval(2, 2, 20, 20);
					g2d.setColor(ColorConstants.TOAST_BUTTON_CIRCLE_TEXT_COLOR);
					String num = String.valueOf(messageCount);
					if (messageCount > 25) {
						g2d.drawString("XX", 4, 17);
					} else if (messageCount > 9) {
						g2d.drawString(num, 4, 17);
					} else {
						g2d.drawString(num, 8, 17);
					}
				} else {
					g2d.setColor(ColorConstants.TOAST_BUTTON_CIRCLE_COLOR_CONNECTED);
					g2d.fillOval(7, 7, 10, 10);
				}
			} else {
				g2d.setColor(ColorConstants.TOAST_BUTTON_CIRCLE_COLOR_DISCONNECTED);
				g2d.drawOval(7, 7, 10, 10);
			}
		} finally {
			g2d.dispose();
		}
	}

	private void init() {
		buildView();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				updateState(ActuationState.IDLE);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (state == ActuationState.IDLE) {
					updateState(ActuationState.ARMED);
				} else {
					updateState(ActuationState.IDLE);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (state == ActuationState.ARMED) {
					updateState(ActuationState.TRIGGERED);
				} else {
					updateState(ActuationState.IDLE);
				}
			}
		});

		AwtBroker.get().getData().getServerConnectedProperty().addModelChangeListener((source, origin, oldValue, newValue) -> updateConnectionState());
		
		AwtBroker.get().getData().getNumMessagesProperty().addModelChangeListener((source, origin, oldValue, newValue) -> updateMessagesCount());
		
		updateConnectionState();
		updateState(ActuationState.IDLE);
	}

	protected void updateMessagesCount() {
		messageCount = AwtBroker.get().getData().getNumMessagesProperty().getValue();
		repaint();
	}

	public void removeActuatedListener(ActuatedListener listener) {
		synchronized (actuatedListenerList) {
			actuatedListenerList.remove(listener);
		}
	}

	public void setMessage(String txt) {
		getTextField().setText(txt);
	}

	public void clearState() {
		updateState(ActuationState.IDLE);
	}

	private void updateConnectionState() {
		isConnected = AwtBroker.get().getData().getServerConnectedProperty().getValue();
		if (isConnected) {
			setBorder(BorderFactory.createEmptyBorder());
		} else {
			RoundedLineBorder border = new RoundedLineBorder(Color.BLACK, Color.GRAY, 2, 3, false);
			setBorder(border);
		}
	}

	protected void updateState(ActuationState state) {
		if (this.state != state) {
			this.state = state;
			switch (state) {
			case ARMED:
				setColor(ColorConstants.COLOR_SELECTED_1, ColorConstants.COLOR_SELECTED_2);
				break;
			case TRIGGERED:
				setColor(ColorConstants.COLOR_TRIGGERED_1, ColorConstants.COLOR_TRIGGERED_2);
				break;
			case IDLE:
			default:
				setColor(ColorConstants.COLOR_UNSELECTED_1, ColorConstants.COLOR_UNSELECTED_2);
				break;
			}
			actuated(state);
		}
	}

}
