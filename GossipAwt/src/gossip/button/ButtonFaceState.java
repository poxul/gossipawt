package gossip.button;

class ButtonFaceState {

	private final boolean isPressed;
	private final boolean isBlocked;
	private final boolean isDisabled;
	private final boolean isSelected;
	final boolean isReleased;

	/**
	 * @param isPressed
	 * @param isBlocked
	 * @param isDisabled
	 * @param isSelected
	 * @param isReleased
	 */
	ButtonFaceState(boolean isPressed, boolean isBlocked, boolean isDisabled, boolean isSelected, boolean isReleased) {
		super();
		this.isPressed = isPressed;
		this.isBlocked = isBlocked;
		this.isDisabled = isDisabled;
		this.isSelected = isSelected;
		this.isReleased = isReleased;
	}

	/**
	 * @return the isPressed
	 */
	public boolean isPressed() {
		return isPressed;
	}

	/**
	 * @return the isBlocked
	 */
	public boolean isBlocked() {
		return isBlocked;
	}

	/**
	 * @return the isDisabled
	 */
	public boolean isDisabled() {
		return isDisabled;
	}

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @return the isReleased
	 */
	public boolean isReleased() {
		return isReleased;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ButtonFaceState [isPressed=");
		builder.append(isPressed);
		builder.append(", isBlocked=");
		builder.append(isBlocked);
		builder.append(", isDisabled=");
		builder.append(isDisabled);
		builder.append(", isSelected=");
		builder.append(isSelected);
		builder.append(", isReleased=");
		builder.append(isReleased);
		builder.append(']');
		return builder.toString();
	}

}