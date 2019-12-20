package gossip.config;

import gossip.view.keyboard.input.InputItemId;

public class InputItemConstants {

	public static final InputItemId ITEM_DEFAULT = new InputItemId("item.default");
	public static final InputItemId ITEM_OK = new InputItemId("item.ok");
	public static final InputItemId ITEM_KEYBOARD_LOGIN = new InputItemId("item.login");
	public static final InputItemId ITEM_KEYBOARD_COMMIT = new InputItemId("item.commit");
	public static final InputItemId ITEM_KEYBOARD_SAVE = new InputItemId("item.save");
	public static final InputItemId ITEM_OFF = new InputItemId("item.off");
	public static final InputItemId ITEM_SERVER_CONNECTION = new InputItemId("headline.server_connection");
	public static final InputItemId ITEM_CLIENTS = new InputItemId("headline.clients");
	public static final InputItemId ITEM_VERSION = new InputItemId("headline.version");
	
	public static final InputItemId ITEM_EMOTE_YES = new InputItemId("chat.emoticon.button_yes");
	public static final InputItemId ITEM_EMOTE_NO = new InputItemId("chat.emoticon.button_no");
	public static final InputItemId ITEM_EMOTE_OK = new InputItemId("chat.emoticon.button_ok");
	public static final InputItemId ITEM_EMOTE_KO = new InputItemId("chat.emoticon.button_ko");
	public static final InputItemId ITEM_DICTIONARY = new InputItemId("chat.function.dictionary");
	public static final InputItemId ITEM_KEYBOARD= new InputItemId("chat.function.keyboard");

	public static InputItemId createKeyId(String k) {
		return new InputItemId("itemid." + k);
	}

}
