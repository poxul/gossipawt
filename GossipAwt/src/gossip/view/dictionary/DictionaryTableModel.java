package gossip.view.dictionary;

import javax.swing.table.AbstractTableModel;

import gossip.config.InputItemConstants;
import gossip.data.MyProfileId;
import gossip.data.ObservableClientProfile;
import gossip.data.model.MySimpleMap;
import gossip.manager.LanguageManager;

public class DictionaryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private MySimpleMap<MyProfileId, ObservableClientProfile> clients;

	/*
	 * selected, name , host, comment
	 * 
	 * 
	 */

	public DictionaryTableModel(MySimpleMap<MyProfileId, ObservableClientProfile> clients) {
		this.clients = clients;
		clients.addModelChangeListener((source, origin, oldValue, newValue) -> fireTableDataChanged());
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}

}
