package gossip.view.keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gossip.rule.InputRule;

public class DefaultInputValue implements InputValue {

	private static final String VALUE = "value";
	private static final String MIN = "min";
	private static final String MAX = "max";

	private final Map<String, Object> properties = new HashMap<>();

	private List<InputRule> defaultInputRules;

	public DefaultInputValue(final Object o, final String min, final String max) {
		super();
		putProperty(VALUE, o);
		putProperty(MIN, min);
		putProperty(MAX, max);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DefaultInputValue))
			return false;
		DefaultInputValue other = (DefaultInputValue) obj;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public String getMax() {
		return (String) getProperty(MAX);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public String getMin() {
		return (String) getProperty(MIN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public Object getObject() {
		return getProperty(VALUE);
	}

	@Override
	public Object getProperty(final String key) {
		return properties.get(key);
	}

	@Override
	public String getValueString() {
		Object o = getProperty(VALUE);
		return o != null ? o.toString() : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		return result;
	}

	@Override
	public void putProperty(final String key, final Object o) {
		properties.put(key, o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void setMax(final String max) {
		putProperty(MAX, max);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void setMin(final String min) {
		putProperty(MIN, min);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void setObject(final Object o) {
		putProperty(VALUE, o);
	}

	@Override
	public List<InputRule> getDefaultInputRules() {
		return defaultInputRules;
	}

	@Override
	public synchronized void addInputRule(InputRule rule) {
		if (defaultInputRules == null) {
			defaultInputRules = new ArrayList<>();
		}
		defaultInputRules.add(rule);
	}

}
