package gossip.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import org.apache.logging.log4j.Logger;

import gossip.config.DimensionConstants;
import gossip.config.ImageConstants;
import gossip.config.InputItemConstants;
import gossip.data.AwtBroker;
import gossip.lib.panel.button.ButtonFaceListener;
import gossip.lib.panel.button.MyButton;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.util.MyLogger;
import gossip.util.MyButtonUtil;

public class MainFooterView extends JPanelDisposable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(MainFooterView.class);

	private JPanelDisposable emotesPanel;

	private ButtonFaceListener emoteListener = new ButtonFaceAdapter() {

		@Override
		public void onButtonFaceChanged(boolean isReleased, String name, String text) {
			if (isReleased) {
				send(text);
			}
		}
	};

	public MainFooterView() {
		init();
	}

	/**
	 * Emotes panel , cancel (hide) button , show dictionary button, show keyboard
	 * button
	 */
	private void buildView() {
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder());
		setPreferredSize(new Dimension(100, DimensionConstants.FOOTER_HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(getPanelEmotes());
	}

	private void init() {
		buildView();
	}

	/**
	 * yes, no, ok, ko, bye
	 * 
	 * @return
	 */
	private JPanelDisposable getPanelEmotes() {
		if (emotesPanel == null) {
			emotesPanel = new JPanelDisposable();
			emotesPanel.setLayout(new FlowLayout());
			emotesPanel.setPreferredSize(new Dimension(300, DimensionConstants.FOOTER_HEIGHT));
			emotesPanel.setOpaque(false);
			emotesPanel.setPreferredSize(new Dimension(DimensionConstants.HEADLINE_VERSION_WIDTH, DimensionConstants.HEADLINE_HEIGHT));
			MyButton buttonYes = MyButtonUtil.createSimpleButton(InputItemConstants.ITEM_EMOTE_YES, ImageConstants.IMAGE_NAME_BUTTON_EMOTE_YES, emoteListener);
			emotesPanel.add(buttonYes);
			MyButton buttonNo = MyButtonUtil.createSimpleButton(InputItemConstants.ITEM_EMOTE_NO, ImageConstants.IMAGE_NAME_BUTTON_EMOTE_NO, emoteListener);
			emotesPanel.add(buttonNo);
		}
		return emotesPanel;
	}

	protected void send(String txt) {
		logger.info("send: {}", txt);
		/**
		 * transmit message
		 */
		AwtBroker.get().getController().say(txt);
	}

}
