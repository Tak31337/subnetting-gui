package info.tak11.subnetting;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class NetText extends Text {
	
	private String name;
	
	public NetText(Composite parent, int style) {
		super(parent, style);
	}
	
	public void setNetName(String newName) {
		name = newName;
	}
	
	public String getNetName() {
		return name;
	}
	
	protected void checkSubclass() {
	// Disable the check that prevents subclassing of SWT components
	} 

}
