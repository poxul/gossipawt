/**
 * 
 */
package gossip.event;

public class SelectionChangedEvent {

	private int index;
	private Object oldSelection;
	private Object newSelection;

	public SelectionChangedEvent(int index, Object oldSelection, Object newSelection) {
		super();
		this.index = index;
		this.oldSelection = oldSelection;
		this.newSelection = newSelection;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return the oldSelection
	 */
	public Object getOldSelection() {
		return oldSelection;
	}

	/**
	 * @return the newSelection
	 */
	public Object getNewSelection() {
		return newSelection;
	}

}