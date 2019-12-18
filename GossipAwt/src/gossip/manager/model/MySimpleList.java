package gossip.manager.model;

import java.util.List;

import gossip.lib.data.DataModelBase;

public class MySimpleList<V> extends DataModelBase {

	private List<V> list;

	public MySimpleList(List<V> value) {
		super();
		this.list = value;
	}

	public void add(V value) {
		list.add(value);
		fireModelChanged("added", null, value);
	}

	public void remove(V value) {
		list.remove(value);
		fireModelChanged("removed", value, null);
	}

}