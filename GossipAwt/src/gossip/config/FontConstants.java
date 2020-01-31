package gossip.config;

import java.awt.Font;

import gossip.lib.panel.FontSupportUtil;

public class FontConstants {

	private static final int FONT_SIZE = 15;

	public static final Font KEYBOARD_SPECIAL_KEY_FONT = new Font(FontSupportUtil.getStandardFontFamily(), Font.PLAIN, FONT_SIZE);
	public static final Font INPUTPANEL_NUMPAD_INPUT_FONT = new Font(FontSupportUtil.getStandardFontFamily(), Font.PLAIN, 20);
	public static final Font INPUTPANEL_NAME_FONT = new Font(FontSupportUtil.getStandardFontFamily(), Font.PLAIN, 16);
	public static final Font ALERT_BOX_FONT = new Font(FontSupportUtil.getStandardFontFamily(), Font.PLAIN, FONT_SIZE);
	public static final Font KEYBOARD_PAD_FONT = new Font(FontSupportUtil.getStandardFontFamily(), Font.PLAIN, FONT_SIZE);
	public static final Font KEYBOARD_FONT = new Font(FontSupportUtil.getStandardFontFamily(), Font.BOLD, FONT_SIZE);
	public static final Font CHAT_LABEL_FONT = new Font(FontSupportUtil.getStandardFontFamily(), Font.BOLD, FONT_SIZE);
	public static final Font CHAT_MESSAGE_FONT = new Font(FontSupportUtil.getStandardFontFamily(), Font.BOLD, FONT_SIZE);
	public static final Font MAIN_HEADLINE_FONT = new Font(FontSupportUtil.getStandardFontFamily(), Font.BOLD, 14);

}
