package gossip.inputelement;

import gossip.lib.UUIDSupport;
import gossip.lib.panel.disposable.Disposable;

public interface InputItemChangeListener extends UUIDSupport, Disposable {

	/**
	 * 
	 */
	void onItemDataChanged();
	
}
