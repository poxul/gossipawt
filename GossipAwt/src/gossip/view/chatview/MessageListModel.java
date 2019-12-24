package gossip.view.chatview;

import javax.swing.AbstractListModel;

import gossip.data.OperatorSayMessage;
import gossip.data.model.MySimpleList;

public class MessageListModel extends AbstractListModel<OperatorSayMessage> {

	private final MySimpleList<OperatorSayMessage> messageStack;

	public MessageListModel(MySimpleList<OperatorSayMessage> messageStack) {
		this.messageStack = messageStack;
		messageStack.addModelChangeListener((source, origin, oldValue, newValue) -> onChange());
	}

	private static final long serialVersionUID = 1L;

	private void onChange() {
		fireContentsChanged(this, 0, getSize());
	}
	
	@Override
	public OperatorSayMessage getElementAt(int index) {
		return messageStack.get(index);
	}

	@Override
	public int getSize() {
		return messageStack.size();
	}

}
