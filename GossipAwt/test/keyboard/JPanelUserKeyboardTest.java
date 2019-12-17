package keyboard;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.Logger;

import gossip.data.device.DeviceData.ApplicationType;
import gossip.keyboard.JPanelKeyBoard;
import gossip.lib.file.FileNameUtil;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.util.MyLogger;
import gossip.run.ConfigurationService;
import gossip.util.KeyBoardUtil;
import gossip.util.KeyBoardUtil.KeyBoardType;

public class JPanelUserKeyboardTest {

	private static Logger logger = MyLogger.getLog(JPanelUserKeyboardTest.class);

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JPanelUserKeyboardTest test = new JPanelUserKeyboardTest();
		test.initView();
	}

	private JFrame frame;
	private JPanelKeyBoard contentPane;

	public JPanelUserKeyboardTest() {
		// NOP
	}

	private JPanelKeyBoard createContentPane() {
		JPanelKeyBoard panel = new JPanelKeyBoard();
		panel.setKeyBoardDefinition(KeyBoardUtil.getKeyBoardDefinition(KeyBoardType.GENERAL, Locale.GERMAN));

		logger.debug("**************************DEBUG create contenet pane*********************************");
		return panel;
	}

	protected void createGui(JPanel panel) {
		frame = new JFrame("Load histogram test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setBackground(Color.DARK_GRAY);
		frame.setPreferredSize(new Dimension(1024, 662));
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

	public void initView() {

		String basePath = "/home/mila/eclipse-workspace/GossipAwt";

		logger.debug("**************************DEBUG******************************************************");
		try {
			ConfigurationService.initConfigurationService(basePath, ApplicationType.MACHINE, new FileNameUtil());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			System.exit(-2);
		}

		SwingUtilities.invokeLater(() -> createGui(getContentPane()));
	}

}
