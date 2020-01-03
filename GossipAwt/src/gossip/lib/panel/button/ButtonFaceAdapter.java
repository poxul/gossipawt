package gossip.lib.panel.button;

import java.util.UUID;

public abstract class ButtonFaceAdapter implements ButtonFaceListener {

	private final UUID objectId = UUID.randomUUID();

	@Override
	public UUID getUuid() {
		return objectId;
	}

}
