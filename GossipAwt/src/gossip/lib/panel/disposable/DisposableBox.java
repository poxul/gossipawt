package gossip.lib.panel.disposable;

import javax.swing.Box;
import javax.swing.BoxLayout;

public class DisposableBox extends Box implements Disposable {

	private static final long serialVersionUID = 1L;

	public static DisposableBox createHorizontalBox() {
		return new DisposableBox(BoxLayout.X_AXIS);
	}

	public static DisposableBox createVerticalBox() {
		return new DisposableBox(BoxLayout.Y_AXIS);
	}

	private final ContainerDisposer disposer = new ContainerDisposer(this);

	private volatile boolean isDisposing;

	public DisposableBox(int axis) {
		super(axis);
	}

	@Override
	public void dispose() {
		if (!isDisposing()) {
			isDisposing = true;
			disposer.dispose();
		}
	}

	public boolean isDisposing() {
		return isDisposing && !disposer.isDisposed();
	}

}
