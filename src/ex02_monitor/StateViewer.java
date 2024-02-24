package ex02_monitor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.FlowLayout;

public class StateViewer extends JFrame  {

	private JPanel contentPane;
	
	private JLabel labelWaitingSouthbound;
	private JLabel labelWaitingNorthbound;
	private JLabel labelGoingSouth;
	private JLabel labelGoingNorth;

	private final JLabel labelSouthArrow = new JLabel("New label");
	private JLabel labelNorthArrow;
	private final JLabel labelAllowedNorthbound = new JLabel("-");
	private final JLabel labelAllowedSouthbound = new JLabel("-");
	private JEditorPane editorPane;
	private JScrollPane scrollPane_1;
	
	private String text="";
	private JButton heavyButton;
	private JButton unbalancedButton;
	private JButton easyButton;
	private JCheckBox fastCheck;
	private final JPanel innerStartPanelUP = new JPanel();
	private final Component horizontalStrut = Box.createHorizontalStrut(15);
	private final Component horizontalStrut_1 = Box.createHorizontalStrut(15);
	private final JButton limitButton = new JButton("4 Cars");
	private final Component horizontalStrut_1_1 = Box.createHorizontalStrut(15);
	private final Component horizontalStrut_1_1_1 = Box.createHorizontalStrut(15);
	private final JButton onlyNorthButton = new JButton("Only Northbound");
	private final Component horizontalStrut_1_1_1_1 = Box.createHorizontalStrut(15);
	private final JButton onlySouthButton = new JButton("Only Southbound");
	private JComboBox allowedCombo;
	private JLabel labelCombo;
	
	// Text operations
	public void appendText (String s) {
		if (text.length()>10000) text="";
		this.text = this.text+"<br><b>"+s+"</b>";
		try {
			EventQueue.invokeAndWait(()->{
				this.editorPane.setText(this.text);
			});
		}
		catch (InterruptedException | InvocationTargetException ie) {}
	}
	
	public void appendRedText(String s) {
		this.text = this.text+"<br><b><span style=\"color: red;\">"+s+"</span></b>";
		try {
			EventQueue.invokeAndWait(()->{
				this.editorPane.setText(this.text);
				JOptionPane.showMessageDialog(this,
					    s,
					    "ANOMALOUS SITUATIONS",
					    JOptionPane.ERROR_MESSAGE);
			});
		}
		catch (InterruptedException | InvocationTargetException ie) {}
	}
	
	//-------- 'SET' operations
	public void setWaitingSouthbound(int val)  {
		
		String txt = val+"" ;
		try {
			EventQueue.invokeAndWait(()->{
				this.labelWaitingSouthbound.setText(txt);
			});
		}
		catch (InterruptedException | InvocationTargetException ie) {}
	}
	
	public void setWaitingNorthbound(int val)  {
		
		String txt = val+"" ;
		try {
			EventQueue.invokeAndWait(()->{
				this.labelWaitingNorthbound.setText(txt);
			});
		}
		catch (InterruptedException | InvocationTargetException ie) {}
	}
	
	public void setGoingSouth(int val)  {
		
		String txt = val+"" ;
		try {
			EventQueue.invokeAndWait(()->{
				this.labelGoingSouth.setText(txt);
			});
		}
		catch (InterruptedException | InvocationTargetException ie) {}
	}
	
	public void setGoingNorth(int val)  {
		
		String txt = val+"" ;
		try {
			EventQueue.invokeAndWait(()->{
				this.labelGoingNorth.setText(txt);
			});
		}
		catch (InterruptedException | InvocationTargetException ie) {ie.printStackTrace();}
	}
	
	public void setAllowedNorthbound(int val)  {
		String txt;
		if (val<0) txt = "-";
		else txt = val+"" ;
		try {
			EventQueue.invokeAndWait(()->{
				this.labelAllowedNorthbound.setText(txt);
			});
		}
		catch (InterruptedException | InvocationTargetException ie) {ie.printStackTrace();}
	}
	
	public void setAllowedSouthbound(int val)  {
		String txt;
		if (val<0) txt = "-";
		else txt = val+"" ;
		try {
			EventQueue.invokeAndWait(()->{
				this.labelAllowedSouthbound.setText(txt);
			});
		}
		catch (InterruptedException | InvocationTargetException ie) {ie.printStackTrace();}
	}
	
