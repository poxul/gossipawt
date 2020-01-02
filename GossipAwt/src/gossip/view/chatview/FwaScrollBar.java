/**
 * 
 */
package gossip.view.chatview;

import java.awt.Dimension;

import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class FwaScrollBar extends JScrollBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7923275173228535079L;
	private final Dimension preferred;

	public FwaScrollBar(int orientation, BasicScrollBarUI ui, Dimension preferredSize) {
		super(orientation);
		this.preferred = preferredSize;
		setUI(ui);
	}

	public FwaScrollBar(BasicScrollBarUI ui) {
		this(VERTICAL, ui, new Dimension(20, 50));
		setUI(ui);
	}

	public FwaScrollBar() {
		super();
		this.preferred = new Dimension(20, 50);
		setUI(new FwaScrollBarUIFlat());
	}

	@Override
	public Dimension getPreferredSize() {
		return preferred;
	}

}