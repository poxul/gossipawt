package gossip.manager;

public class DocumentManagerUtil {

	private DocumentManagerUtil() {
	}

	private static DocumentManager dokumentManager;

	private static DocumentManager createDocumentManager() {
		return new DocumentManager();
	}

	public static synchronized DocumentManager getDocumentManager() {
		if (dokumentManager == null) {
			dokumentManager = createDocumentManager();
		}
		return dokumentManager;
	}

}
