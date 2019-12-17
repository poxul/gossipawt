package gossip.button;

import java.awt.Component;
import java.awt.Image;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import gossip.event.ListenerSet;
import gossip.event.ListenerSet.ListenerSetProcessor;
import gossip.event.PressedReleasedListener;
import gossip.inputelement.InputItemChangeListener;
import gossip.inputelement.InputItemId;
import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;
import gossip.lib.panel.DisableListener;
import gossip.lib.panel.disposable.DisposablePanelAdatper;
import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;
import gossip.manager.ImageManager;
import gossip.manager.LanguageManager;
import gossip.util.ObjectUtil;

public class DefaultButtonFace extends DisposablePanelAdatper implements PressedReleasedListener, DisableListener, InputItemChangeListener {

	private class PressedJob extends ServiceJobAWTDefault {

		private final boolean isPressed;

		private PressedJob(boolean isPressed) {
			super("pressed =" + isPressed);
			this.isPressed = isPressed;
		}

		@Override
		public Boolean startJob() {
			setPressedIntern(isPressed);
			logger.debug("Pressed job: {}", isPressed);
			return true;
		}

		@Override
		public String toString() {
			return "button pressed =" + isPressed;
		}
	}

	private class UpdateJob extends ServiceJobAWTDefault {

		private final UUID objectId;

		private UpdateJob(UUID objectId) {
			super("update");
			this.objectId = objectId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			UpdateJob other = (UpdateJob) obj;
			if (objectId == null) {
				if (other.objectId != null) {
					return false;
				}
			} else if (!objectId.equals(other.objectId)) {
				return false;
			}
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
			return result;
		}

		@Override
		public Boolean startJob() {
			fireButtonFaceChanged(false);
			logger.debug("Update job {} {}", getFaceName(), objectId);
			return true;
		}

		@Override
		public String toString() {
			return "button face update";
		}
	}

	public enum ActivationMode {
		NORMAL,
		ACTIVE,
		LOCKED
	}

	protected boolean isPressed = false;
	protected boolean isBlocked = false;
	protected boolean isDisabled = false;
	protected boolean isSelected = false;

	protected String buttonText = null;
	protected final String faceName;
	protected InputItemId inputItemId;

	private Image overlay;

	private int xPosition = 0;
	private int yPosition = 0;

	private class ButtonFaceProcessor implements ListenerSetProcessor<ButtonFaceListener> {

		@Override
		public void process(ButtonFaceListener listener, Object event) {
			if (event instanceof ButtonFaceState) {
				ButtonFaceState state = (ButtonFaceState) event;
				listener.onButtonFaceChanged(state.isReleased, getFaceName(), getButtonText());
			}
		}

	}

	private final ListenerSet<ButtonFaceListener> listenerList = new ListenerSet<ButtonFaceListener>(new ButtonFaceProcessor());

	protected static final Logger logger = MyLogger.getLog(DefaultButtonFace.class);
	private boolean isPressedRequested = false;
	protected UUID objectId;

	private ActivationMode mode = ActivationMode.NORMAL; // shift alt ... 0 = normal
	private volatile String fixedText;

	public static DefaultButtonFace createButtonFace(String name) {
		return new DefaultButtonFace(name);
	}

	DefaultButtonFace(String name) {
		super();
		faceName = name;
	}

	public void addButtonFaceListener(ButtonFaceListener listener) {
		if (listener != null) {
			listenerList.add(listener);
		}
	}

	void clearPressed() {
		isPressedRequested = false;
		isPressed = isPressedRequested;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DefaultButtonFace other = (DefaultButtonFace) obj;
		if (buttonText == null) {
			if (other.buttonText != null) {
				return false;
			}
		} else if (!buttonText.equals(other.buttonText)) {
			return false;
		}
		if (faceName == null) {
			if (other.faceName != null) {
				return false;
			}
		} else if (!faceName.equals(other.faceName)) {
			return false;
		}
		if (inputItemId == null) {
			if (other.inputItemId != null) {
				return false;
			}
		} else if (!inputItemId.equals(other.inputItemId)) {
			return false;
		}
		if (objectId == null) {
			if (other.objectId != null) {
				return false;
			}
		} else if (!objectId.equals(other.objectId)) {
			return false;
		}
		return true;
	}

	private void fireButtonFaceChanged(boolean isReleased) {
		ButtonFaceState state = new ButtonFaceState(isPressed, isBlocked, isDisabled, isSelected, isReleased);
		listenerList.fireEvent(state);
	}

	public String getButtonText() {
		return buttonText;
	}

	public String getFaceName() {
		return faceName;
	}

	/**
	 * 
	 * @return
	 */
	public InputItemId getInputItemId() {
		return inputItemId;
	}

