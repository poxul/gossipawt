package gossip.view.toast;

public interface ActuatedListener {
	
	public enum ActuationState {
		IDLE,
		ARMED,
		TRIGGERED
	}


	void onActuated(ActuationState state);

}
