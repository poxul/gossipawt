package gossip.lib.panel.flatbutton;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class MouseListenerButton implements MouseListener {

	private final FlatButton button;

	private boolean isArmed;

	public MouseListenerButton(FlatButton button) {
		super();
		this.button = button;
	}

	public abstract void onTrigger();

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isArmed && button.contains(e.getPoint())) {
			isArmed = false;
			onTrigger();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (button.contains(e.getPoint())) {
			isArmed = true;
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