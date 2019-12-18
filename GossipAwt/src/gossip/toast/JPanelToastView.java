package gossip.toast;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;

import gossip.lib.panel.JPanelMyBack;
import gossip.lib.panel.MyTextField;

public class JPanelToastView extends JPanelMyBack {

	private static final long serialVersionUID = 1L;
	private MyTextField textField;

	public JPanelToastView() {
		super(Color.red, Color.LIGHT_GRAY, BorderFactory.createEmptyBorder(0, 0, 0, 0), false);
		init();
	}

	private void init() {
		buildView();
		
		addMouseListener(new MouseAdapter() {
		});
		
		updateView();
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

	private void updateView() {
		// NOP
	}

	public void setMessage(String txt) {
		getTextField().setText(txt);
	}

}
