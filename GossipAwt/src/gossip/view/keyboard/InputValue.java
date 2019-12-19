package gossip.view.keyboard;

import java.util.List;

import gossip.rule.InputRule;

public interface InputValue {

	/**
	 * @return the o
	 */
	Object getObject();

	/**
	 * @param o the o to set
	 */
	void setObject(Object o);

	/**
	 * @return the min
	 */
	String getMin();

	/**
	 * @param min the min to set
	 */
	void setMin(String min);

	/**
	 * @return the max
	 */
	String getMax();

	/**
	 * @param max the max to set
	 */
	void setMax(String max);

	String getValueString();

	void putProperty(String key, Object o);

	Object getProperty(String key);

	List<InputRule> getDefaultInputRules();

	void addInputRule(InputRule rule);

}