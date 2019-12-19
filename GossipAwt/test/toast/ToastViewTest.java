package toast;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;
import java.util.UUID;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.Logger;

import gossip.chatview.JPanelChatView;
import gossip.data.AwtBroker;
import gossip.data.MyProfileId;
import gossip.data.OperatorSayMessage;
import gossip.data.device.DeviceData.ApplicationType;
import gossip.lib.file.FileNameUtil;
import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;
import gossip.lib.panel.ComponentUtil;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.util.MyLogger;
import gossip.run.ConfigurationService;
import gossip.run.GossipClient;
import gossip.toast.ActuatedListener.ActuationState;
import gossip.toast.JPanelToastView;
import toast.LocationUtil.ViewId;

public class ToastViewTest {

	private static Logger logger = MyLogger.getLog(ToastViewTest.class);

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ToastViewTest test = new ToastViewTest();
		test.initView();
	}

	private JFrame frame;
	private JPanelDisposable contentPane;
	private GossipClient gClient;
	private JDialog dialogChat;
	private JPanelChatView chatView;
	private boolean isShowChat;

	public ToastViewTest() {
		// NOP
	}

	private JPanelChatView createChatView() {
		JPanelChatView panel = new JPanelChatView();
		// dummy message
		MyProfileId sender = new MyProfileId(new UUID(0, 1));
		OperatorSayMessage m = new OperatorSayMessage(sender, "lala", new Date());
		panel.addMessage(m);
		// dummy message end
		return panel;
	}

	private JPanelDisposable createContentPane() {
		JPanelToastView panel = new JPanelToastView();
		panel.setMessage("Hier könnte ihre Werbung stehen und weitere Kleinigkeiten");

		panel.addActuatedListener(state -> {
			if (state == ActuationState.TRIGGERED) {
				toggleDialog();
			}
		});

		return panel;
	}

	private void toggleDialog() {
		isShowChat = !isShowChat;
		showDialog(isShowChat);
	}

	private JDialog createDialogChat() {
		JDialog dialog = new JDialog();
		dialog.setUndecorated(true);
		dialog.setLayout(new BorderLayout());
		dialog.setPreferredSize(new Dimension(450, 300));
		dialog.add(getChatView(), BorderLayout.CENTER);
		dialog.pack();
		return dialog;
	}

	protected void createGui(JPanel panel) {
		frame = ComponentUtil.createFrame();
		frame.setAlwaysOnTop(true);
		frame.setContentPane(panel);
		frame.setBackground(Color.DARK_GRAY);
		frame.setPreferredSize(new Dimension(120, 25));
		frame.pack();
		frame.setVisible(true);
		frame.setLocation(LocationUtil.getLocation(ViewId.TOAST, frame.getBounds()));
	}

	private JPanelChatView getChatView() {
		if (chatView == null) {
			chatView = createChatView();
		}
		return chatView;
	}

	private JPanelDisposable getContentPane() {
		if (contentPane == null) {
			contentPane = createContentPane();
		}
		return contentPane;
	}

	public JDialog getDialogChat() {
		if (dialogChat == null) {
			dialogChat = createDialogChat();
		}
		return dialogChat;
	}

	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Connect observer
	 * 
	 * @param rows
	 * @param columns
	 */
	public void initClient(String host, int port) {
		gClient = new GossipClient(host, port);
		gClient.init();
	}

	private void initData() {
		AwtBroker.create();
		AwtBroker.get().getController().init(gClient);
	}

	public void initView() {

		String basePath = "/home/mila/git/gossipawt/GossipAwt";

		logger.debug("**************************DEBUG******************************************************");
		try {
			ConfigurationService.initConfigurationService(basePath, ApplicationType.MACHINE, new FileNameUtil());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			System.exit(-2);
		}
		initClient("localhost", 45049);
		initData();

		SwingUtilities.invokeLater(() -> createGui(getContentPane()));
	}

	protected void showDialog(boolean mode) {
		ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("view dialog: " + mode) {

			@Override
			public Boolean startJob() {
				JDialog dialog = getDialogChat();
				dialog.setVisible(mode);
				dialog.setLocation(LocationUtil.getLocation(ViewId.CHAT, dialog.getBounds()));
				return true;
			}
		});
	}

}
