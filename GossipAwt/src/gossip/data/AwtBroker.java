package gossip.data;

public class AwtBroker {

	private static AwtBroker instance;

	public synchronized static AwtBroker create() {
		instance = new AwtBroker();
		return instance;
	}

	public static AwtBroker get() {
		return instance;
	}

	private final AwtDataController controller;
	private final AwtData data = new AwtData();

	private AwtBroker() {
		controller = new AwtDataController(data);
	}

	public AwtDataController getController() {
		return controller;
	}

	public AwtData getData() {
		return data;
	}

}
