package gossip.keyboard;

public class InputValueFactory {

	public static InputValue createInputValue(Object o) {
		return createInputValue(o, null, null);
	}

	public static InputValue createInputValue(Object o, String min, String max) {
		if (o == null) {
			return new DefaultInputValue(null, min, max);
		} else {
			return new DefaultInputValue(o, min, max);
		}
	}

}
