package gossip.view.keyboard.input;

import gossip.lib.UUIDSupport;
import gossip.lib.panel.disposable.Disposable;

public interface InputItemChangeListener extends UUIDSupport, Disposable {

	/**
	 * 
	 */
	void onItemDataChanged();
	
}
