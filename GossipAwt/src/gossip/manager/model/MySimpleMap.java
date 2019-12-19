package gossip.manager.model;

import java.util.Map;
import java.util.Set;

import gossip.lib.data.DataModelBase;

public class MySimpleMap<K, V> extends DataModelBase {

	private Map<K, V> map;

	public MySimpleMap(Map<K, V> value) {
		super();
		this.map = value;
	}

	public V get(Object key) {
		return map.get(key);
	}

	public void put(K key, V value) {
		map.put(key, value);
		fireModelChanged("added", null, value);
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public void remove(K key) {
		V oldValue = map.remove(key);
		if (oldValue != null) {
			fireModelChanged("removed", oldValue, null);
		}
	}

	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	public int size() {
		return map.size();
	}

}