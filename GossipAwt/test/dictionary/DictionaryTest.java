package dictionary;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.Logger;

import gossip.data.AwtBroker;
import gossip.data.device.DeviceData.ApplicationType;
import gossip.lib.file.FileNameUtil;
import gossip.lib.panel.ComponentUtil;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.util.MyLogger;
import gossip.run.ConfigurationService;
import gossip.run.GossipClient;
import gossip.view.dictionary.JPanelDictionary;

public class DictionaryTest {

	private static Logger logger = MyLogger.getLog(DictionaryTest.class);

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DictionaryTest test = new DictionaryTest();
		test.initView();
	}

	private JFrame frame;
	private JPanelDisposable contentPane;
	private GossipClient gClient;

	public DictionaryTest() {
		// NOP
	}

	private JPanelDictionary createContentPane() {
		JPanelDictionary panel = new JPanelDictionary();
		return panel;
	}

	protected void createGui(JPanel panel) {
		frame = ComponentUtil.createFrame();
		frame.setAlwaysOnTop(true);
		frame.setContentPane(panel);
		frame.setBackground(Color.DARK_GRAY);
		frame.setPreferredSize(new Dimension(720, 240));
		frame.pack();
		frame.setVisible(true);
	}

	private JPanelDisposable getContentPane() {
		if (contentPane == null) {
			contentPane = createContentPane();
		}
		return contentPane;
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

}
