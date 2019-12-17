package gossip.lib.panel.disposable;

import java.awt.Container;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.Logger;

import gossip.lib.strict.StrictUtil;
import gossip.lib.util.MyLogger;
import gossip.util.DisposableUtil;

public class ContainerDisposer {

	private static final Logger logger = MyLogger.getLog(ContainerDisposer.class);

	private volatile boolean isDisposed = false;

	private final Container parent;

	private static int instanceCount = 0;

	private static int addCount = 0;
	private static int removeCount = 0;
	private static int instanceCountTemp = 0;

	private static synchronized void countInstance(boolean up) {
		if (up) {
			instanceCount++;
			addCount++;
		} else {
			instanceCount--;
			removeCount++;
		}
		if (addCount >= 100 || removeCount >= 100) {
			logger.debug(MyLogger.WATCH_MARKER, "disposable instances ={} diff: {} added: {} removed: {}",
					instanceCount, Math.abs(instanceCount - instanceCountTemp), addCount, removeCount);
			instanceCountTemp = instanceCount;
			addCount = 0;
			removeCount = 0;
		}
	}

	/**
	 * @param parent
	 */
	public ContainerDisposer(final Container parent) {
		super();
		this.parent = parent;
		countInstance(true);
	}

	public boolean canDispose() {
		return !isDisposed;
	}

	public boolean isDisposed() {
		return isDisposed;
	}

	public void dispose() {
		if (!isDisposed) {
			isDisposed = true;
			if (!StrictUtil.isThread(false)) {
				try {
					SwingUtilities.invokeAndWait(this::disposeContainer);
				} catch (Exception e) {
					MyLogger.printExecption(logger, e);
				}
			} else {
				disposeContainer();
			}
		}
	}

	public void disposeContainer() {
		countInstance(false);
		DisposableUtil.disposeContainer(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContainerDisposer [isDisposed=");
		builder.append(isDisposed);
		builder.append(", parent=");
		builder.append(parent);
		builder.append("]");
		return builder.toString();
	}

}