package gossip.config;

import java.awt.Dimension;

public class DimensionConstants {

	public static final int INPUT_LABEL_HEIGHT = 60;
	public static final int INPUT_LABEL_WIDTH = 200;
	public static final int BUTTON_HEIGHT_RECT_KEYBOARD = 30;
	public static final int BUTTON_FACE_SPACE_WIDTH = 200;
	public static final int BUTTON_FACE_SPACE_HEIGHT = BUTTON_HEIGHT_RECT_KEYBOARD;

	public static final Dimension PREFERRED_SIZE_BACKSPACE_PANEL = new Dimension(80, BUTTON_HEIGHT_RECT_KEYBOARD);
	public static final Dimension DEFAULT_KEY_DIMENSION = new Dimension(50, BUTTON_HEIGHT_RECT_KEYBOARD);
	public static final Dimension BUTTON_FACE_DIMENSION_CURSOR = DEFAULT_KEY_DIMENSION;
	public static final Dimension BUTTON_FACE_DIMENSION_SHIFT = DEFAULT_KEY_DIMENSION;
	public static final Dimension BUTTON_FACE_DIMENSION_BACKSPACE = new Dimension(150, BUTTON_HEIGHT_RECT_KEYBOARD);

}
