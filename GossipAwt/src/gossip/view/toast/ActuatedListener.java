package gossip.view.toast;

public interface ActuatedListener {
	
	public enum ActuationState {
		UNKNOWN,
		IDLE,
		ARMED,
		TRIGGERED
	}


	void onActuated(ActuationState state);

}
