package gossip.lib.panel.flatbutton;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

public abstract class MouseListenerButton implements MouseListener {

	private final FlatButton button;

	private boolean isArmed;

	private final Component destination;

	public MouseListenerButton(FlatButton button, Component destination) {
		super();
		this.button = button;
		this.destination = destination;
	}

	public abstract void onTrigger();

	@Override
	public void mouseReleased(MouseEvent e) {
		e = SwingUtilities.convertMouseEvent(e.getComponent(), e, destination);
		if (isArmed && button.contains(e.getPoint())) {
			isArmed = false;
			onTrigger();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		e = SwingUtilities.convertMouseEvent(e.getComponent(), e, destination);
		if (button.contains(e.getPoint())) {
			isArmed = true;
		}else {
			isArmed = false;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		isArmed = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// NOP
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// NOP
	}

}