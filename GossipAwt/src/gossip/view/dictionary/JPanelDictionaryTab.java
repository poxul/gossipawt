package gossip.view.dictionary;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableModel;

import org.apache.logging.log4j.Logger;

import gossip.config.ColorConstants;
import gossip.config.ImageConstants;
import gossip.data.AwtBroker;
import gossip.data.device.DeviceData.ApplicationType;
import gossip.data.model.MySimpleModel;
import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.panel.disposable.JScrollPaneDisposable;
import gossip.lib.panel.flatbutton.DrawableFlatButton;
import gossip.lib.panel.flatbutton.FlatButton;
import gossip.lib.panel.flatbutton.MouseListenerButton;
import gossip.lib.util.MyLogger;
import gossip.util.DrawingUtil;
import gossip.view.ViewController;

public class JPanelDictionaryTab extends JPanelDisposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = MyLogger.getLog(JPanelDictionaryTab.class);

	private JScrollPaneDisposable scrollPane;

	private JTable table;

	private FlatButton buttonHide;
	private FlatButton buttonAdd;

	private final ApplicationType filterType;

	private MySimpleModel<Boolean> isKeyboard;

	private ViewController viewController;

	public JPanelDictionaryTab(ApplicationType filterType, ViewController viewController) {
		this.viewController = viewController;
		this.filterType = filterType;
		init();
	}

	private void init() {
		logger.info("start init");

		isKeyboard = AwtBroker.get().getData().getShowKeyboardProperty();
		isKeyboard.addModelChangeListener((source, origin, oldValue, newValue) -> repaint());

		buildView();

		buttonHide = new DrawableFlatButton(0, 0, ImageConstants.IMAGE_NAME_BUTTON_HIDE_CHAT, ColorConstants.BUTTON_COLOR_HIDE);
		buttonAdd = new DrawableFlatButton(0, 0, ImageConstants.IMAGE_NAME_BUTTON_ADD_MESSAGE, ColorConstants.BUTTON_COLOR_ADD);

	}

	private void buildView() {
		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(ColorConstants.EDIT_VIEW_BACKGROUND);
		add(getScrollPane(), BorderLayout.CENTER);
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPaneDisposable(getTable()) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected JViewport createViewport() {

					return new JViewport() {

						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public void paint(Graphics g) {
							super.paint(g);
							Graphics2D g2d = DrawingUtil.getGraphics2d(g);
							try {
								int xPos = getWidth() - DrawableFlatButton.BUTTON_GAP - DrawableFlatButton.BUTTON_DIAMETER;
								int yPos = DrawableFlatButton.BUTTON_GAP;
								buttonHide.draw(g2d, xPos, yPos);

								if (!isKeyboard.getValue()) {
									xPos = getWidth() - DrawableFlatButton.BUTTON_GAP - DrawableFlatButton.BUTTON_DIAMETER;
									yPos = getHeight() - (DrawableFlatButton.BUTTON_DIAMETER + DrawableFlatButton.BUTTON_GAP);
									buttonAdd.draw(g2d, xPos, yPos);
								}
							} finally {
								g2d.dispose();
							}

						}
					};
				}

			};
			scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

			JViewport viewPort = scrollPane.getViewport();

			viewPort.setOpaque(false);

			buttonHide = new DrawableFlatButton(0, 0, ImageConstants.IMAGE_NAME_BUTTON_HIDE_CHAT, ColorConstants.BUTTON_COLOR_HIDE);
			buttonAdd = new DrawableFlatButton(0, 0, ImageConstants.IMAGE_NAME_BUTTON_ADD_MESSAGE, ColorConstants.BUTTON_COLOR_ADD);

			getTable().addMouseListener(new MouseListenerButton(buttonAdd, viewPort) {

				@Override
				public void onTrigger() {
					functionAdd();
				}
			});

			getTable().addMouseListener(new MouseListenerButton(buttonHide, viewPort) {

				@Override
				public void onTrigger() {
					functionHide();
				}
			});

		}
		return scrollPane;
	}

	protected void functionAdd() {
		viewController.showKeyboard(true);
	}

	protected void functionHide() {
		viewController.showChat(false);
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable(getModel());
		}
		return table;
	}

	private TableModel getModel() {
		return new DictionaryTableModel(AwtBroker.get().getData().getClients(), filterType);
	}

}
