package gossip.view.chatview;

import javax.swing.JList;
import javax.swing.ListModel;

import gossip.data.OperatorSayMessage;
import gossip.view.ViewController;

class MessageList extends JList<OperatorSayMessage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private final ViewController viewController;

	public MessageList(ListModel<OperatorSayMessage> model, ViewController viewController) {
		super(model);
		this.viewController = viewController;
	}

}