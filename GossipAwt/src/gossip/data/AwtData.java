package gossip.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gossip.data.MyProfile;
import gossip.data.MyProfileId;
import gossip.data.OperatorSayMessage;
import gossip.data.model.MySimpleList;
import gossip.data.model.MySimpleMap;
import gossip.data.model.MySimpleModel;
import gossip.data.model.MySimpleSet;
import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;

public class AwtData {

	private final MySimpleModel<Boolean> serverConnectedProperty = new MySimpleModel<>(false);

	private final MySimpleModel<Integer> numConnectedProperty = new MySimpleModel<>(0);

	private final MySimpleModel<Integer> numReceiverProperty = new MySimpleModel<>(0);

	private final MySimpleModel<String> myNameProperty = new MySimpleModel<>();

	private final MySimpleMap<MyProfileId, ObservableClientProfile> clients = new MySimpleMap<>(new HashMap<MyProfileId, ObservableClientProfile>());

	private final MySimpleSet<MyProfileId> selected = new MySimpleSet<>(new HashSet<MyProfileId>());

	private final MySimpleList<OperatorSayMessage> osmStack = new MySimpleList<>(new ArrayList<OperatorSayMessage>());

	private final List<SelecitonChangedListener> selectionListenerList = new ArrayList<>();

	public void setConnected(boolean isConnected) {
		ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("scp") {

			@Override
			public Boolean startJob() {
				serverConnectedProperty.setValue(isConnected);
				return true;
			}
		});
	}

	public void setMyProfile(final MyProfile p) {
		if (p != null) {
			ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("scp") {

				@Override
				public Boolean startJob() {
					upateMyName(p);
					return true;
				}
			});
		}
	}

	private void upateMyName(MyProfile p) {
		myNameProperty.setValue(p.getName());
	}

	public MySimpleModel<Boolean> getServerConnectedProperty() {
		return serverConnectedProperty;
	}

	public MySimpleModel<String> getMyNameProperty() {
		return myNameProperty;
	}

	public void setConnectionsSize(final int size) {
		ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("scp") {

			@Override
			public Boolean startJob() {
				numConnectedProperty.setValue(size);
				return true;
			}
		});
	}

	public MySimpleModel<Integer> getNumConnectedProperty() {
		return numConnectedProperty;
	}

	public void setConnections(final Collection<MyProfile> collection) {
		ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("scp") {

			@Override
			public Boolean startJob() {
				upateClientsList(collection);
				return true;
			}
		});
	}

	
	private void upateClientsList(Collection<MyProfile> collection) {

		Set<MyProfileId> keys = clients.keySet();

		List<MyProfileId> removeList = new ArrayList<>();

		for (MyProfileId k : keys) {
			if (!containsId(collection, k)) {
				removeList.add(k);
			}
		}

		if (!removeList.isEmpty()) {
			for (MyProfileId id : removeList) {
				clients.remove(id);
				selected.remove(id);
			}
		}

		for (MyProfile myProfile : collection) {
			if (clients.containsKey(myProfile.getProfileId())) {
				ObservableClientProfile val = clients.get(myProfile.getProfileId());
				val.set(myProfile);
			} else {
				clients.put(myProfile.getProfileId(), new ObservableClientProfile(myProfile));
			}
		}
	}

	private boolean containsId(Collection<MyProfile> collection, MyProfileId id) {
		for (MyProfile myProfile : collection) {
			if (myProfile.getProfileId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public MySimpleMap<MyProfileId, ObservableClientProfile> getClients() {
		return clients;
	}

	public void addSelected(MyProfileId id) {
		synchronized (selected) {
			selected.add(id);
			fireSelectionChanged(id, true);
		}
	}

	public void removeSelected(MyProfileId id) {
		synchronized (selected) {
			selected.remove(id);
			fireSelectionChanged(id, false);
		}
	}

	public void addSelectionChangedListener(SelecitonChangedListener listener) {
		synchronized (selectionListenerList) {
			selectionListenerList.add(listener);
		}
	}

	public void removeSelectionChangedListener(SelecitonChangedListener listener) {
		synchronized (selectionListenerList) {
			selectionListenerList.remove(listener);
		}
	}

	private void fireSelectionChanged(MyProfileId id, boolean isAdded) {
		synchronized (selectionListenerList) {
			for (SelecitonChangedListener listener : selectionListenerList) {
				listener.onSelectionChanged(id, isAdded);
			}
		}
	}

	public MySimpleModel<Integer> getNumReceiverProperty() {
		return numReceiverProperty;
	}

	public void setReceiverSize(final int size) {
		ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("scp") {

			@Override
			public Boolean startJob() {
				numReceiverProperty.setValue(size);
				return true;
			}
		});
	}

	public void addMessage(OperatorSayMessage osm) {
		osmStack.add(osm);
	}

	public MySimpleList<OperatorSayMessage> getOsmStack() {
		return osmStack;
	}

}
