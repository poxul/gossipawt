package gossip.toast;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;

import gossip.config.ColorConstants;
import gossip.lib.panel.JPanelMyBack;

public class JPanelToastView extends JPanelMyBack {

	private static final long serialVersionUID = 1L;

	public JPanelToastView() {
		super(Color.red, Color.LIGHT_GRAY, BorderFactory.createEmptyBorder(0, 0, 0, 0), false);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		buildView();
		updateView();
	}

	private void buildView() {
		setMinimumSize(new Dimension(100, 100));
		setLayout(new BorderLayout());
		setBackground(ColorConstants.TOAST_BACKGROUND);
	}

	private void updateView() {
		// TODO update view
	}

	public void setMessage(String txt) {

	}

}
