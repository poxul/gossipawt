package gossip.lib.panel;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import gossip.button.DefaultButtonFace;
import gossip.lib.panel.disposable.Disposable;
import gossip.lib.util.StringUtil;

public class MyButton extends JButton implements Disposable {

	private static final long serialVersionUID = 1L;

	private boolean isBlocked = false;

	private final Map<String, DefaultButtonFace> faces = new HashMap<>();

	private String curFace;

	public MyButton() {
		super();
	}

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
			initCurFace(f);
			firePropertiesChanged();
		}

	}

	private void initCurFace(String key) {
		DefaultButtonFace face = getButtonFace(key);
		setText(face.getButtonText());
		Image img = face.getOverlay();
		if (img != null) {
			setIcon(new ImageIcon(img));
		}
	}

	public DefaultButtonFace getCurFace() {
		return faces.get(curFace);
	}

	public void setCenterImage(boolean b) {
		// TODO Auto-generated method stub

	}

	public void addFace(DefaultButtonFace buttonFace) {
		faces.put(buttonFace.getFaceName(), buttonFace);
	}

}
