package gossip.event;

public class KeyBoardEvent implements UserEvent {

	public enum KeyBoardResultType {
		ON_ENTER,
		ON_CANCEL,
		ON_SWITCH_TO_NUM_PAD,
		ON_SWITCH_TO_ALPHANUM,
		ON_SWITCH_TO_HEX,
		ON_CHANGE,
		ON_SWITCH_TO_PASSWORD_CONFIRM,
		ON_START_BELT,
		ON_SWITCH_TO_DATE_TIME, // NO_UCD (unused code)
		ON_SWITCH_TO_IP,
		ON_SWITCH_TO_CN,
		ON_DELETE,
		ON_REVERT,
		ON_COMMIT_BOUNDS,
		ON_REVERT_BOUNDS,
		ON_SERVICE_PAGE
	}

	private KeyBoardResultType type;

	public KeyBoardEvent(KeyBoardResultType type) {
		super();
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public KeyBoardResultType getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KeyBoardEvent [type=" + type + "]";
	}

}