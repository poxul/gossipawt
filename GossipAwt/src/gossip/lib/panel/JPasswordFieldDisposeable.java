package gossip.lib.panel;

import javax.swing.JPasswordField;

import gossip.lib.panel.disposable.ContainerDisposer;
import gossip.lib.panel.disposable.Disposable;

public class JPasswordFieldDisposeable extends JPasswordField implements Disposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ContainerDisposer disposer = new ContainerDisposer(this);

	@Override
	public void dispose() {
		disposer.dispose();
	}
}
