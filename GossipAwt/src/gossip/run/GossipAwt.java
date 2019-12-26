package gossip.run;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.Logger;

import gossip.config.LocationUtil;
import gossip.config.LocationUtil.ViewId;
import gossip.data.AwtBroker;
import gossip.data.device.DeviceIdUtil;
import gossip.data.device.DeviceData.ApplicationType;
import gossip.lib.exception.ConfigurationException;
import gossip.lib.file.FileNameUtil;
import gossip.lib.panel.ComponentUtil;
import gossip.lib.util.CmdLineUtil;
import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;
import gossip.view.ViewController;

public class GossipAwt {

	private static Logger logger = MyLogger.getLog(GossipAwt.class);

	private static final String HELP_TEXT = "\n\tCommand line options :" + "\n\t --base <path> | -b <path>" + "\n\t [--host 127.0.0.1][-h localhost]"
			+ "\n\t [--port 45049][-p 12345]" + "\n";

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GossipAwt awtView = new GossipAwt();

		DeviceIdUtil.setType(ApplicationType.MACHINE);
		Properties prop = new Properties();
		try {
			CmdLineUtil.parseCmd(args, prop);
		} catch (ConfigurationException e) {
			logger.error(HELP_TEXT);
			System.exit(-1);
		}
		String basePath = prop.getProperty("base");
		String hostName = prop.getProperty("host");
		String strPort = prop.getProperty("port");

		if (StringUtil.isNullOrEmpty(hostName) || StringUtil.isNullOrEmpty(basePath)) {
			logger.error(HELP_TEXT);
			System.exit(-1);
		}
		int port = CmdLineUtil.parsePortNumber(strPort);
		hostName = CmdLineUtil.parseHostName(hostName);
		logger.info("client started (base)={}, (server)={}, (port)={}", basePath, hostName, port);
		// go go go
		awtView.initView(basePath, hostName, port);
	}

	private JFrame frame;
	private GossipClient gClient;

	public GossipAwt() {
		// NOP
	}

	protected void createGui(JPanel panel) {
		frame = ComponentUtil.createFrame();
		frame.setAlwaysOnTop(true);
		frame.setOpacity(0.5f);
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

	public void initView(String basePath, String host, int port) {
		try {
			ConfigurationService.initConfigurationService(basePath, ApplicationType.MACHINE, new FileNameUtil());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			System.exit(-2);
		}
		initClient(host, port);


		initData();
		ViewController viewController = new ViewController(gClient, AwtBroker.get().getData());

		SwingUtilities.invokeLater(() -> createGui(viewController.getToadtView()));
	}

}
