package gossip.lib.pipe;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PipedReader;

import org.apache.logging.log4j.Logger;

import gossip.lib.job.ServiceJobDefault;
import gossip.lib.util.MyLogger;

public abstract class MyPipeObserver extends ServiceJobDefault {

	private static Logger logger = MyLogger.getLog(MyPipeObserver.class);

	private final File file;

	private volatile boolean done = false;

	public MyPipeObserver(File file) {
		super("pipe observer " + file);
		this.file = file;
	}

	public abstract void received(String txt);

	public void close() {
		done = true;
	}

	
	@Override
	protected Integer doInBackground(Integer initResult) {
		PipedReader in = new PipedReader();
		try (MyPipedWriter pr = new MyPipedWriter(file, in)) {
			logger.info("start: {}", pr);
			try (BufferedReader br = new BufferedReader(in)) {
				logger.info("start reading: {}", br);
				while (!done) {
					String line = br.readLine();
					received(line);
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				done = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

}