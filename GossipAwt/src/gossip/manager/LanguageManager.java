package gossip.manager;

import java.util.Locale;

import gossip.keyboard.KeyBoardDefinition;
import gossip.keyboard.input.InputItemChangeListener;
import gossip.keyboard.input.InputItemId;
import gossip.lib.util.StringUtil;
import gossip.util.KeyBoardUtil;
import gossip.util.KeyBoardUtil.KeyBoardType;

public class LanguageManager {

	private static class LanguageManagerImpl {

		private Locale locale = Locale.GERMAN;

		public Locale getLocale() {
			return locale;
		}

		public void setLocale(Locale locale) {
			this.locale = locale;
		}

		private KeyBoardType type = KeyBoardType.GENERAL;

		public KeyBoardType getType() {
			return type;
		}

		public void setType(KeyBoardType type) {
			this.type = type;
		}

		public String getLocaleText(InputItemId itemId) {
			return itemId == null ? "" : itemId.toString();
		}

	}

	private static LanguageManagerImpl instance = create();

	private static LanguageManagerImpl get() {
		return instance;
	}

	private static LanguageManagerImpl create() {
		return new LanguageManagerImpl();
	}

	private LanguageManager() {
		// NOP
	}

	public static Locale getLocale() {
		return get().getLocale();
	}

	public static KeyBoardType getKeyBoardType() {
		return get().getType();
	}

	public static KeyBoardDefinition getNumPadDefinition(String name) {
		get().setLocale(Locale.forLanguageTag(name));
		return KeyBoardUtil.getKeyBoardDefinition(getKeyBoardType(), getLocale());
	}

	public static KeyBoardDefinition getKeyBoardDefinition(KeyBoardType type) {
		get().setType(type);
		return KeyBoardUtil.getKeyBoardDefinition(getKeyBoardType(), getLocale());
	}

	public static String getLocaleText(InputItemId itemId) {
		return get().getLocaleText(itemId);
	}

	public static void setLanguage(Locale locale) {
		get().setLocale(locale);
	}

	public static void removeInputElementChangeListener(InputItemChangeListener listener, InputItemId id) {
		// TODO Auto-generated method stub

	}

	public static void addInputElementChangeListener(InputItemChangeListener listener, InputItemId id) {
		// TODO Auto-generated method stub

	}

	public static InputItemId createKeyId(String k) {
		return new InputItemId("itemid." + k);
	}

	public static boolean isUnresolvedString(String str) {
		return StringUtil.isNullOrEmpty(str);
	}

}
