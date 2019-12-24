package gossip.view;

import java.awt.BorderLayout;

import javax.swing.JDialog;

import gossip.config.DimensionConstants;
import gossip.config.InputItemConstants;
import gossip.config.LocationUtil;
import gossip.config.LocationUtil.ViewId;
import gossip.event.KeyBoardResultEvent;
import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;
import gossip.manager.LanguageManager;
import gossip.run.GossipClient;
import gossip.view.toast.ActuatedListener;
import gossip.view.toast.JPanelToastButton;

public class ViewController implements ActuatedListener {

	/**
	 * Main chat dialog
	 */
	private JDialog dialogChat;


	/**
	 * Dialog to enter text
	 */
	private JDialog keyboardDialog;

	/**
	 * Main panel in chat view
	 */
	private MainView mainView;
	
	/**
	 * Button you can not hide 
	 */
	private JPanelToastButton toastButton;

	/**
	 * Chat view sate
	 */
	private boolean isShowChat;

	@SuppressWarnings("unused")
	private final GossipClient client;

	public ViewController(GossipClient gClient) {
		this.client = gClient;
	}

	public void showDictionary() {
		getMainView().switchView(MainView.DICTIONARY_VIEW);
	}

	public void showChat() {
		getMainView().switchView(MainView.CHAT_VIEW);
	}

	public void showKeyboard(boolean mode) {
		if (keyboardDialog != null) {
			doKeyboard(mode);
		} else if (mode) {
			ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("view dialog: " + mode) {

				@Override
				public Boolean startJob() {
					doKeyboard(mode);
					return true;
				}
			});
		}
	}

	private void doKeyboard(boolean mode) {
		JDialog dialog = getDialogKeyboard();
		dialog.setVisible(mode);
		dialog.setLocation(LocationUtil.getLocation(ViewId.KEYBOARD, dialog.getBounds()));
	}

	@Override
	public void onActuated(ActuationState state) {
		if (state == ActuationState.TRIGGERED) {
			toggleDialog();
		}
	}

	private void toggleDialog() {
		isShowChat = !isShowChat;
		showChatDialog(isShowChat);
	}

	private MainView getMainView() {
		if (mainView == null) {
			mainView = createMainView();
		}
		return mainView;
	}

	private MainView createMainView() {
		return new MainView(this);
	}

	protected void showChatDialog(boolean mode) {
		ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("view dialog: " + mode) {

			@Override
			public Boolean startJob() {
				JDialog dialog = getDialogChat();
				dialog.setVisible(mode);
				dialog.setLocation(LocationUtil.getLocation(ViewId.CHAT, dialog.getBounds()));
				if (mode) {
					getToadtView().clearState();
				}
				return true;
			}
		});
	}

	public JPanelToastButton getToadtView() {
		if (toastButton == null) {
			toastButton = createToastButton();
		}
		return toastButton;
	}

	private JPanelToastButton createToastButton() {
		JPanelToastButton panel = new JPanelToastButton();
		panel.setMessage(getToastButtonMessage());
		panel.addActuatedListener(this);
		return panel;
	}

	private String getToastButtonMessage() {
		return LanguageManager.getLocaleText(InputItemConstants.ITEM_TOAST_BUTTON);
	}

	public JDialog getDialogChat() {
		if (dialogChat == null) {
			dialogChat = createDialogChat();
		}
		return dialogChat;
	}

	private JDialog createDialogChat() {
		JDialog dialog = new JDialog();
		dialog.setUndecorated(true);
		dialog.setLayout(new BorderLayout());
		dialog.setPreferredSize(DimensionConstants.CHAT_DIALOG_DIMENSION);
		dialog.add(getMainView(), BorderLayout.CENTER);
		dialog.pack();
		return dialog;
	}

	public JDialog getDialogKeyboard() {
		if (keyboardDialog == null) {
			keyboardDialog = createDialogKeyBoard();
		}
		return keyboardDialog;
	}

	private JDialog createDialogKeyBoard() {
		return KeyboardDialog.createDialogKeyBoard(this);
	}

	public void onKeyboard(KeyBoardResultEvent event) {
		// TODO handle keyboard result
	}

}