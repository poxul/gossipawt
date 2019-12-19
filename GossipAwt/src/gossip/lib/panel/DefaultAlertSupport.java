package gossip.lib.panel;

import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import gossip.lib.panel.disposable.JPanelDisposable;
import gossip.lib.panel.txt.StringFormatterUtil;
import gossip.lib.util.StringUtil;
import gossip.manager.LanguageManager;
import gossip.rule.InputRule.SEVERITY;
import gossip.view.keyboard.input.AbstractInputItemChangeListener;
import gossip.view.keyboard.input.InputItemId;

public class DefaultAlertSupport extends AbstractInputItemChangeListener {

	private JPanelDisposable parent;

	public DefaultAlertSupport(JPanelDisposable parent) {
		super();
		this.parent = parent;
		init();
	}

	private final List<InputItemId> alertList = new ArrayList<>();
	private int errorId = 0;
	private JPanelAlertBox alertPanel;

	public JPanelAlertBox getAlertPanel() {
		if (alertPanel == null) {
			alertPanel = new JPanelAlertBox(600, 55);
			alertPanel.setVisible(false);
		}
		return alertPanel;
	}

	private void init() {
		parent.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentShown(ComponentEvent e) {
				updateVisible();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				updateVisible();
			}

		});
	}

	void addAllertText(InputItemId  id) {
		if (id != null) {
			LanguageManager.addInputElementChangeListener(this, id);
			alertList.add(id);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		for (InputItemId item : alertList) {
			LanguageManager.removeInputElementChangeListener(this, item);
		}
		if (alertPanel != null) {
			alertPanel.dispose();
		}
	}

	private String getAlertString(int idx, List<?> values) {
		InputItemId id = alertList.get(idx);
		String formatStr = LanguageManager.getLocaleText(id);
		return StringFormatterUtil.getFormatedText(formatStr, values, false);
	}

	@Override
	public void onItemDataChanged() {
		updateAlertString();
	}

	private boolean isVisibleRequested = false;

	public void setAlertPanelVisibleRequested(boolean mode) {
		isVisibleRequested = mode;
		updateVisible();
	}

	private void updateVisible() {
		if (parent.isShowing()) {
			_setAlertPanelVisible(isVisibleRequested);
		} else {
			_setAlertPanelVisible(false);
		}
	}

	private void _setAlertPanelVisible(boolean mode) {
		getAlertPanel().setVisible(mode);
	}

	public void setAlertSeverity(SEVERITY severity) {
		getAlertPanel().setSeverity(severity);
	}

	public void setAlertText(String text) {
		if (StringUtil.isNullOrEmpty(text)) {
			setAlertPanelVisibleRequested(false);
		} else {
			getAlertPanel().setInfoText(text);
			setAlertPanelVisibleRequested(true);
		}
	}

	private List<?> values = null;

	/**
	 * Set alert text to idx and format it with values
	 * 
	 * On i < 0 the panel will be disabled
	 * 
	 * @param i
	 * @param values
	 */
	void setError(int i, List<?> values) {
		this.errorId = i;
		this.values = values;
		updateAlertString();
	}

	private void updateAlertString() {
		if (errorId >= 0 && errorId < alertList.size()) {
			setAlertText(getAlertString(errorId, values));
			setAlertPanelVisibleRequested(true);
		} else {
			setAlertText("");
			setAlertPanelVisibleRequested(false);
		}
	}

	public JPanelDisposable getParent() {
		return parent;
	}

	public Point getParentPosition() {
		Point p = null;
		JPanelDisposable dis = getParent();
		if (dis.isShowing()) {
			p = dis.getLocationOnScreen();
		}
		return p == null ? new Point() : p;
	}

}