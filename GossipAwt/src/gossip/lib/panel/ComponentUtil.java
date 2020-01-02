package gossip.lib.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.logging.log4j.Logger;

import gossip.config.DimensionConstants;
import gossip.lib.util.MyLogger;

public class ComponentUtil {

	private static final Logger logger = MyLogger.getLog(ComponentUtil.class);

	private static boolean checkLookAndFeel(String name) {
		boolean rc = false;
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				logger.info("installed look and feel ={}", info.getName()); //$NON-NLS-1$
				if (name.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					logger.info("used look and feel ={}", info.getClassName()); //$NON-NLS-1$
					rc = true;
					break;
				}
			}

		} catch (Exception e) {
			logger.warn("requested look and feel is not supported =" + name); //$NON-NLS-1$
			logger.warn(e.getMessage(), e);
		}

		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");
		return rc;
	}

	public static JFrame createFrame() {
		logger.info("build view"); //$NON-NLS-1$

		// Look and feel
		// GTK is buggy :-(
		// if (!checkLookAndFeel("GTK+")) { //$NON-NLS-1$
		// checkLookAndFeel("Nimbus"); //$NON-NLS-1$
		// }

		if (!checkLookAndFeel("Nimbus")) { //$NON-NLS-1$
			checkLookAndFeel("Metal"); //$NON-NLS-1$
		}

		// tabbed pane
		UIManager.put("TabbedPane.borderHightlightColor", Color.BLACK);
		UIManager.put("TabbedPane.darkShadow", Color.BLACK);
		UIManager.put("TabbedPane.shadow", Color.LIGHT_GRAY);
		UIManager.put("TabbedPane.light", Color.BLACK);
		UIManager.put("TabbedPane.highlight", Color.BLACK);
		UIManager.put("TabbedPane.focus", Color.BLACK);
		UIManager.put("TabbedPane.selectHighlight", Color.BLACK);
		UIManager.put("TabbedPane.selected", Color.WHITE); // Selektierter tab
		UIManager.put("TabbedPane.contentAreaColor", Color.WHITE); // Inhalt des selektierten Tabs
		UIManager.put("TabbedPane.selectedForeground", Color.BLACK); //
		UIManager.put("TabbedPane.tabAreaBackground", Color.WHITE);
		UIManager.put("TabbedPane.tabsOverlapBorder", Boolean.TRUE);
		UIManager.put("TabbedPane.tabAreaInsets", new Insets(5, 5, 5, 5));
		UIManager.put("TabbedPane.tabInsets", new Insets(5, 5, 5, 5));

		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		JFrame frame = new JFrame(); // $NON-NLS-1$
		SwingUtilities.updateComponentTreeUI(frame);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(DimensionConstants.FRAME_MINIMUM_DIM_WIDTH, DimensionConstants.FRAME_MINIMUM_DIM_HEIGHT));
		return frame;
	}

}
