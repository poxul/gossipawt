package gossip.view.chatview;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import gossip.config.ColorConstants;
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
			name.setBackground(ColorConstants.BACKGROUND_COLOR_CHAT_LABEL);
		}
		return name;
	}

	private JTextField getLabelMessage() {
		if (message == null) {
			message = new JTextField();
			message.setBackground(ColorConstants.BACKGROUND_COLOR_CHAT_MESSAGE);
		}
		return message;
	}

	private void init() {
		setLayout(new BorderLayout());
		setOpaque(false);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 5, 2),
				new RoundedLineBorder(ColorConstants.FRAME_COLOR_CHAT_1, ColorConstants.FRAME_COLOR_CHAT_2, 1, 4)));
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
		sb.append("                                ");
		sb.append(StringValueUtil.buildTimeStirng(m.getDate()));
		getLabelName().setText(sb.toString());
	}

}
