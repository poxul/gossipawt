package pipe;

import java.io.File;

import org.apache.logging.log4j.Logger;

import gossip.lib.pipe.MyPipeObserver;
import gossip.lib.util.MyLogger;

public class MyPipeTest {

	private static Logger logger = MyLogger.getLog(MyPipeTest.class);

	public static void main(String[] args) {
		logger.info("test");
		File file = new File("test/pipe/test.p");
		MyPipeObserver obs = new MyPipeObserver(file) {

			@Override
			public void received(String line) {
				logger.info("read: {}", line);
				if ("BYE".equalsIgnoreCase(line)) {
					logger.info("exit loop");
					close();
				}

			}

		};
		new Thread(obs).start();
	}

}
