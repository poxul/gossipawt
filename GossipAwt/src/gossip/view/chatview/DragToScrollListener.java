package gossip.view.chatview;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JScrollBar;

import gossip.lib.panel.disposable.JScrollPaneDisposable;


public class DragToScrollListener implements MouseListener, MouseMotionListener {

	private class Animator implements ActionListener {
		/**
		 *
		 * Performs the animation through the setting of the JScrollBar values.
		 *
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			// damp the scrolling intensity
			pixelsPerMSX -= pixelsPerMSX * damping;
			pixelsPerMSY -= pixelsPerMSY * damping;
			// check to see if timer should stop.
			if ((Math.abs(pixelsPerMSX) < 0.01) && (Math.abs(pixelsPerMSY) < 0.01)) {
				animationTimer.stop();
				return;
			}

			// calculate new X value
			int nValX = getHorizontalScrollBar().getValue() + (int) (pixelsPerMSX * scrollingIntensity);
			int nValY = getVerticalScrollBar().getValue() + (int) (pixelsPerMSY * scrollingIntensity);

			// Deal with out of scroll bounds
			if (nValX <= 0) {
				nValX = 0;
			} else if (nValX >= getHorizontalScrollBar().getMaximum()) {
				nValX = getHorizontalScrollBar().getMaximum();
			}

			if (nValY <= 0) {
				nValY = 0;
				pageUp();
				animationTimer.stop();
			} else if (nValY >= getVerticalScrollBar().getMaximum()) {
				nValY = getVerticalScrollBar().getMaximum();
				pageDown();
				animationTimer.stop();
			}

			// Set new values
			if ((scrollBarMask & HORIZONTAL_SCROLL_BAR) != 0) {
				getHorizontalScrollBar().setValue(nValX);
			}

			if ((scrollBarMask & VERTICAL_SCROLL_BAR) != 0) {
				getVerticalScrollBar().setValue(nValY);
			}

			// Check again to see if timer should stop
			if (((nValX == 0) || (nValX == getHorizontalScrollBar().getMaximum())) && (Math.abs(pixelsPerMSY) < 1)) {
				animationTimer.stop();
			}

			if (((nValY == 0) || (nValY == getVerticalScrollBar().getMaximum())) && (Math.abs(pixelsPerMSX) < 1)) {
				animationTimer.stop();
			}

		}

	}

	private static final int HORIZONTAL_SCROLL_BAR = 1;
	private static final int VERTICAL_SCROLL_BAR = 2;

	// defines the intensite of automatic scrolling.
	private int scrollingIntensity = 5;

	// value used to descrease scrolling intensity during animation
	private double damping = 0.05;

	// indicates the number of milliseconds between animation updates.
	private int animationSpeed = 20;

	// Animation timer
	private javax.swing.Timer animationTimer = null;
	// the time of the last mouse drag event
	private long lastDragTime = 0;
	private Point lastDragPoint = null;
	// animation rates
	private double pixelsPerMSX;
	private double pixelsPerMSY;

	// flag which defines the draggable scroll directions
	private int scrollBarMask = HORIZONTAL_SCROLL_BAR | VERTICAL_SCROLL_BAR;

	// the draggable component
	private final Component draggableComponent;

	// the JScrollPane containing the component - programmatically determined.
	private JScrollPaneDisposable scroller = null;

	// List of drag speeds used to calculate animation speed
	// Uses the Point2D class to represent speeds rather than locations

	// the default cursor
	private Cursor defaultCursor;

	private final java.util.List<Point2D.Double> dragSpeeds = new ArrayList<Point2D.Double>();

	public DragToScrollListener(final Component c) {
		draggableComponent = c;
		defaultCursor = draggableComponent.getCursor();
		draggableComponent.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent arg0) {
				setScroller();
				defaultCursor = draggableComponent.getCursor();
			}
		});
		setScroller();
	}

	private JScrollBar getHorizontalScrollBar() {
		return scroller.getHorizontalScrollBar();
	}

	private static JScrollPaneDisposable getParentScroller(final Component c) {
		Container parent = c.getParent();
		if (parent != null) {
			if (parent instanceof JScrollPaneDisposable) {
				return (JScrollPaneDisposable) parent;
			} else {
				return getParentScroller(parent);
			}
		}
		return null;
	}

	private JScrollBar getVerticalScrollBar() {
		return scroller.getVerticalScrollBar();
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		// NOP
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		if (scroller == null) {
			return;
		}
		Point p = e.getPoint();
		int diffx = 0;
		int diffy = 0;
		if (lastDragPoint != null) {
			diffx = p.x - lastDragPoint.x;
			diffy = p.y - lastDragPoint.y;
		}
		lastDragPoint = e.getPoint();
		// scroll the x axis
		if ((scrollBarMask & HORIZONTAL_SCROLL_BAR) != 0) {
			getHorizontalScrollBar().setValue(getHorizontalScrollBar().getValue() - diffx);
		}

		// the Scrolling affects mouse locations - offset the last drag point to compensate
		lastDragPoint.x = lastDragPoint.x - diffx;
		// scroll the y axis
		if ((scrollBarMask & VERTICAL_SCROLL_BAR) != 0) {
			getVerticalScrollBar().setValue(getVerticalScrollBar().getValue() - diffy);
		}

		// the Scrolling affects mouse locations - offset the last drag point to compensate
		lastDragPoint.y = lastDragPoint.y - diffy;
		// add a drag speed
		dragSpeeds.add(new Point2D.Double((e.getPoint().x - lastDragPoint.x), (e.getPoint().y - lastDragPoint.y)));
		lastDragTime = System.currentTimeMillis();
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		// NOP
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		// NOP
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		// NOP
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		if ((animationTimer != null) && animationTimer.isRunning()) {
			animationTimer.stop();
		}
		draggableComponent.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		dragSpeeds.clear();
		lastDragPoint = e.getPoint();
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		draggableComponent.setCursor(defaultCursor);
		if (scroller == null) {
			return;
		}
		long durationSinceLastDrag = System.currentTimeMillis() - lastDragTime;
		if (durationSinceLastDrag > 20) {
			return;
		}

		// get average speed for last few drags
		pixelsPerMSX = 0;
		pixelsPerMSY = 0;

		int j = 0;
		for (int i = dragSpeeds.size() - 1; (i >= 0) && (i > (dragSpeeds.size() - 6)); i--, j++) {
			pixelsPerMSX += dragSpeeds.get(i).x;
			pixelsPerMSY += dragSpeeds.get(i).y;
		}

		pixelsPerMSX /= -(double) j;
		pixelsPerMSY /= -(double) j;

		// start the timer
		if ((Math.abs(pixelsPerMSX) > 0) || (Math.abs(pixelsPerMSY) > 0)) {
			animationTimer = new javax.swing.Timer(animationSpeed, new Animator());
			animationTimer.start();
		}

	}

	/**
	 *
	 * Sets how frequently the animation will occur in milliseconds. Default
	 *
	 * value is 30 milliseconds. 60+ will get a bit flickery.
	 *
	 * @param timing
	 *            The timing, in milliseconds.
	 *
	 */
	public void setAnimationTiming(final int timing) {
		animationSpeed = timing;
	}

