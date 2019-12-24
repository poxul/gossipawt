package toast;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.Logger;

import gossip.config.LocationUtil;
import gossip.config.LocationUtil.ViewId;
import gossip.data.AwtBroker;
import gossip.data.device.DeviceData.ApplicationType;
import gossip.lib.file.FileNameUtil;
import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;
import gossip.lib.panel.ComponentUtil;
import gossip.lib.util.MyLogger;
import gossip.run.ConfigurationService;
import gossip.run.GossipClient;
import gossip.view.MainView;
import gossip.view.ViewController;
import gossip.view.toast.JPanelToastButton;
import gossip.view.toast.ActuatedListener.ActuationState;

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
	private JPanelToastButton toastView;
	private GossipClient gClient;
	private JDialog dialogChat;
	private MainView mainView;
	private boolean isShowChat;

	public ToastViewTest() {
		// NOP
	}

	private JPanelToastButton createToastView() {
		JPanelToastButton panel = new JPanelToastButton();
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
		dialog.add(getMainView(), BorderLayout.CENTER);
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

	private MainView getMainView() {
		if (mainView == null) {
			mainView = createMainView();
		}
		return mainView;
	}

	private MainView createMainView() {
		return new MainView(new ViewController(gClient));
	}

	private JPanelToastButton getContentPane() {
		if (toastView == null) {
			toastView = createToastView();
		}
		return toastView;
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
				if (mode) {
					getContentPane().clearState();
				}
				return true;
			}
		});
	}

}
