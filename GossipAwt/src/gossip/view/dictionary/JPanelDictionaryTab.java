package gossip.view.dictionary;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableModel;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.data.AwtBroker;
import gossip.data.device.DeviceData.ApplicationType;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.panel.disposable.JScrollPaneDisposable;
import gossip.lib.util.MyLogger;

public class JPanelDictionaryTab extends JPanelDisposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(JPanelDictionaryTab.class);

	private JScrollPaneDisposable scrollPane;

	private JTable table;

	public JPanelDictionaryTab() {
		init();
	}

	private void init() {
		logger.info("start init");
		buildView();
	}

	
	private void buildView() {
		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(ColorConstants.EDIT_VIEW_BACKGROUND);
		add(getScrollPane(), BorderLayout.CENTER);
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPaneDisposable(getTable());
			scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			scrollPane.setViewportBorder(null);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setOpaque(false);
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable(getModel());
		}
		return table;
	}

	private TableModel getModel() {
		return new DictionaryTableModel(AwtBroker.get().getData().getClients(), ApplicationType.DESKTOP);
	}
	

}
