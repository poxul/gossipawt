package gossip.data;

import java.util.HashMap;

import gossip.data.MyProfile;
import gossip.data.device.DeviceData.ApplicationType;
import gossip.data.model.MySimpleMap;
import gossip.data.model.MySimpleModel;
import gossip.lib.data.ModelChangeListener;
import gossip.util.StringValueUtil;

public class ObservableClientProfile {

	public static final String PROFILE_NAME = "name";
	public static final String HOST_NAME = "host";
	public static final String COMMENT = "comment";
	public static final String PROFILE_ID = "id";

	private MySimpleMap<String, MySimpleModel<String>> dataMap = new MySimpleMap<>(new HashMap<String, MySimpleModel<String>>());

	private final MySimpleModel<String> nameProperty = new MySimpleModel<>();
	private final MySimpleModel<String> hostnameProperty = new MySimpleModel<>();
	private final MySimpleModel<String> commentProperty = new MySimpleModel<>();
	private final MySimpleModel<String> idProperty = new MySimpleModel<>();

	private final MySimpleModel<Boolean> select = new MySimpleModel<>();

	private boolean isMachine = false;
	private MyProfile myProfile;

	public ObservableClientProfile(MyProfile p) {
		init();
		set(p);
	}

	private void init() {
		dataMap.put(PROFILE_NAME, nameProperty);
		dataMap.put(HOST_NAME, hostnameProperty);
		dataMap.put(COMMENT, commentProperty);
		dataMap.put(PROFILE_ID, idProperty);
		select.addModelChangeListener(new ModelChangeListener() {

			@Override
			public void onModelChaned(Object source, String origin, Object oldValue, Object newValue) {
				if (select.getValue()) {
					AwtBroker.get().getData().addSelected(myProfile.getProfileId());
				} else {
					AwtBroker.get().getData().removeSelected(myProfile.getProfileId());
				}

			}
		});

	}

	/**
	 * update properties
	 * 
	 * @param p
	 */
	public void set(MyProfile p) {
		this.myProfile = p;
		nameProperty.setValue(p.getName());
		hostnameProperty.setValue(StringValueUtil.stripHostName(p.getHostName()));
		commentProperty.setValue(p.getComment());
		idProperty.setValue(StringValueUtil.strValueOfProfileId(p.getProfileId()));
		isMachine = p.getType() == ApplicationType.MACHINE;
	}

	public MyProfile getMyProfile() {
		return myProfile;
	}

	public MySimpleModel<String> getValue(String key) {
		return dataMap.get(key);
	}

	public boolean isMachine() {
		return isMachine;
	}

	public boolean compare(ObservableClientProfile p) {
		if (p == null) {
			return false;
		}
		if (p.myProfile == null) {
			return false;
		}
		return myProfile.equals(p.myProfile);
	}

	public static boolean compare(ObservableClientProfile p1, ObservableClientProfile p2) {
		if (p1 == p2) {
			return true;
		}
		if (p1 == null) {
			return p2 == null;
		}
		if (p2 == null) {
			return false;
		}
		return p1.compare(p2);
	}

	public MySimpleModel<Boolean> selectProperty() {
		return select;
	}

}
