package gossip.lib.job;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.Logger;

import gossip.lib.strict.StrictUtil;
import gossip.lib.util.MyLogger;

public abstract class ServiceJobAWTDefault extends ServiceJobAWT<Boolean, Boolean> {

	private static final Logger logger = MyLogger.getLog(ServiceJobAWTDefault.class);
	
	@Override
	protected Boolean init() {
		return Boolean.TRUE;
	}

	public ServiceJobAWTDefault(String name) {
		super(name);
	}

	@Override
	protected Boolean doInBackground(Boolean initResult) {
		if (!StrictUtil.isThread(false)) {
			try {
				SwingUtilities.invokeLater(this::startJob);
			} catch (Exception e) {
				MyLogger.printExecption(logger, e);
			}
		} else {
			startJob();
		}
		return Boolean.TRUE;
	}

	public abstract Boolean startJob();

}