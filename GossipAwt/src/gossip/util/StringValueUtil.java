package gossip.util;

import java.rmi.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gossip.config.InputItemConstants;
import gossip.data.AwtBroker;
import gossip.data.MyProfile;
import gossip.data.MyProfileId;
import gossip.data.ObservableClientProfile;
import gossip.data.device.DeviceData;
import gossip.lib.util.StringUtil;
import gossip.manager.LanguageManager;
import gossip.protobuf.HeaderData.DeviceId;
import gossip.run.ConfigurationService;

public class StringValueUtil {

	private StringValueUtil() {
	}

	public static String strValueOfDeviceId(DeviceId id) {
		String result = null;
		if (id != null) {
			result = id.toString();
			result = result.substring(result.length() - 4);
		} else {
			result = "$$$$";
		}
		return result;
	}

	public static String strValueOfIp(String ip) {
		return ip == null ? "0.0.0.0" : ip;
	}

	private static final String TLD_INTERN_DE = "\\.intern\\..*\\.de";
	private static final String TLD_INTERN_COM = "\\.intern\\..*\\.com";

	private static final String[] KNOWN_TDLS = {
			TLD_INTERN_DE,
			TLD_INTERN_COM
	};

	public static String strValueOfHost(String host) throws UnknownHostException {
		if (!StringUtil.isNullOrEmpty(host)) {
			host = host.trim();
			for (String tdl : KNOWN_TDLS) {
				if (host.endsWith(tdl) && host.length() > tdl.length()) {
					host = host.substring(0, host.length() - tdl.length());
				}
			}
		}
		if (StringUtil.isNullOrEmpty(host)) {
			throw new UnknownHostException("unknown host string");
		}

		return host;
	}

	public static String strValueOfPropertyKey(String key) {
		String txt = LanguageUtil.getPropertyName(key);
		return key == null ? "#" + key : txt;
	}

	public static String strValueOfUser(String user) {
		return user == null ? "Anonymous" : user;
	}

	public static String stripHostName(String host) {
		if (!StringUtil.isNullOrEmpty(host) && !Character.isDigit(host.charAt(0))) {
			String[] ipc = host.split("\\.");
			if (ipc != null && ipc.length > 1) {
				host = ipc[0];
			}
		}
		return host;
	}

	private static final long TURN_LIMIT = 4000;

	public static String stringValueOfMillis(final long t) {
		if (t > TURN_LIMIT * 60 * 60 * 1000l)
			return String.valueOf(t / (60 * 60 * 1000l)) + "h";
		else if (t > TURN_LIMIT * 60 * 1000l)
			return String.valueOf(t / (60 * 1000l)) + "min";
		else if (t > TURN_LIMIT)
			return String.valueOf(t / 1000l) + "s";
		else
			return String.valueOf(t) + "ms";
	}

	public static String buildClientsString(int num) {
		StringBuilder sb = new StringBuilder();
		sb.append(LanguageManager.getLocaleText(InputItemConstants.ITEM_CLIENTS));
		sb.append(": ");
		sb.append(num);
		return sb.toString();
	}

	public static String buildVersionString() {
		StringBuilder sb = new StringBuilder();
		sb.append(LanguageManager.getLocaleText(InputItemConstants.ITEM_VERSION));
		sb.append(" ");
		DeviceData dd = ConfigurationService.getDeviceData();
		sb.append(dd.getVersionString());
		return sb.toString();
	}

	public static String strValueOfProfileId(MyProfileId id) {
		String result = null;
		if (id != null) {
			result = id.toString();
			result = result.substring(result.length() - 4);
		} else {
			result = "$$$$";
		}
		return result;
	}

	public static String getName(MyProfileId id) {
		MyProfile p = AwtBroker.get().getController().getMyProfile(id);
		return p == null ? "???" : p.getName() + "@" + p.getHostName();
	}

	private static final String DATE_TIME_FORMAT_WITH_SECONDS = "yyyy-MM-dd HH:mm:ss";

	public static String buildTimeStirng(Date date) {
		SimpleDateFormat dateFormater = new SimpleDateFormat(DATE_TIME_FORMAT_WITH_SECONDS);
		return dateFormater.format(date);
	}

	public static String buildProfileString(ObservableClientProfile p) {
		return p == null ? "Y" : p.getMyProfile().getHostName();
	}

}
