package gossip.lib.diagnostic;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import gossip.lib.util.MyLogger;

public class InstanceCounter {


	public static class InstanceLogger implements Runnable {

		private static final Logger logger = MyLogger.getLog(InstanceLogger.class);
		private final String name;

		/**
		 * @param name
		 */
		public InstanceLogger(String name) {
			super();
			this.name = name;
		}

		@Override
		public void run() {
			logger.info(MyLogger.OBSERVER_MARKER, "logger {} size ={}", name, list.size());
			for (InstanceCounter i : list) {
				dumpInfo(i);
			}
		}

		private void dumpInfo(InstanceCounter i) {
			int diff = i.upCount - i.downCount;
			logger.info(MyLogger.OBSERVER_MARKER, "instances of {}" + "\n\t-alive ={}" + "\n\t-up    ={} ({})" + "\n\t-down  ={} ({})", i.name, diff, i.upCount - i.oldUp,
					i.upCount, i.downCount - i.oldDown, i.downCount);
			i.clearTempCount();
		}

	}

	private int upCount;
	private int oldUp;
	private int downCount;
	private int oldDown;
	private final String name;

	private static final List<InstanceCounter> list = new ArrayList<>();

	public static InstanceCounter createInstanceCounter(String name) {
		synchronized (list) {
			InstanceCounter i = new InstanceCounter(name);
			list.add(i);
			return i;
		}
	}

	private InstanceCounter(String name) {
		super();
		this.name = name;
	}

	public void up() {
		upCount++;
	}

	public void down() {
		downCount++;
	}

	public void clearTempCount() {
		oldUp = upCount;
		oldDown = downCount;
	}

}