	@Override
	public UUID getUuid() {
		if (objectId == null) {
			objectId = UUID.randomUUID();
		}
		return objectId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buttonText == null) ? 0 : buttonText.hashCode());
		result = prime * result + ((faceName == null) ? 0 : faceName.hashCode());
		result = prime * result + ((inputItemId == null) ? 0 : inputItemId.hashCode());
		result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
		return result;
	}

	/**
	 * @return the isBlocked
	 */
	public boolean isBlocked() {
		return isBlocked;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public boolean isPressed() {
		return isPressed;
	}

	public boolean isSelected() {
		return isSelected;
	}

	void _onButtonPressed() {
		if (!isDisabled()) {
			if (setPressed(true)) {
				onButtonPressed();
			}
		}
	}

	@Override
	public void onButtonPressed() {
		// NOP
	}

	void _onButtonReleased(boolean doAction) {
		if (setPressed(false)) {
			if (doAction) {
				onButtonReleased();
			} else {
				onButtonLost();
			}
		}
	}

	@Override
	public void onButtonReleased() {
		// NOP
	}

	@Override
	public void onButtonLost() {
		// NOP
	}

	@Override
	public void onItemDataChanged() {
		updateButtonText();
	}

	private void updateButtonText() {
		if (!isFixedText()) {
			setButtonText(LanguageManager.getLocaleText(inputItemId));
		} else {
			setButtonText(fixedText);
		}
	}

	void removeButtonFaceListener(ButtonFaceListener listener) {
		listenerList.remove(listener);
	}

	/**
	 * @param isBlocked the isBlocked to set
	 */
	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public void setFixedText(String text) {
		if (!StringUtil.compare(this.fixedText, text)) {
			this.fixedText = text;
			updateButtonText();
		}
	}

	public boolean isFixedText() {
		return !StringUtil.isNullOrEmpty(fixedText);
	}

	private void setButtonText(String buttonText) {
		if (buttonText == null) {
			buttonText = "";
		}
		if (!StringUtil.compare(this.buttonText, buttonText)) {
			this.buttonText = buttonText;
			fireButtonFaceChanged(false);
		}
	}

	@Override
	public boolean setDisabled(boolean isDisabled) {
		boolean isChanged = false;
		if (this.isDisabled != isDisabled) {
			this.isDisabled = isDisabled;
			if (isDisabled) {
				setPressedIntern(false);
			}
			isChanged = true;
		}
		return isChanged;
	}

	/**
	 * 
	 */
	public void setInputItemId(InputItemId itemId) {
		if (!ObjectUtil.compare(itemId, this.inputItemId)) {
			if (this.inputItemId != null) {
				LanguageManager.removeInputElementChangeListener(this, itemId);
			}
			this.inputItemId = itemId;
			if (this.inputItemId != null) {
				LanguageManager.addInputElementChangeListener(this, itemId);
			}
		}
	}

	private synchronized boolean setPressed(boolean isPressed) {
		boolean rc = false;
		if (isPressed != isPressedRequested) {
			PressedJob job = new PressedJob(isPressed);
			ServiceJobAWTUtil.invokeAWT(job);
			isPressedRequested = isPressed;
			rc = true;
		}
		return rc;
	}

	protected void setPressedIntern(boolean isPressed) {
		if (this.isPressed != isPressed) {
			this.isPressed = isPressed;
			fireButtonFaceChanged(!isPressed);
		}
	}

	public void setSelected(boolean isSelected) {
		if (this.isSelected != isSelected) {
			this.isSelected = isSelected;
			if (isSelected) {
				setPressedIntern(false);
			}
		}
	}

	synchronized void update() {
		ServiceJobAWTDefault job = new UpdateJob(objectId);
		ServiceJobAWTUtil.invokeAWT(job);
	}

	boolean setPosition(int x, int y) {
		boolean isPositionChanged = false;
		if (xPosition != x || yPosition != y) {
			xPosition = x;
			yPosition = y;
			isPositionChanged = true;
		}
		return isPositionChanged;
	}

	public int getXPosition() {
		return xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public Image getOverlay() {
		return overlay;
	}

	public void setOverlay(String overlayName, Component comp) {
		setOverlay(ImageManager.getImage(overlayName, comp, 40, 40));
	}

	public void setOverlay(Image overlay) {
		if (overlay != null && !ObjectUtil.compare(this.overlay, overlay)) {
			this.overlay = ImageManager.scaleImage(overlay, 40, 40);
		}
	}

	public boolean isOverlay() {
		return overlay != null;
	}

	public void setMode(ActivationMode mode) {
		this.mode = mode;
	}

	public ActivationMode getMode() {
		return mode;
	}

	@Override
	public void dispose() {
		super.dispose();
		if (this.inputItemId != null) {
			LanguageManager.removeInputElementChangeListener(this, inputItemId);
		}
		listenerList.dispose();
	}

}