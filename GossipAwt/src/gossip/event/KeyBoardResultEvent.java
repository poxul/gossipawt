package gossip.event;

public class KeyBoardResultEvent extends KeyBoardEvent {

	private final String result;
	private Object value;
	private Object selection;

	public KeyBoardResultEvent(final KeyBoardResultType type, final String result, final Object value) {
		super(type);
		this.result = result;
		this.value = value;
	}

	public KeyBoardResultEvent(final KeyBoardResultType type, final String result) {
		this(type, result, null);
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
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
		return "KeyBoardResultEvent type:" + getType().name();
	}
}
