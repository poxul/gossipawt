package gossip.inputelement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gossip.event.ListenerSet;
import gossip.event.ListenerSet.ListenerSetProcessor;
import gossip.lib.panel.disposable.Disposable;
import gossip.manager.LanguageManager;

public class MultiItemHolder implements Disposable, InputItemChangeListener {

	@Override
	public String toString() {
		return "MultiItemHolder [listenerSet=" + listenerSet + ", items=" + items + ", objectId=" + objectId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((listenerSet == null) ? 0 : listenerSet.hashCode());
		result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MultiItemHolder other = (MultiItemHolder) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (listenerSet == null) {
			if (other.listenerSet != null)
				return false;
		} else if (!listenerSet.equals(other.listenerSet))
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		return true;
	}

	private class ItemChangeProcessor implements ListenerSetProcessor<InputItemChangeListener> {

		@Override
		public void process(final InputItemChangeListener item, final Object event) {
			item.onItemDataChanged();
		}

	}

	private final ListenerSet<InputItemChangeListener> listenerSet = new ListenerSet<>(new ItemChangeProcessor());

	private final List<InputItemId> items = new ArrayList<>();

	private UUID objectId;

	/*
	 * (non-Javadoc)
	 * 
	 */
	public void addItemChangeListener(InputItemChangeListener listener) {
		listenerSet.add(listener);
	}

	@Override
	public void dispose() {
		listenerSet.dispose();
		clearItemChangeListener();
	}

	public void clearItemChangeListener() {
		synchronized (items) {
			for (InputItemId element : items) {
				LanguageManager.removeInputElementChangeListener(this, element);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	public void removeItemChangeListener(InputItemChangeListener listener) {
		listenerSet.remove(listener);
	}

	public void addItem(InputItemId id) {
		synchronized (items) {
			items.add(id);
			LanguageManager.addInputElementChangeListener(this, id);
		}
	}

	public void removeItem(InputItemId id) {
		synchronized (items) {
			if (items.remove(id)) {
				LanguageManager.removeInputElementChangeListener(this, id);
			}
		}
	}

	@Override
	public void onItemDataChanged() {
		listenerSet.fireEvent(null);
	}

	@Override
	public UUID getUuid() {
		if (objectId == null) {
			objectId = UUID.randomUUID();
		}
		return objectId;
	}
}
