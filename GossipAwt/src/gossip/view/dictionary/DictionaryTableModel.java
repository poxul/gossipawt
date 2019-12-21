package gossip.view.dictionary;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import gossip.config.InputItemConstants;
import gossip.data.MyProfileId;
import gossip.data.ObservableClientProfile;
import gossip.data.model.MySimpleMap;
import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;
import gossip.manager.LanguageManager;

public class DictionaryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private MySimpleMap<MyProfileId, ObservableClientProfile> clients;

	/*
	 * selected, name , host, comment
	 * 
	 * 
	 */

	private List<ObservableClientProfile> profiles;

	public DictionaryTableModel(MySimpleMap<MyProfileId, ObservableClientProfile> clients) {
		this.clients = clients;
		clients.addModelChangeListener((source, origin, oldValue, newValue) -> updateTable());
	}

	private void updateTable() {
		ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("") {

			@Override
			public Boolean startJob() {
				profiles = new ArrayList<>(clients.getCollection());
				fireTableDataChanged();
				return true;
			}
		});
	}

	@Override
	public int getRowCount() {
		return clients.size();
	}

	@Override
	public int getColumnCount() {
		return InputItemConstants.CLIENT_TABEL_NAMES.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return LanguageManager.getLocaleText(InputItemConstants.CLIENT_TABEL_NAMES[columnIndex]);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0) {
			return Boolean.class;
		} else {
			return String.class;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ObservableClientProfile val = profiles.get(rowIndex);
		if (val != null) {
			switch (columnIndex) {
			case 0:
				return val.selectProperty().getValue();
			case 1:
				return val.getMyProfile().getName();
			case 2:
				return val.getMyProfile().getHostName();
			case 3:
				return val.getMyProfile().getComment();
			default:
				return null;
			}
		}
		return null;
	}

	private ObservableClientProfile getProfile(int idx) {
		if (idx >= 0 && profiles.size() > idx) {
			return profiles.get(idx);
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 0 && aValue instanceof Boolean) {
			ObservableClientProfile val = getProfile(rowIndex);
			if (val != null) {
				val.selectProperty().setValue((Boolean) aValue);
			}
		}
	}

}
