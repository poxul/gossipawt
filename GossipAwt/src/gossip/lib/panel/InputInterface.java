package gossip.lib.panel;

public interface InputInterface {

	public abstract void backspace();

	/**
	 * Step == 1 --> Cursor einen Buchstaben nach rechts.
	 * 
	 * Step == -1 --> Cursor einen Buchstaben nach links,
	 */
	public abstract void cursorMove(int step);

	public abstract void cleanInputText();

	public abstract boolean cleanSelected();

	/**
	 * @return the inputText
	 */
	public abstract String getInputText();

	public abstract void insertText(String text);

	/**
	 * @return the isPassword
	 */
	public abstract boolean isPassword();

	/**
	 * @param inputText the inputText to set
	 */
	public abstract void setInputText(String inputText);

}