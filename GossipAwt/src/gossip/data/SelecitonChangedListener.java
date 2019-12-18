package gossip.data;

import gossip.data.MyProfileId;

public interface SelecitonChangedListener {

	void onSelectionChanged(MyProfileId selected, boolean isAdded);

}
