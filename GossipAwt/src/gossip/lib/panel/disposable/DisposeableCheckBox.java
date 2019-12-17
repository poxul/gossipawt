package gossip.lib.panel.disposable;

import javax.swing.JCheckBox;

public class DisposeableCheckBox extends JCheckBox implements Disposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ContainerDisposer disposer = new ContainerDisposer(this);

	public DisposeableCheckBox() {
		super();
	}

	public DisposeableCheckBox(String string) {
		super(string);
	}

	@Override
	public void dispose() {
		disposer.dispose();
	}

}
