package gossip.lib.panel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;

import gossip.button.DefaultButtonFace;
import gossip.lib.panel.disposable.Disposable;
import gossip.lib.util.StringUtil;

public class MyButton extends JButton implements Disposable {

	private static final long serialVersionUID = 1L;

	private boolean isBlocked = false;
	
	private final Map<String, DefaultButtonFace> faces = new HashMap<>();
	
	private String curFace;

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	public void setBlocked(boolean isBlocked) {
		if (this.isBlocked != isBlocked) {
			this.isBlocked = isBlocked;
			firePropertiesChanged();
		}
	}

	private void firePropertiesChanged() {
		// TODO Auto-generated method stub
	}


	public Set<String> getButtonFaceKeySet() {
		return faces.keySet();
	}

	public String getCurFaceKey() {
		return curFace;
	}

	public DefaultButtonFace getButtonFace(String key) {
		return faces.get(key);
	}

	public void setCurrentFace(String f) {
		if (!StringUtil.compare(f, curFace)) {
			this.curFace = f;
			firePropertiesChanged();
		}

	}

	public DefaultButtonFace getCurFace() {
		return faces.get(curFace);
	}

}
