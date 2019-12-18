package gossip.lib.panel;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.apache.logging.log4j.Logger;

import gossip.button.DefaultButtonFace;
import gossip.lib.panel.disposable.Disposable;
import gossip.lib.panel.exception.InitalisationException;
import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;

public class MyButton extends JButton implements Disposable {

	private static final Logger logger = MyLogger.getLog(MyButton.class);

	private static final long serialVersionUID = 1L;

	private boolean isBlocked = false;

	private final Map<String, DefaultButtonFace> faces = new HashMap<>();

	private String curFace;

	private boolean isPressable = true;

	private final MouseListener buttonMouseListener = new MouseAdapter() {

		@Override
		public void mousePressed(final MouseEvent e) {
			logger.debug("MOUSE_PRESSED");
			if (e.isConsumed()) {
				logger.debug("MOUSE_PRESSED IS-CONSUMED");
			} else if (isInButtonBounds(e.getPoint()) && setPressedIntern(true, true)) {
				e.consume();
			}
		}

		@Override
		public void mouseReleased(final MouseEvent e) {
			logger.debug("MOUSE_RELEASED");
			if (e.isConsumed()) {
				logger.debug("MOUSE_PRESSED IS-CONSUMED");
			} else {
				if (setPressedIntern(false, isInButtonBounds(e.getPoint()))) {
					e.consume();
				}
			}
		}

		private boolean isInButtonBounds(final Point p) {
			return contains(getBounds(), p);
		}

		private boolean contains(Rectangle bounds, Point p) {
			return p.y >= 0 && p.x >= 0 && bounds.height >= p.y && bounds.width >= p.x;
		}

		private boolean setPressedIntern(final boolean mode, boolean isOk) {
			boolean rc = false;
			for (Map.Entry<String, DefaultButtonFace> entry : faces.entrySet()) {
				DefaultButtonFace bFace = entry.getValue();
				if (!bFace.isDisabled() && entry.getKey().equals(getCurFaceKey())) {
					if (mode) {
						if (isPressable) {
							bFace._onButtonPressed();
							rc = true;
						}
					} else {
						bFace._onButtonReleased(isOk);
						rc = true;
					}
				}
			}
			return rc;
		}

	};

	public MyButton() {
		super();
		init();
	}

	private void init() {
		addMouseListener(buttonMouseListener);
	}

	@Override
	public void dispose() {
		// NOP
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

	public void addFace(DefaultButtonFace buttonFace) {
		if (buttonFace == null) {
			throw new InitalisationException("invalid button face");
		}
		faces.put(buttonFace.getFaceName(), buttonFace);
		if (StringUtil.isNullOrEmpty(curFace)) {
			setCurrentFace(buttonFace.getFaceName());
		}
	}

}
