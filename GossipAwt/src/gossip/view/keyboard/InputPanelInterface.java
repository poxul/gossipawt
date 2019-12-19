package gossip.view.keyboard;

import gossip.event.KeyBoardResultListener;

public interface InputPanelInterface extends InputPanelModel {

	public abstract void addKeyBoardResultListener(KeyBoardResultListener listener);

	public abstract void setVisible(boolean aFlag);

	public abstract void cleanInput();

	/**
	 * @return the quetionText
	 */
	public abstract String getInputLabelText();

	public abstract void setInputLabelText(String text);

}