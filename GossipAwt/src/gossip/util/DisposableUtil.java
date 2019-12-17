package gossip.util;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.border.Border;

import org.apache.logging.log4j.Logger;

import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;
import gossip.lib.panel.disposable.Disposable;
import gossip.lib.util.MyLogger;

public class DisposableUtil {

	private static final Logger logger = MyLogger.getLog(DisposableUtil.class);

	private DisposableUtil() {
		// it is just an util function compilation
	}

	public static void disposeContainer(Container container) {
		if (container != null) {
			disposeContainerContent(container);
			if (container instanceof Disposable) {
				((Disposable) container).dispose();
			}
		}
	}

	public static void disposeBorder(final Container container) {
		if (container instanceof JComponent) {
			Border b = ((JComponent) container).getBorder();
			if (b instanceof Disposable) {
				((Disposable) b).dispose();
			}
		}
	}

	public static void disposeContainerContent(final Container container) {
		if (container != null) {
			logger.trace("dispose ={}", container.getClass().getName());

			Component[] subComponents = container.getComponents();
			if (subComponents != null && subComponents.length > 0) {
				for (int i = 0; i < subComponents.length; i++) {
					final Component component = subComponents[i];
					ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("dispose container") {

						@Override
						public Boolean startJob() {
							if (component instanceof Disposable) {
								((Disposable) component).dispose();
							}
							disposeBorder(container);
							container.remove(component); // emits AWT events !! XXXX // WORK XX
							return true;
						}
					});
				}
			}
			disposeBorder(container);
		}
	}

}
