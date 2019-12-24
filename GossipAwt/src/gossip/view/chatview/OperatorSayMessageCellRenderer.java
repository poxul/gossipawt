package gossip.view.chatview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import gossip.data.OperatorSayMessage;
import gossip.lib.panel.RoundedLineBorder;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.util.StringValueUtil;

public class OperatorSayMessageCellRenderer extends JPanelDisposable implements ListCellRenderer<OperatorSayMessage> {

	private static final long serialVersionUID = 1L;

	private JLabel name;
	private JTextField message;

	public OperatorSayMessageCellRenderer() {
		init();
	}

	private JLabel getLabelName() {
		if (name == null) {
			name = new JLabel();
		}
		return name;
	}

	private JTextField getLabelMessage() {
		if (message == null) {
			message = new JTextField();
		}
		return message;
	}

	private void init() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 5, 2), new RoundedLineBorder(Color.BLACK, Color.GRAY, 1, 4)));
		add(getLabelName(), BorderLayout.NORTH);
		add(getLabelMessage(), BorderLayout.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends OperatorSayMessage> list, OperatorSayMessage value, int index, boolean isSelected,
			boolean cellHasFocus) {
		setName(value);
		setMessage(value);
		return this;
	}

	public void setMessage(OperatorSayMessage m) {
		StringBuilder sb = new StringBuilder();
		sb.append(">");
		sb.append(m.getText());
		sb.append("<\n");
		getLabelMessage().setText(sb.toString());
	}

	public void setName(OperatorSayMessage m) {
		StringBuilder sb = new StringBuilder();
		sb.append(StringValueUtil.getName(m.getSender()));
		sb.append("\t");
		sb.append(StringValueUtil.buildTimeStirng(m.getDate()));
		getLabelName().setText(sb.toString());
	}

}
