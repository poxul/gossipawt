package gossip.lib.panel.button;

import gossip.lib.UUIDSupport;

/**
 * 
 * @author mila
 * 
 */
public interface ButtonFaceListener extends UUIDSupport {

	void onButtonFaceChanged(boolean isReleased, String name, String text);

}