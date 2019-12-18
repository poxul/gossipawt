package gossip.event;

public class KeyBoardResultEvent extends KeyBoardEvent {

	private final String result;
	private Object selection;

	public KeyBoardResultEvent(final KeyBoardResultType type, final String result) {
		super(type);
		this.result = result;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Select additional modes from QuestionSelectionList
	 * 
	 * @param selection
	 */
	public void setSelection(Object selection) {
		this.selection = selection;
	}

	/**
	 * Optional get additional modes selected from QuestionSelectionList
	 * 
	 * @return
	 */
	public Object getSelection() {
		return selection;
	}

	@Override
	public String toString() {
		return "KeyBoardResultEvent [result=" + result + ", selection=" + selection + super.toString() + "]";
	}

}
