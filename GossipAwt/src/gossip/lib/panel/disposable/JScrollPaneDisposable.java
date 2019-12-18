package gossip.lib.panel.disposable;

import java.awt.Component;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class JScrollPaneDisposable extends JScrollPane implements Disposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param comp
	 */
	public JScrollPaneDisposable(JTable comp) {
		super(comp);
	}

	/**
	 * 
	 * @param comp
	 */
	public JScrollPaneDisposable(JPanelDisposable comp) {
		super(comp);
	}

	/**
	 * 
	 * @param comp
	 */
	public JScrollPaneDisposable(JEditorPane comp) {
		super(comp);
	}

	/**
	 * 
	 * @param statisticList
	 */
	public JScrollPaneDisposable(Component statisticList) {
		super(statisticList);
	}

	/*
	 * 
	 */
	private final ContainerDisposer disposer = new ContainerDisposer(this);

	/**
	 * 
	 */
	@Override
	public void dispose() {
		disposer.dispose();
	}

	/**
	 * 
	 */
	public void pageUp() {
		// NOP
	}

	/**
	 * 
	 */
	public void pageDown() {
		// NOP
	}

}
