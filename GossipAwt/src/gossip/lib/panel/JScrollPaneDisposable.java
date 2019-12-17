package gossip.lib.panel;

import java.awt.Component;

import javax.swing.JScrollPane;

import gossip.lib.panel.disposable.ContainerDisposer;
import gossip.lib.panel.disposable.Disposable;
import gossip.lib.panel.disposable.JPanelDisposable;

public class JScrollPaneDisposable extends JScrollPane implements Disposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param comp
	 */
	public JScrollPaneDisposable(JPanelDisposable comp) {
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
