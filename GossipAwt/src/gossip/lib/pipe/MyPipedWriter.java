package gossip.lib.pipe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.apache.logging.log4j.Logger;

import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;

public class MyPipedWriter extends BufferedWriter {

	private class ObserverThread extends Thread {

		private final FileSystem fileSystem = FileSystems.getDefault();
		private WatchService watcher;

		private BufferedReader checkCreateReader(File file) throws FileNotFoundException {
			if (file != null && file.exists() && file.canRead()) {
				logger.debug("file is valid: {}", file);
				DataInputStream in = new DataInputStream(new FileInputStream(file));
				return new BufferedReader(new InputStreamReader(in));
			} else {
				return null;
			}
		}

		private void clearReader(BufferedReader br) throws IOException {
			if (br != null) {
				logger.debug("clear buffer");
				// read until empty
				String line = null;
				String lastLine = null;
				do {
					lastLine = line;
					line = br.readLine();
					logger.trace("clear buffer line: {}", line);
				} while (line != null);
				process(lastLine);
			}
		}

		private void observer() throws InterruptedException, IOException {
			boolean done = false;
			/*
			 * initial check for existing file
			 */
			BufferedReader br = checkCreateReader(file);
			clearReader(br);

			while (!done) {

				WatchKey key = watcher.take();
				logger.debug("observed: {}", key.isValid());

				List<WatchEvent<?>> events = key.pollEvents();
				for (WatchEvent<?> event : events) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
						logger.debug("create: {}", event.kind());
						/*
						 * open stream and read
						 * 
						 */
						if (br == null) {
							logger.trace("check open reader");
							br = checkCreateReader(file);
							clearReader(br);
							logger.trace("created: {}", br);
						} else {
							logger.debug("skip open reader");
						}
					}
					if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
						logger.debug("delete: {}", event.kind());
						/*
						 * close stream
						 */
						if (br != null && (!file.exists() || !file.canRead())) {
							br.close();
							br = null;
						}
					}
					if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
						logger.debug("modify: {}", event.kind());
						/*
						 * read file if exist
						 */
						if (br != null) {
							while (process(br))
								;
						}
					}
				}

				if (!key.reset()) {
					done = true;
				}
			}

			logger.debug("done 2");

		}

		private boolean process(BufferedReader br) throws IOException {
			if (br == null) {
				throw new IOException("no reader");
			}
			String result = br.readLine();
			return process(result);
		}

		private boolean process(String result) throws IOException {
			if (!StringUtil.isNullOrEmpty(result)) {
				logger.trace("process: {}", result);
				write(result + "\n");
				flush();
				return true;
			} else {
				return false;
			}
		}

		private void registerWatcher() throws IOException {
			watcher = fileSystem.newWatchService();
			Path myDir = fileSystem.getPath(file.getParent());
			myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		}

		@Override
		public void run() {

			try {
				logger.info("start register: {}", file);
				registerWatcher();
				logger.info("start observer");
				observer();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			} catch (InterruptedException e) {
				// Restore interrupted state...
				Thread.currentThread().interrupt();
			}

		}
	}

	private static Logger logger = MyLogger.getLog(MyPipedWriter.class);

	private final File file;

	private final ObserverThread worker = new ObserverThread();

	public MyPipedWriter(File file, PipedReader snk) throws IOException {
		super(new PipedWriter(snk));
		this.file = file;
		worker.start();
	}

	@Override
	public void close() throws IOException {
		worker.interrupt();
		super.close();
	}

}