	/**
	 * Start the viewer
	 */
	public static void startViewer(String[] args) {
		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					try {
						StateViewer frame = new StateViewer();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} 
		catch (InterruptedException | InvocationTargetException ie) {ie.printStackTrace();}
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public StateViewer() throws IOException {
		setTitle("Kerberos State Viewer by ESN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 863, 614);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		labelWaitingSouthbound = new JLabel("0");
		labelWaitingSouthbound.setForeground(new Color(255, 0, 0));
		labelWaitingSouthbound.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cars waiting to go SOUTH", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		labelWaitingSouthbound.setHorizontalAlignment(SwingConstants.CENTER);
		labelWaitingSouthbound.setBounds(93, 192, 261, 73);
		labelWaitingSouthbound.setFont(new Font("Tahoma", Font.PLAIN, 60));
		contentPane.add(labelWaitingSouthbound);
		
		labelWaitingNorthbound = new JLabel("0");
		labelWaitingNorthbound.setForeground(new Color(0, 128, 255));
		labelWaitingNorthbound.setHorizontalAlignment(SwingConstants.CENTER);
		labelWaitingNorthbound.setFont(new Font("Tahoma", Font.PLAIN, 60));
		labelWaitingNorthbound.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cars waiting to go NORTH", TitledBorder.TRAILING, TitledBorder.BOTTOM, null, new Color(0, 128, 255)));
		labelWaitingNorthbound.setBounds(93, 467, 261, 73);
		contentPane.add(labelWaitingNorthbound);
		
		labelGoingSouth = new JLabel("0");
		labelGoingSouth.setForeground(new Color(255, 0, 0));
		labelGoingSouth.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Going SOUTH", TitledBorder.LEFT, TitledBorder.BOTTOM, null, new Color(0, 0, 0)));
		labelGoingSouth.setHorizontalAlignment(SwingConstants.CENTER);
		labelGoingSouth.setFont(new Font("Tahoma", Font.PLAIN, 60));
		labelGoingSouth.setBounds(103, 276, 109, 180);
		contentPane.add(labelGoingSouth);
		
		labelGoingNorth = new JLabel("0");
		labelGoingNorth.setForeground(new Color(0, 128, 255));
		labelGoingNorth.setHorizontalAlignment(SwingConstants.CENTER);
		labelGoingNorth.setFont(new Font("Tahoma", Font.PLAIN, 60));
		labelGoingNorth.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Going NORTH", TitledBorder.RIGHT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		labelGoingNorth.setBounds(234, 276, 109, 180);
		contentPane.add(labelGoingNorth);
		
		this.labelSouthArrow.setVerticalAlignment(SwingConstants.TOP);
		this.labelSouthArrow.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		this.labelSouthArrow.setBounds(26, 192, 54, 277);
		File file = new File("resources/downArrow.png");
		ImageIcon original = new ImageIcon(ImageIO.read(file));
		Image image = original.getImage();
		image = image.getScaledInstance(labelSouthArrow.getWidth(),labelSouthArrow.getHeight(), Image.SCALE_FAST);
		original = new ImageIcon(image);
		this.labelSouthArrow.setIcon(original);
		this.contentPane.add(this.labelSouthArrow);
		
		this.labelNorthArrow = new JLabel("New label");
		this.labelNorthArrow.setBounds(371, 276, 46, 252);
		file = new File("resources/upArrow.png");
		original = new ImageIcon(ImageIO.read(file));
		image = original.getImage();
		image = image.getScaledInstance(labelNorthArrow.getWidth(),labelNorthArrow.getHeight(), Image.SCALE_FAST);
		original = new ImageIcon(image);
		this.labelNorthArrow.setIcon(original);
		this.contentPane.add(this.labelNorthArrow);
		this.labelAllowedNorthbound.setForeground(new Color(0, 128, 255));
		this.labelAllowedNorthbound.setHorizontalAlignment(SwingConstants.CENTER);
		this.labelAllowedNorthbound.setFont(new Font("Tahoma", Font.BOLD, 22));
		this.labelAllowedNorthbound.setBorder(new TitledBorder(null, "since", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 255)));
		this.labelAllowedNorthbound.setBounds(364, 192, 59, 42);
		
		this.contentPane.add(this.labelAllowedNorthbound);
		this.labelAllowedSouthbound.setForeground(new Color(255, 0, 0));
		this.labelAllowedSouthbound.setHorizontalAlignment(SwingConstants.CENTER);
		this.labelAllowedSouthbound.setFont(new Font("Tahoma", Font.BOLD, 22));
		this.labelAllowedSouthbound.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "since", TitledBorder.TRAILING, TitledBorder.BOTTOM, null, new Color(255, 0, 0)));
		this.labelAllowedSouthbound.setBounds(24, 499, 59, 42);
		
		this.contentPane.add(this.labelAllowedSouthbound);
		
