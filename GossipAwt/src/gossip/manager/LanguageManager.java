package gossip.manager;

import java.util.Locale;

import gossip.data.LanguageMap;
import gossip.lib.util.StringUtil;
import gossip.util.FileLoaderUtil;
import gossip.util.KeyBoardUtil;
import gossip.util.KeyBoardUtil.KeyBoardType;
import gossip.view.keyboard.KeyBoardDefinition;
import gossip.view.keyboard.input.InputItemChangeListener;
import gossip.view.keyboard.input.InputItemId;
import gossip.util.ResourcesUtil;

public class LanguageManager implements MyManager {

	private static class LanguageManagerImpl {

		private Locale locale = Locale.GERMAN;

		private LanguageMap languageMap;

		private void init() {
			String languageFileName = ResourcesUtil.getLanguageFileName(getLocale());
			languageMap = FileLoaderUtil.readLanguageMap(languageFileName);
		}

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
			if (languageMap == null) {
				init(); // TODO
			}
			return itemId == null ? "" : languageMap.get(itemId);
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

	public static boolean isUnresolvedString(String str) {
		return StringUtil.isNullOrEmpty(str);
	}

	@Override
	public void postInit() {
		// TODO Auto-generated method stub
		instance.init();
	}

	@Override
	public String getManagerId() {
		// TODO Auto-generated method stub
		return null;
	}

}
