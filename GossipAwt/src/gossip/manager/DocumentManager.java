package gossip.manager;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import gossip.lib.util.MyLogger;


public class DocumentManager implements MyManager {

	private static final Logger logger = MyLogger.getLog(DocumentManager.class);

	private static final String MANAGER_ID = "DocumentManager";

	private static final Map<String, Document> documentCache = new HashMap<>();

	@Override
	public void postInit() {
		// NOP
	}

	public Document get(String name) {
		synchronized (documentCache) {
			return documentCache.get(name);
		}
	}

	public Document put(String name, Document doc) {
		Document old;
		synchronized (documentCache) {
			old = documentCache.put(name, doc);
			logger.info(MyLogger.OBSERVER_MARKER, "add document {} cache size ={}", name, documentCache.size());
		}
		return old;
	}

	public int size() {
		synchronized (documentCache) {
			return documentCache.size();
		}
	}

	public Document remove(String name) {
		Document old;
		synchronized (documentCache) {
			old = documentCache.remove(name);
			logger.info("remove document {} cache size ={}", name, documentCache.size());
		}
		return old;
	}

	@Override
	public String getManagerId() {
		return MANAGER_ID;
	}

	public void clear() {
		synchronized (documentCache) {
			documentCache.clear();
			logger.info("clear document cache");
		}
	}

}