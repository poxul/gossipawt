package gossip.run;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.Logger;

import gossip.config.LocationUtil;
import gossip.config.LocationUtil.ViewId;
import gossip.data.AwtBroker;
import gossip.data.device.DeviceData.ApplicationType;
import gossip.lib.file.FileNameUtil;
import gossip.lib.panel.ComponentUtil;
import gossip.lib.util.MyLogger;
import gossip.view.ViewController;

public class GossipAwt {

	private static Logger logger = MyLogger.getLog(GossipAwt.class);

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GossipAwt test = new GossipAwt();
		test.initView();
	}

	private JFrame frame;
	private GossipClient gClient;


	public GossipAwt() {
		// NOP
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

		ViewController viewController = new ViewController(gClient);

		initData();

		SwingUtilities.invokeLater(() -> createGui(viewController.getToadtView()));
	}

}
