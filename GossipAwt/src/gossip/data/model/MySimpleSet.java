package gossip.data.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import gossip.lib.data.DataModelBase;

public class MySimpleSet<V> extends DataModelBase {

	private Set<V> set;

	public MySimpleSet(Set<V> value) {
		super();
		this.set = value;
	}

	public void add(V value) {
		set.add(value);
		fireModelChanged("added", null, value);
	}

	public void remove(V value) {
		set.remove(value);
		fireModelChanged("removed", value, null);
	}

	public int size() {
		return set.size();
	}

	public Collection<V> values() {
		return new ArrayList<>(set);
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

}