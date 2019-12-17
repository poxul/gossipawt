package gossip.keyboard;

import gossip.event.KeyBoardResultListener;

public interface InputPanelInterface extends InputPanelModel {

	public abstract void addKeyBoardResultListener(KeyBoardResultListener listener);

	public abstract void setVisible(boolean aFlag);

	public abstract void cleanInput();

	/**
	 * @return the quetionText
	 */
	public abstract String getQuetionText();

	public abstract void setQuestionText(String text);

	public abstract void setHeadlineText(String text);

}