package gossip.event;

/**
 * 
 * @author mila
 * 
 */
public interface PressedReleasedListener {

	public void onButtonPressed();

	public void onButtonReleased();

	public void onButtonLost();

}