		this.scrollPane_1 = new JScrollPane();
		this.scrollPane_1.setBorder(new TitledBorder(null, "Output Messages", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.scrollPane_1.setBounds(455, 192, 367, 348);
		this.contentPane.add(this.scrollPane_1);
		
		this.editorPane = new JEditorPane();
		this.editorPane.setContentType("text/html");
		this.scrollPane_1.setViewportView(this.editorPane);
		
		JPanel startPanel = new JPanel();
		startPanel.setBorder(new TitledBorder(null, "Start", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		startPanel.setBounds(22, 22, 780, 138);
		contentPane.add(startPanel);
		startPanel.setLayout(null);
		this.innerStartPanelUP.setBounds(17, 24, 741, 43);
		
		startPanel.add(this.innerStartPanelUP);
		this.innerStartPanelUP.setLayout(new BoxLayout(this.innerStartPanelUP, BoxLayout.X_AXIS));
		
		heavyButton = new JButton("Heavy Traffic");
		this.innerStartPanelUP.add(this.heavyButton);
		
		this.innerStartPanelUP.add(this.horizontalStrut);
		
		unbalancedButton = new JButton("Unbalanced");
		this.innerStartPanelUP.add(this.unbalancedButton);
		
		this.innerStartPanelUP.add(this.horizontalStrut_1);
		
		easyButton = new JButton("3 cars");
		this.innerStartPanelUP.add(this.easyButton);
		
		this.innerStartPanelUP.add(this.horizontalStrut_1_1);

		this.innerStartPanelUP.add(this.limitButton);
		
		this.innerStartPanelUP.add(this.horizontalStrut_1_1_1);
		
		this.innerStartPanelUP.add(this.onlyNorthButton);
		
		this.innerStartPanelUP.add(this.horizontalStrut_1_1_1_1);
		
		this.innerStartPanelUP.add(this.onlySouthButton);
		
		JPanel innerStartPanelDown = new JPanel();
		innerStartPanelDown.setBounds(17, 78, 741, 35);
		startPanel.add(innerStartPanelDown);
		innerStartPanelDown.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		
		fastCheck = new JCheckBox("FAST CARS");
		innerStartPanelDown.add(fastCheck);
		fastCheck.setSelected(true);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		innerStartPanelDown.add(horizontalStrut_2);
		
		labelCombo = new JLabel("MAX cars allowed when there are cars waiting in the opposite direction");
		innerStartPanelDown.add(labelCombo);
		
		allowedCombo = new JComboBox();
		innerStartPanelDown.add(allowedCombo);
		allowedCombo.setModel(new DefaultComboBoxModel(new String[] {"1", "4"}));
		allowedCombo.setSelectedIndex(1);
		
		easyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Launcher.launch(Launcher.EASY, fastCheck.isSelected(),
						Integer.parseInt(allowedCombo.getSelectedItem().toString()));
				disableAll();
				easyButton.setBackground(Color.GREEN);
				
			}
		});
		
		unbalancedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Launcher.launch(Launcher.UNBALANCED, fastCheck.isSelected(),
						Integer.parseInt(allowedCombo.getSelectedItem().toString()));
				disableAll();
				unbalancedButton.setBackground(Color.GREEN);
			}
		});
		
		heavyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Launcher.launch(Launcher.CONGESTED, fastCheck.isSelected(),
						Integer.parseInt(allowedCombo.getSelectedItem().toString()));
				disableAll();
				heavyButton.setBackground(Color.GREEN);
			}
		});
		
		limitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Launcher.launch(Launcher.LIMIT, fastCheck.isSelected(),
						Integer.parseInt(allowedCombo.getSelectedItem().toString()));
				disableAll();
				limitButton.setBackground(Color.GREEN);
			}
		});
		
		onlyNorthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Launcher.launch(Launcher.ONLYNORTH, fastCheck.isSelected(),
						Integer.parseInt(allowedCombo.getSelectedItem().toString()));
				disableAll();
				onlyNorthButton.setBackground(Color.GREEN);
			}
		});
		
		onlySouthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Launcher.launch(Launcher.ONLYSOUTH, fastCheck.isSelected(),
						Integer.parseInt(allowedCombo.getSelectedItem().toString()));
				disableAll();
				onlySouthButton.setBackground(Color.GREEN);
			}
		});
		
		// this.contentPane.add(this.textArea);
	}
	
	public void disableAll() {
		this.fastCheck.setEnabled(false);
		this.heavyButton.setEnabled(false);
		this.unbalancedButton.setEnabled(false);
		this.easyButton.setEnabled(false);
		this.limitButton.setEnabled(false);
		this.onlyNorthButton.setEnabled(false);
		this.onlySouthButton.setEnabled(false);
		this.allowedCombo.setEnabled(false);
		this.labelCombo.setEnabled(false);
	}
}
