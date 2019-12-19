package gossip.view.keyboard;

import java.io.Serializable;

public class DefaultInputPanelModel implements InputPanelModel, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2774796305862967364L;
	private InputValue value;

	public String getMaxValue() {
		return value != null ? value.getMax() : "";
	}

	public String getMinValue() {
		return value != null ? value.getMin() : "";
	}

	@Override
	public InputValue getParameter() {
		return value;
	}

	@Override
	public String getParameterValueString() {
		return value != null ? value.getValueString() : "";
	}

	@Override
	public void setParameter(InputValue value) {
		this.value = value;

	}

}
