package gossip.lib.panel.disposable;

import java.util.List;

public interface DisposablePanel extends Disposable {

	public void addDisposable(Disposable disposable);
	
	public void removeDisposable(Disposable disposable);

	public List<Disposable> getDisposable();

	public void clearDisposable();
	
	public boolean isDisposing();
}