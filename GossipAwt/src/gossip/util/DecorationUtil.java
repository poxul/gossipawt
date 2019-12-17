package gossip.util;

import gossip.enumelement.EnumElement;
import gossip.enumelement.EnumElementList;
import gossip.lib.panel.disposable.DisposableComboBox;

public class DecorationUtil {

	public static DisposableComboBox createComboBox(EnumElementList enumList, EnumElement selectedItem) {
		return createComboBox(enumList, selectedItem, true);
	}

	private static final int COMBO_BOX_WIDTH = 240;

	public static DisposableComboBox createComboBox(EnumElementList enumList, EnumElement selectedItem, boolean isToBottom) {
		DisposableComboBox comboBox = new DisposableComboBox();
		return comboBox;
	}
}
