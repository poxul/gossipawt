package gossip.lib.panel;

import java.awt.Insets;
import java.awt.Paint;

import gossip.lib.panel.disposable.Disposable;
import gossip.lib.panel.txt.StringFormatterUtil;

public interface MyTextFieldInterface extends Disposable {

	public abstract String getText();

	public abstract void setMargin(Insets insets);

	public abstract void setPaint(Paint paint);

	public abstract void setText(String infoText);

	public abstract void setTextAlignment(StringFormatterUtil.Alignment alignment);

}