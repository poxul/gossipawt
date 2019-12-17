package gossip.event;

import java.util.Locale;

public class LanguageChangedDataEvent implements UserEvent {
	private final Locale locale;

	public LanguageChangedDataEvent(Locale locale) {
		this.locale = locale;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LanguageChangedDataEvent))
			return false;
		LanguageChangedDataEvent other = (LanguageChangedDataEvent) obj;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		return true;
	}

	public Locale getLocale() {
		return locale;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LanguageChangedDataEvent [locale=");
		builder.append(locale);
		builder.append(']');
		return builder.toString();
	}

}
