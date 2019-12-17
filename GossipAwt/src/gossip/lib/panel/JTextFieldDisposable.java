package gossip.lib.panel;

import javax.swing.JTextField;

import gossip.lib.job.ServiceJobAWTDefault;
import gossip.lib.job.ServiceJobAWTUtil;
import gossip.lib.panel.disposable.ContainerDisposer;
import gossip.lib.panel.disposable.Disposable;
import gossip.lib.util.StringUtil;

public class JTextFieldDisposable extends JTextField implements Disposable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ContainerDisposer disposer = new ContainerDisposer(this);

	@Override
	public void dispose() {
		disposer.dispose();
	}

	@Override
	public void setText(String t) {
		if (!StringUtil.compare(t, super.getText())) {
			super.setText(t);
			if (!hasFocus()) {
				requestTextInputFocus();
			}
		}
	}

	private void requestTextInputFocus() {
		ServiceJobAWTUtil.invokeAWT(new ServiceJobAWTDefault("request input focus") {

			@Override
			public Boolean startJob() {
				return requestFocusInWindow();
			}
		});
	}

}
