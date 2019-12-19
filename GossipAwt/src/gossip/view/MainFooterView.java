package gossip.view;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import gossip.config.DimensionConstants;
import gossip.lib.panel.disposable.JPanelDisposable;

public class MainFooterView extends JPanelDisposable {

	private static final long serialVersionUID = 1L;

	public MainFooterView() {
		init();
	}

	private void buildView() {
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder());
		setPreferredSize(new Dimension(100, DimensionConstants.FOOTER_HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
	}

	private void init() {
		buildView();
	}

}