	/**
	 *
	 * Sets the animation damping.
	 *
	 * @param damping
	 *            The new value
	 *
	 */
	public void setDamping(final double damping) {
		this.damping = damping;
	}

	/**
	 *
	 *
	 *
	 * Sets the Draggable elements - the Horizontal or Vertical Direction. One
	 *
	 * can use a bitmasked 'or' (HORIZONTAL_SCROLL_BAR | VERTICAL_SCROLL_BAR ).
	 *
	 * @param mask
	 *            One of HORIZONTAL_SCROLL_BAR, VERTICAL_SCROLL_BAR, or HORIZONTAL_SCROLL_BAR | VERTICAL_SCROLL_BAR
	 *
	 */
	public void setDraggableElements(final int mask) {
		scrollBarMask = mask;
	}

	private void setScroller() {
		JScrollPaneDisposable c = getParentScroller(draggableComponent);
		if (c != null) {
			scroller = (JScrollPaneDisposable) c;
		} else {
			scroller = null;
		}
	}

	public void home() {
		if (scroller != null) {
			scroller.getVerticalScrollBar().setValue(0);
		}
	}

	public void pageUp() {
		if (scroller != null) {
			scroller.pageUp();
		}
	}

	public void pageDown() {
		if (scroller != null) {
			scroller.pageDown();
		}
	}

	/**
	 *
	 * Sets the scrolling intensity - the default value being 5. Note, that this has an
	 *
	 * inverse relationship to intensity (1 has the biggest difference, higher numbers having
	 *
	 * less impact).
	 *
	 * @param intensity
	 *            The new intensity value (Note the inverse relationship).
	 *
	 */
	public void setScrollingIntensity(final int intensity) {
		scrollingIntensity = intensity;
	}

}
