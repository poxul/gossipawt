package gossip.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.Logger;

import gossip.lib.UUIDSupport;
import gossip.lib.util.MyLogger;

public class ListenerSet<T extends UUIDSupport> implements Serializable {

	private static final Logger logger = MyLogger.getLog(ListenerSet.class);

	
	public static interface ListenerSetProcessor<T extends UUIDSupport> {

		public void process(T item, Object event);

	}

	private Map<UUID, T> map = new ConcurrentHashMap<UUID, T>();

	/**
	 * 
	 */
	private static final long serialVersionUID = -1295945944176255253L;

	private final ListenerSetProcessor<T> processor;

	public ListenerSet(final ListenerSetProcessor<T> processor) {
		super();
		this.processor = processor;
	}

	public void add(final T o) {
		if (o != null && o.getUuid() != null) {
			map.put(o.getUuid(), o);
			fireEvent(processor, o, null);
		} else {
			throw new RuntimeException("invalid listener =" + o);
		}
	}

	public void dispose() {
		map.clear();
	}

	/**
	 * Die Methode sperrt die Daten. Sie sollte nicht für die Optimierung genutzt
	 * werden!
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	public int size() {
		return map.size();
	}

	public void fireEvent(final Object event) {
		fireEvent(processor, event);
	}

	public void fireEvent(final ListenerSetProcessor<T> p, final Object event) {
		if (!map.isEmpty()) {
			ArrayList<T> v = new ArrayList<>();
			v.addAll(map.values());
			for (T element : v) {
				fireEvent(p, element, event);
			}
		}
	}

	private void fireEvent(final ListenerSetProcessor<T> p, final T element, final Object event) {
		try {
			p.process(element, event);
		} catch (Exception e) {
			MyLogger.printExecption(logger, e);
		}
	}

	public boolean remove(final T o) {
		T val = null;
		if (o != null) {
			val = map.remove(o.getUuid());
		}
		return val != null;
	}

	/**
	 * Debug view only
	 * 
	 * @return
	 */
	public Set<Map.Entry<UUID, T>> getEntrySet() {
		return map.entrySet();
	}

}