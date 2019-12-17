package gossip.lib.panel.disposable;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import gossip.lib.util.MyLogger;

public class DisposablePanelAdatper implements DisposablePanel {

	private static final Logger logger = MyLogger.getLog(DisposablePanelAdatper.class);

	private volatile boolean isDisposed = false;

	private final List<Disposable> disposables = new ArrayList<>();

	@Override
	public void addDisposable(Disposable disposable) {
		disposables.add(disposable);
	}

	@Override
	public void removeDisposable(Disposable disposable) {
		disposables.remove(disposable);
	}

	@Override
	public void clearDisposable() {
		disposables.clear();
	}

	@Override
	public List<Disposable> getDisposable() {
		return disposables;
	}

	@Override
	public void dispose() {
		if (!isDisposed) {
			isDisposed = true;
			List<Disposable> temp = new ArrayList<>();
			temp.addAll(disposables);
			for (Disposable disposable : temp) {
				disposable.dispose();
			}
			clearDisposable();
		} else {
			logger.debug("dispose is runing");
		}
	}

	@Override
	public boolean isDisposing() {
		return isDisposed;
	}

}