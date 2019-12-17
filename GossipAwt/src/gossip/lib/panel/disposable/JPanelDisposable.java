package gossip.lib.panel.disposable;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import org.apache.logging.log4j.Logger;

import gossip.lib.diagnostic.InstanceCounter;
import gossip.lib.util.MyLogger;

public class JPanelDisposable extends JPanel implements Disposable {

	private static final long serialVersionUID = 1L;


	private static final Logger logger = MyLogger.getLog(JPanelDisposable.class);
	
	
	private final ContainerDisposer disposer = new ContainerDisposer(this);

	private static final InstanceCounter ic = InstanceCounter.createInstanceCounter(JPanelDisposable.class.getName());

	private volatile boolean isDisposing;

	public JPanelDisposable() {
		super();
		ic.up();
	}

	public JPanelDisposable(LayoutManager layout) {
		super(layout);
		ic.up();
	}

	@Override
	public Component add(Component comp) {
		if (comp == null || isDisposing()) {
			logger.error("invalid container to add {}", this);
		}
		return super.add(comp);
	}

	@Override
	public void doLayout() {
		try {
			super.doLayout();
		} catch (Exception e) {
			MyLogger.printExecption(logger, e);
			/*
			 * find childs
			 */
			Component[] comps = getComponents();
			for (int i = 0; i < comps.length; i++) {
				Component sub = comps[i];
				if (sub != null) {
					logger.error("exception class child {}={} # {}", i, sub.getClass().getName(), sub);
				} else {
					logger.error("sub missing!!");
				}
			}

			/*
			 * find parents
			 */
			Container parent = getParent();
			if (parent != null) {
				logger.error("exception class parent ={} # {}", parent.getClass().getName(), parent);
			}
			MyLogger.printExecption(logger, e);
		}
	}

	@Override
	public void dispose() {
		if (!isDisposing()) {
			logger.debug("dispose");
			isDisposing = true;
			disposer.dispose();
			ic.down();
		}
	}

	public boolean isDisposing() {
		return isDisposing && !disposer.isDisposed();
	}

	@Override
	public void removeNotify() {
		if (isDisposing) {
			// TODO clean up listener
		}
		super.removeNotify();
	}

}
