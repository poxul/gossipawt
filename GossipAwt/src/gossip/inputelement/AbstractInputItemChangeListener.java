package gossip.inputelement;

import java.util.UUID;

public abstract class AbstractInputItemChangeListener implements InputItemChangeListener {

	private final UUID objectId = UUID.randomUUID();

	@Override
	public UUID getUuid() {
		return objectId;
	}

	@Override
	public void dispose() {
		// NOP
	}

}
