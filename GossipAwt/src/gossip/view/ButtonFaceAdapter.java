package gossip.view;

import java.util.UUID;

import gossip.lib.panel.button.ButtonFaceListener;

public abstract class ButtonFaceAdapter implements ButtonFaceListener {

	private final UUID objectId = UUID.randomUUID();

	@Override
	public UUID getUuid() {
		return objectId;
	}

}
