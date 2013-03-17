package info.tak11.subnetting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.taksmind.subnet.Subnet;


/**
 * Classful Subnetting
 *
 * Graphical implementation of the Java Subnetting API 
 *
 * @author Lance Colton
 */

public class Subnetting {

    private Shell shell;
    private RowLayout mrl = new RowLayout();
    private GridData data = new GridData(GridData.FILL_BOTH);
    private GridData data2 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    
    private Group group1;
    private Group group2;
    private Group group3;
    private Group group4;
    private Group group5;
    private Group group6;
    private Group group7;
    
    private NetText ip;
    private NetText maskbits;
    private NetText submask;
    private NetText totalhost;
    private NetText totalsubnet;
    private NetText subnetbits;
    //readonly
    private NetText netaddress;
    private NetText broadcastaddr;
    private NetText range;
    
    private Subnet network = new Subnet();
    
    public Subnetting(final Display display) {

        shell = new Shell(display, SWT.DIALOG_TRIM);
        shell.setText("Classful Subnetting");

        initUI();
        
        center(shell);
        update("maskbits");
        shell.pack();
        
        shell.open();
        
        while (!shell.isDisposed()) {
          if (!display.readAndDispatch()) {
            display.sleep();
          }
        }
    }

    public void initUI() {
    	mrl.pack = false;
    	mrl.fill = true;
    	mrl.center = true;
    	mrl.type = SWT.VERTICAL;
    	shell.setLayout(mrl);
    	
        FocusListener selected = new FocusListener() {
			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				//do nothing
			}

			@Override
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				update(((NetText)e.getSource()).getNetName());
			}
        };
        
    	createGroups();
    	
    	// Group 1
    	
    	Label label1 = new Label(group1, SWT.None);
    	label1.setText("IP Address");
    	label1.setToolTipText("The IPv4 Address");
    	label1.setLayoutData(data2);
    	
    	ip = new NetText(group1, SWT.BORDER);
    	ip.setNetName("ip");
    	ip.setText("192.168.1.1");
    	ip.setToolTipText("Enter IP Address");
    	ip.setLayoutData(data);
    	ip.addFocusListener(selected);
    	
    	Label label2 = new Label(group2, SWT.None);
    	label2.setToolTipText("The masked bits ( ex. /24 is 24)");
    	label2.setText("Masked Bits");
    	
    	maskbits = new NetText(group2, SWT.BORDER);
    	maskbits.setNetName("maskbits");
    	maskbits.setToolTipText("Input Masked Bits");
    	maskbits.setText("24");
    	maskbits.addFocusListener(selected);
    	
    	// Group 2
    	
    	Label label3 = new Label(group3, SWT.None);
    	label3.setToolTipText("The Subnet Mask");
    	label3.setText("Subnet Mask");
    	
    	submask = new NetText(group3, SWT.BORDER);
    	submask.setNetName("submask");
    	submask.setToolTipText("Enter subnet mask");
    	submask.setLayoutData(data);
    	submask.addFocusListener(selected);
    	
    	// Group 3
    	
    	Label label4 = new Label(group4, SWT.None);
    	label4.setToolTipText("total amount of possible hosts(for usable -2)");
    	label4.setText("Total Hosts");
    	
    	totalhost = new NetText(group4, SWT.BORDER);
    	totalhost.setNetName("totalhost");
    	totalhost.setToolTipText("Input the number of required total hosts");
    	totalhost.addFocusListener(selected);
    	
    	Label label5 = new Label(group4, SWT.None);
    	label5.setToolTipText("The total amount of subnets.");
    	label5.setText("Total Subnets");
    	
    	totalsubnet = new NetText(group4, SWT.BORDER);
    	totalsubnet.setNetName("totalsubnet");
    	totalsubnet.setToolTipText("Enter total number of required subnets.");
    	totalsubnet.addFocusListener(selected);
    	
    	// Group 4
    	
    	Label label6 = new Label(group2, SWT.None);
    	label6.setToolTipText("The number of subnet bits");
    	label6.setText("Subnet Bits");
    	
    	subnetbits = new NetText(group2, SWT.BORDER);
    	subnetbits.setNetName("subnetbits");
    	subnetbits.setToolTipText("Enter number of required subnet bits.");
    	subnetbits.addFocusListener(selected);
    	
    	// Group 5
    	
    	Label label7 = new Label(group5, SWT.None);
    	label7.setToolTipText("The subnet address.");
    	label7.setText("Subnet Address");
    	
    	netaddress = new NetText(group5, SWT.READ_ONLY);
    	netaddress.setNetName("netaddress");
    	netaddress.setToolTipText("The subnet address.");
    	netaddress.setLayoutData(data);
    	
    	// Group 6
    	
    	Label label8 = new Label(group6, SWT.None);
    	label8.setToolTipText("The broadcast address.");
    	label8.setText("Broadcast Address");
    	
    	broadcastaddr = new NetText(group6, SWT.READ_ONLY);
    	broadcastaddr.setNetName("broadcastaddr");
    	broadcastaddr.setToolTipText("The broadcast address.");
    	broadcastaddr.setLayoutData(data);
    	
    	// Group 7
    	group7.setSize(200, 25);
    	Label label9 = new Label(group7, SWT.None);
    	label9.setToolTipText("The usable address range.");
    	label9.setText("Range");
    	
    	range = new NetText(group7, SWT.READ_ONLY);
    	range.setNetName("range");
    	range.setToolTipText("Usable address range.");
    	range.setSize(200, 25);
    	
    	range.setLayoutData(data);
    	
    	
    }
    
    public void center(Shell shell) {

        Rectangle bds = shell.getDisplay().getBounds();

        Point p = shell.getSize();

        int nLeft = (bds.width - p.x) / 2;
        int nTop = (bds.height - p.y) / 2;

        shell.setBounds(nLeft, nTop, p.x, p.y);
    }

    public void createGroups() {
    	group1 = new Group(shell, SWT.NONE);
    	group1.setLayout(new GridLayout(2, true));
    	
    	group2 = new Group(shell, SWT.NONE);
    	group2.setLayout(new GridLayout(4, true));
    	
    	group3 = new Group(shell, SWT.NONE);
    	group3.setLayout(new GridLayout(2, true));
    	
    	group4 = new Group(shell, SWT.NONE);
    	group4.setLayout(new GridLayout(4, true));
    	
    	group5 = new Group(shell, SWT.NONE);
    	group5.setLayout(new GridLayout(2, true));
    	
    	group6 = new Group(shell, SWT.NONE);
    	group6.setLayout(new GridLayout(2, true));
    	
    	group7 = new Group(shell, SWT.NONE);
    	group7.setLayout(new GridLayout(2, false));
    }
    
    /**
     * 
     * @param info the information besides the IP that we have.
     */
    public void update(String info) {
    	network.setIPAddress(this.ip.getText());
    	
    	boolean reset = false;
    	
    	if ( info.equals("maskbits") ) {
    		network.setMaskedBits(Integer.parseInt(maskbits.getText()));
    		reset = true;
    	} 
    	else if( info.equals("submask")) {
    		network.setSubnetMask(submask.getText());
    		reset = true;
    	}
    	else if( info.equals("totalhost")) {
    		network.setTotalHosts(Integer.parseInt(totalhost.getText()));
    		reset = true;
    	}
    	else if( info.equals("totalsubnet") ) {
    		network.setTotalSubnets(Integer.parseInt(totalsubnet.getText()));
    		reset = true;
    	}
    	else if( info.equals("subnetbits") ) {
    		network.setSubnetBits(Integer.parseInt(subnetbits.getText()));
    		reset = true;
    	} 

    	if( reset ) {
	    	maskbits.setText(Integer.toString(network.getMaskedBits()));
	    	submask.setText(network.getSubnetMask());
	    	totalhost.setText(Integer.toString(network.getTotalHosts()));
	    	totalsubnet.setText(Integer.toString(network.getTotalSubnets()));
	    	subnetbits.setText(Integer.toString(network.getSubnetBits()));
	    	//set read-only fields
	    	netaddress.setText(network.getSubnetAddress());
	    	broadcastaddr.setText(network.getBroadcastAddress());
	    	range.setText(network.getMinimumHostAddressRange() + "-" + network.getMaximumHostAddressRange()); 
    	}
    }
    
    public static void main(String[] args) {
        Display display = new Display();
        new Subnetting(display);
        display.dispose();
    }
}
