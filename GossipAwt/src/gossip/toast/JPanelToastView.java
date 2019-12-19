package gossip.toast;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.lib.panel.JPanelMyBack;
import gossip.lib.panel.MyTextField;
import gossip.lib.util.MyLogger;
import gossip.toast.ActuatedListener.ActuationState;

public class JPanelToastView extends JPanelMyBack {

	private static final long serialVersionUID = 1L;

	private static Logger logger = MyLogger.getLog(JPanelToastView.class);

	private MyTextField textField;

	private final List<ActuatedListener> actuatedListenerList = new ArrayList<>();

	private ActuationState state = ActuationState.IDLE;

	public JPanelToastView() {
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
	}

	public void removeActuatedListener(ActuatedListener listener) {
		synchronized (actuatedListenerList) {
			actuatedListenerList.remove(listener);
		}
	}

	public void setMessage(String txt) {
		getTextField().setText(txt);
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
