package gossip.data.model;

import gossip.lib.data.DataModelBase;
import gossip.lib.util.ObjectUtil;

public class MySimpleModel<T> extends DataModelBase {

	private T value;

	public MySimpleModel(T value) {
		super();
		this.value = value;
	}

	public MySimpleModel() {
		super();
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		if (!ObjectUtil.compare(this.value, value)) {
			T old = this.value;
			this.value = value;
			fireModelChanged("changed", old, value);
		}
	}

}