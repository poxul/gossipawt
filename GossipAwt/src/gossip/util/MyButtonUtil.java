package gossip.util;

import java.util.Set;

import gossip.lib.panel.button.ButtonFaceListener;
import gossip.lib.panel.button.DefaultButtonFace;
import gossip.lib.panel.button.MyButton;
import gossip.view.keyboard.input.InputItemId;

public class MyButtonUtil {

	public static MyButton createSimpleButton(InputItemId id, String imageName, ButtonFaceListener listener) {
		MyButton button = new MyButton();
		DefaultButtonFace buttonFace = DefaultButtonFace.createButtonFace("simple");
		buttonFace.addButtonFaceListener(listener);
		buttonFace.setInputItemId(id);
		buttonFace.setOverlay(ImageUtil.getImage(imageName));
		button.addFace(buttonFace);
		return button;
	}

	public static void setSingleButtonFaceItem(MyButton button, InputItemId id) {
		Set<String> set = button.getButtonFaceKeySet();
		for (String string : set) {
			button.getButtonFace(string).setInputItemId(id);
		}
	}

}
