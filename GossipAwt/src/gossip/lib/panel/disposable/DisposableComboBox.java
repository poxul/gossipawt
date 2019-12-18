package gossip.lib.panel.disposable;

import javax.swing.JComboBox;

import gossip.event.SelectionChangeListener;

public class DisposableComboBox<T> extends JComboBox<T> implements Disposable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void addSelectionChangeListener(SelectionChangeListener selectionChangeListener) {
		// TODO Auto-generated method stub
		
	}

}
