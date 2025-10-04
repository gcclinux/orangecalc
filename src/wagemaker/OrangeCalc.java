package wagemaker;

/******************************************************************************************************
 * @author by Ricardo Wagemaker (["java"] + "@" + "wagemaker.co.uk") 2013-2015
 * @version 1.5.7
 * @since   2013 - 2025
 ******************************************************************************************************/

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.InputMap;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.border.Border;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class OrangeCalc extends JFrame implements ActionListener {
	private static final String ACTION_KEY = "The Action";
	
	private void loadFrameIcon() {
        URL imgUrl = null;
        ImageIcon imgIcon = null; 
        imgUrl = getClass().getResource("/images/gcclinux.png");
        imgIcon = new ImageIcon(imgUrl);
        Image img = imgIcon.getImage();
        this.setIconImage(img);
	}

	// Declare Version

	private static final long serialVersionUID = 1;

	JPanel[] row = new JPanel[5];
	JButton[] button = new JButton[24];
	String[] buttonString = { "7", "8", "9", "+", "4", "5", "6", "-", "1", "2",
			"3", "x", ".", "/", "C", "\u221A", "+/-", "=", "0", "%", " ", "x\u00B2", "\u03C0", " " };

	// Get the size of the screen
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	// Determine the new location of the window
	int w = this.getSize().width;
	int h = this.getSize().height;
	int x = (dim.width - w) / (3);
	int y = (dim.height - h) / 5;

	int[] dimW = { 73, 463, 450, 480, 152 };
	int[] dimH = { 73, 21, 53, 470 };
	Dimension displayDimension = new Dimension(dimW[1], dimH[2]);
	Dimension displayMiniDimension = new Dimension(dimW[1], dimH[1]);
	Dimension regularDimension = new Dimension(dimW[0], dimH[0]);
	Dimension extendedDimension = new Dimension(dimW[4], dimH[0]);
	Dimension zeroButDimension = new Dimension(dimW[0], dimH[0]);

	boolean[] function = new boolean[4];
	double[] temporary = { 0, 0 };
	JTextField display = new JTextField(""); // Main Digital Display
	JTextField displayMini = new JTextField(""); // result Digital Display
	JTextField allValues = new JTextField();

	boolean checkResult = false;

	static JMenuBar mb = new JMenuBar(); // Menu bar
	static JMenu mnuFile = new JMenu("File"); // File Entry on Menu bar
	static JMenu mnuEdit = new JMenu("Edit"); // File Entry on Menu bar
	static JMenuItem mnuStyle = new JMenu("Style"); // File Entry on Menu bar
	static JMenu mnuHelp = new JMenu("Help"); // Help Menu entry

	ImageIcon iconQuit =new ImageIcon(getClass().getResource("/images/quit.png") );
	ImageIcon iconAbout =new ImageIcon(getClass().getResource("/images/audio.png") );
	ImageIcon iconSupport =new ImageIcon(getClass().getResource("/images/support.png") );
	ImageIcon iconStar =new ImageIcon(getClass().getResource("/images/star.gif") );
	ImageIcon iconHist =new ImageIcon(getClass().getResource("/images/history.png") );
	ImageIcon iconClearHist =new ImageIcon(getClass().getResource("/images/delete.png") );
	ImageIcon iconSoundControl =new ImageIcon(getClass().getResource("/images/audio.png") );
	ImageIcon iconCopyResult =new ImageIcon(getClass().getResource("/images/copy.png") );
	ImageIcon iconOrange =new ImageIcon(getClass().getResource("/images/orange.png") );
	ImageIcon iconGray =new ImageIcon(getClass().getResource("/images/darkgray.png") );
	ImageIcon iconExport =new ImageIcon(getClass().getResource("/images/export.png") );
	ImageIcon iconBorderCompound =new ImageIcon(getClass().getResource("/images/copy.png") );
	ImageIcon iconBorderLower =new ImageIcon(getClass().getResource("/images/lower.png") );
	ImageIcon iconBorderRaised =new ImageIcon(getClass().getResource("/images/raised.png") );
	ImageIcon iconBorderPlain =new ImageIcon(getClass().getResource("/images/plain.png") );
	ImageIcon iconLime =new ImageIcon(getClass().getResource("/images/lime.png") );
	ImageIcon iconPurple =new ImageIcon(getClass().getResource("/images/star.gif") ); // TODO: Create purple.png icon
	

	public JMenu mnuItemBack = new JMenu("BackGround"); // Sub-Menu Quit item
	{
		// set icon separately because JMenu does not have a (String, Icon) constructor in all JDKs
		mnuItemBack.setIcon(iconStar);
	}
	public JMenu mnuItemButtonStyle = new JMenu("Button Style"); // Sub-Menu Quit item
	{
		// set icon separately because JMenu does not have a (String, Icon) constructor in all JDKs
		mnuItemButtonStyle.setIcon(iconStar);
	}
	public JMenuItem mnuItemQuit = new JMenuItem("Quit", iconQuit );
	public JMenuItem mnuItemAbout = new JMenuItem("About", iconAbout ); // Sub-Menu About Entry
	public JMenuItem mnuItemSupport = new JMenuItem("Support", iconSupport ); // Sub-Menu About Entry

	public JMenuItem mnuItemHist = new JMenuItem("History", iconHist ); // Sub-Menu About Entry
	public JMenuItem mnuItemHistOld = new JMenuItem("History (Old)", iconHist); // Sub-Menu About Entry
	public JMenuItem mnuClearHist = new JMenuItem("Clear (History)", iconClearHist ); // Sub-Menu About Entry
	public JMenuItem mnuSoundControl = new JMenuItem("Audio (On/Off)", iconSoundControl );
	public JMenuItem mnuCopyResult = new JMenuItem("Copy (result)", iconCopyResult );
	public JMenuItem mnuExport = new JMenuItem("Export (Excel)", iconExport );
	
	public JMenuItem mnuItemOrange = new JMenuItem("Orange", iconOrange); // Sub-Menu item
	public JMenuItem mnuItemPurple = new JMenuItem("Purple", iconPurple); // Sub-Menu item
	public JMenuItem mnuItemPlain = new JMenuItem("Plain", iconBorderPlain);
	public JMenuItem mnuItemNoBoarder = new JMenuItem("Plain", iconBorderPlain);
	public JMenuItem mnuItemBorderCompound = new JMenuItem("Compound", iconBorderCompound);
	public JMenuItem mnuItemBorderLower = new JMenuItem("Lower Border" ,iconBorderLower);
	public JMenuItem mnuItemBorderRaised = new JMenuItem("Raised Border" ,iconBorderRaised);
	public JMenuItem mnuItemLime = new JMenuItem("Lime" ,iconLime);

	// Creating Button Borders

	Border raisedbevel = BorderFactory.createRaisedBevelBorder();
	Border loweredbevel = BorderFactory.createLoweredBevelBorder();
	Object compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);

	// Finish Creating boarders

	// Key press constructor

	class KeyPressed extends AbstractAction {
		private static final long serialVersionUID = 1L;
		AbstractButton c;

		KeyPressed(AbstractButton c) {
			this.c = c;
		}

		public void actionPerformed(ActionEvent tf) {
			c.doClick();
		}

	}

	@SuppressWarnings("unused")
	OrangeCalc() throws IOException {
		
		

		super(""+CalcProperties.Title); // Frame Title
		
		loadFrameIcon();


		// ACTION_KEY is now a class field

		// START TOP Menu

		mnuFile.setFont(FontTheme.size14p);
		mnuEdit.setFont(FontTheme.size14p);
		mnuHelp.setFont(FontTheme.size14p);
		mnuStyle.setFont(FontTheme.size14p);
		mnuItemQuit.setFont(FontTheme.size14p);
		mnuItemSupport.setFont(FontTheme.size14p);

		mnuItemAbout.setFont(FontTheme.size14p);
		mnuItemHist.setFont(FontTheme.size14p);
		mnuClearHist.setFont(FontTheme.size14p);
		mnuItemHistOld.setFont(FontTheme.size14p);
		mnuSoundControl.setFont(FontTheme.size14p);
		mnuCopyResult.setFont(FontTheme.size14p);
		mnuItemBack.setFont(FontTheme.size14p);
		mnuItemButtonStyle.setFont(FontTheme.size14p);
		mnuItemOrange.setFont(FontTheme.size14p);
		mnuItemPurple.setFont(FontTheme.size14p);
		mnuExport.setFont(FontTheme.size14p);
		mnuItemNoBoarder.setFont(FontTheme.size14p);
		mnuItemBorderCompound.setFont(FontTheme.size14p);
		mnuItemBorderLower.setFont(FontTheme.size14p);
		mnuItemBorderRaised.setFont(FontTheme.size14p);
		mnuItemLime.setFont(FontTheme.size14p);
		mnuItemPlain.setFont(FontTheme.size14p);
		

		setJMenuBar(mb);
		mb.add(mnuFile);
		mb.add(mnuEdit);
		mb.add(mnuStyle);
		mb.add(mnuHelp);


		mnuHelp.add(mnuItemSupport);
		mnuHelp.add(mnuItemAbout);
		mnuFile.add(mnuItemHist);
		
		File historyOLD = CalcProperties.historyOld();
		if (historyOLD.exists()){
			mnuFile.add(mnuItemHistOld);
		}
		mnuFile.add(mnuItemQuit);
		mnuEdit.add(mnuCopyResult);
		mnuEdit.add(mnuSoundControl);
		mnuEdit.add(mnuClearHist);
		//mnuEdit.add(mnuExport);
		mnuStyle.add(mnuItemBack);
		mnuStyle.add(mnuItemButtonStyle);
		mnuItemBack.add(mnuItemLime);
		mnuItemBack.add(mnuItemPlain);
		mnuItemBack.add(mnuItemOrange);
		mnuItemBack.add(mnuItemPurple);
		mnuItemButtonStyle.add(mnuItemNoBoarder);
		mnuItemButtonStyle.add(mnuItemBorderCompound);
		mnuItemButtonStyle.add(mnuItemBorderLower);
		mnuItemButtonStyle.add(mnuItemBorderRaised);
		
		

		// END TOP MENU

		CalcProperties.setDesign();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(5, 6);
		setLayout(grid);
		

		for (int i = 0; i < 4; i++)
			function[i] = false;

		// FlowLayout f1 = new FlowLayout(FlowLayout.CENTER,0,10);
		FlowLayout f1 = new FlowLayout(FlowLayout.LEFT);
		// FlowLayout f2 = new FlowLayout(FlowLayout.LEFT,8,8);
		FlowLayout f2 = new FlowLayout(FlowLayout.LEFT);

		for (int i = 0; i < 5; i++)
			row[i] = new JPanel();
		row[0].setLayout(f1);

		for (int i = 1; i < 5; i++)
			row[i].setLayout(f2);
		
		// String workingDir;
		
		File file = new File(CalcProperties.workingDir());
		
		if (!file.exists()){
			CalcProperties.setDesign();
			mb.setForeground(CalcProperties.ORANGE); // set colour to menu bars
			mnuFile.setForeground(CalcProperties.ORANGE);
			mnuEdit.setForeground(CalcProperties.ORANGE);
			mnuHelp.setForeground(CalcProperties.ORANGE);
			mnuItemQuit.setForeground(CalcProperties.ORANGE); // set colour to menu bar
			mnuItemAbout.setForeground(CalcProperties.ORANGE); // set colour to menu bar
			mnuItemHist.setForeground(CalcProperties.ORANGE);
			mnuItemHistOld.setForeground(CalcProperties.ORANGE);
			mnuClearHist.setForeground(CalcProperties.ORANGE);
			mnuSoundControl.setForeground(CalcProperties.ORANGE);
			mnuItemSupport.setForeground(CalcProperties.ORANGE);

			mnuCopyResult.setForeground(CalcProperties.ORANGE);
			mnuItemBack.setForeground(CalcProperties.ORANGE);
			mnuItemButtonStyle.setForeground(CalcProperties.ORANGE);
			mnuItemOrange.setForeground(CalcProperties.ORANGE);
			mnuItemPurple.setForeground(CalcProperties.ORANGE);
			mnuStyle.setForeground(CalcProperties.ORANGE);
			mnuExport.setForeground(CalcProperties.ORANGE);
			mnuItemNoBoarder.setForeground(CalcProperties.ORANGE);
			mnuItemBorderCompound.setForeground(CalcProperties.ORANGE);
			mnuItemBorderLower.setForeground(CalcProperties.ORANGE);
			mnuItemBorderRaised.setForeground(CalcProperties.ORANGE);
			mnuItemLime.setForeground(CalcProperties.ORANGE);
			mnuItemPlain.setForeground(CalcProperties.ORANGE);
			
			
			for (int i = 0; i < 5; i++) {
				row[i].setBackground(CalcProperties.ORANGE);
			}
			
			Map<Integer, Character> keystrokeMap = createKeystrokeMap();
			
			for (int i = 0; i < 24; i++) {
				button[i] = new JButton(); // Moved
				button[i].setBorder((Border) compound);  // button border on STart up // Moved
				button[i].setContentAreaFilled(true); // Enable to remove buttons.
				button[i].setText(buttonString[i]);

				if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6
						|| i == 8 || i == 9 || i == 10 || i == 18) {
					button[i].setBackground(CalcProperties.DARKGREY);  // Moved
					button[i].setFont(FontTheme.size18i); // Moved
				} else if (i == 3 || i == 7 || i == 11 || i == 12 || i == 13
						|| i == 14 || i == 15 || i == 16 || i == 17 || i == 19
						|| i == 20 || i == 22 || i == 21 || i == 23) {
					button[i].setBackground(CalcProperties.ORANGE);
					button[i].setFont(FontTheme.size18i);
				}

				button[i].addActionListener(this);

				Character c = keystrokeMap.get(i);
				if (c != null) {
					KeyStroke keyStroke = KeyStroke.getKeyStroke(c, 0);
					InputMap inputMap = button[i]
							.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
					inputMap.put(keyStroke, ACTION_KEY);
					ActionMap actionMap = button[i].getActionMap();
					actionMap.put(ACTION_KEY, new KeyPressed(button[i]));
				}
			}
			
			// HERE
			
			Properties newProperties = new Properties(System.getProperties());
			
				newProperties.put("theme.colour", "ORANGE");
				newProperties.put("button.colour", "DARKGREY");
				newProperties.put("button_c.colour", "ORANGE");
				newProperties.put("audio.status", "false");
				newProperties.put("mb.colour", "ORANGE");
				newProperties.put("mnuFile.colour", "ORANGE");
				newProperties.put("mnuEdit.colour", "ORANGE");
				newProperties.put("mnuHelp.colour", "ORANGE");
				newProperties.put("mnuItemQuit.colour", "ORANGE");
				newProperties.put("mnuItemAbout.colour", "ORANGE");
				newProperties.put("mnuItemHist.colour", "ORANGE");
				newProperties.put("mnuItemHistOld.colour", "ORANGE");
				newProperties.put("mnuClearHist.colour", "ORANGE");
				newProperties.put("mnuSoundControl.colour", "ORANGE");
				newProperties.put("mnuItemSupport.colour", "ORANGE");

				newProperties.put("mnuCopyResult.colour", "ORANGE");
				newProperties.put("mnuItemBack.colour", "ORANGE");
				newProperties.put("mnuItemButtonStyle.colour", "ORANGE");
				newProperties.put("mnuItemOrange.colour", "ORANGE");
				newProperties.put("mnuItemPurple.colour", "ORANGE");
				newProperties.put("mnuStyle.colour", "ORANGE");
				newProperties.put("mnuExport.colour", "ORANGE");
				newProperties.put("mnuItemNoBoarder.colour", "ORANGE");
				newProperties.put("mnuItemBorderCompound.colour", "ORANGE");
				newProperties.put("mnuItemBorderLower.colour", "ORANGE");
				newProperties.put("mnuItemBorderRaised.colour", "ORANGE");
				newProperties.put("mnuItemLime.colour", "ORANGE");
				newProperties.put("mnuItemPlain.colour", "ORANGE");
				newProperties.put("row.colour", "ORANGE");
				
				for (int i = 0; i < 1; i++) {
					if (button[0].getBorder() == compound) {
						newProperties.put("style.type", "compound");
					} else if (button[0].getBorder() == loweredbevel) {
						newProperties.put("style.type", "loweredbevel");
					} else if (button[0].getBorder() == raisedbevel) {
						newProperties.put("style.type", "raisedbevel");
						button[20].setBorder(null);
					} else if (button[0].getBorder() == null) {
						newProperties.put("style.type", "null");
						button[20].setBorder(null);
					}
				}
				
				System.setProperties(newProperties);
				FileOutputStream out = null;
				try {
					out = new FileOutputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					newProperties.store(out, "");
				} catch (IOException e) {
					e.printStackTrace();
				}
				// HERE
			
		} else {
			
			// Reading config file and storing information in memory
			Properties prop = new Properties();
			FileInputStream input = new FileInputStream(file);
			prop.load(input);
			
			
			
			
			// Checking the integraty of the config file in case there is a OLD config without all the properties.
			if (prop.getProperty("mnuFile.colour") == null){ prop.setProperty("mnuFile.colour", "ORANGE"); }

			if (prop.getProperty("mnuItemAbout.colour") == null){ prop.setProperty("mnuItemAbout.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemBack.colour") == null){ prop.setProperty("mnuItemBack.colour", "ORANGE"); }
			if (prop.getProperty("mnuClearHist.colour") == null){ prop.setProperty("mnuClearHist.colour", "ORANGE"); }
			if (prop.getProperty("theme.colour") == null){ prop.setProperty("theme.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemOrange.colour") == null){ prop.setProperty("mnuItemOrange.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemPurple.colour") == null){ prop.setProperty("mnuItemPurple.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemHist.colour") == null){ prop.setProperty("mnuItemHist.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemHistOld.colour") == null){ prop.setProperty("mnuItemHistOld.colour", "ORANGE"); }

			if (prop.getProperty("style.type") == null){ prop.setProperty("style.type", "null"); }
			if (prop.getProperty("mnuEdit.colour") == null){ prop.setProperty("mnuEdit.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemBorderRaised.colour") == null){ prop.setProperty("mnuItemBorderRaised.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemSupport.colour") == null){ prop.setProperty("mnuItemSupport.colour", "ORANGE"); }
			if (prop.getProperty("mnuStyle.colour") == null){ prop.setProperty("mnuStyle.colour", "ORANGE"); }
			if (prop.getProperty("button_c.colour") == null){ prop.setProperty("button_c.colour", "ORANGE"); }
			if (prop.getProperty("mnuCopyResult.colour") == null){ prop.setProperty("mnuCopyResult.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemBorderCompound.colour") == null){ prop.setProperty("mnuItemBorderCompound.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemQuit.colour") == null){ prop.setProperty("mnuItemQuit.colour", "ORANGE"); }
			if (prop.getProperty("button.colour") == null){ prop.setProperty("button.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemNoBoarder.colour") == null){ prop.setProperty("mnuItemNoBoarder.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemLime.colour") == null){ prop.setProperty("mnuItemLime.colour", "ORANGE"); }
			if (prop.getProperty("mnuSoundControl.colour") == null){ prop.setProperty("mnuSoundControl.colour", "ORANGE"); }
			if (prop.getProperty("mnuHelp.colour") == null){ prop.setProperty("mnuHelp.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemBorderLower.colour") == null){ prop.setProperty("mnuItemBorderLower.colour", "ORANGE"); }
			if (prop.getProperty("audio.status") == null){ prop.setProperty("audio.status", "ORANGE"); }
			if (prop.getProperty("mnuItemPlain.colour") == null){ prop.setProperty("mnuItemPlain.colour", "ORANGE"); }
			if (prop.getProperty("mb.colour") == null){ prop.setProperty("mb.colour", "ORANGE"); }
			if (prop.getProperty("mnuExport.colour") == null){ prop.setProperty("mnuExport.colour", "ORANGE"); }
			if (prop.getProperty("mnuFile.colour") == null){ prop.setProperty("mnuFile.colour", "ORANGE"); }
			if (prop.getProperty("row.colour") == null){ prop.setProperty("row.colour", "ORANGE"); }
			if (prop.getProperty("mnuItemButtonStyle.colour") == null){ prop.setProperty("mnuItemButtonStyle.colour", "ORANGE"); }

			
			// CHECK VERSION
			
			boolean connectivity;
			 
			   try
			   {
			     URL version = URI.create(CalcProperties.versionFile).toURL();
			     URLConnection conn = version.openConnection();
			     conn.setConnectTimeout(5000);
			     conn.connect();
			     connectivity = true;
			     
			        BufferedReader in = new BufferedReader(new InputStreamReader(version.openStream()));
			        
			        String newVersion = in.readLine();
			        String currentVersion = CalcProperties.Version;

			        String currentValue = currentVersion.replaceAll("\\.", "");
			        String newValue = newVersion.replaceAll("\\.", "");
			        
			        int x = Integer.parseInt(currentValue);
			        int y = Integer.parseInt(newValue);

			        if (x == y){
			        	//JOptionPane.showMessageDialog(null, "Your Version is up to date!");
			        } else if (x > y){
			        } else if (x < y){
			        	int selectedOption = JOptionPane.showConfirmDialog(null, 
			        			" EN - Download new version? \n PL - aktualizacja nowej wersji? \n DE - Update auf neue Version? \n BR - atualizar para nova versão? \n NL - update naar de nieuwe versie? \n RU - обновление до новой версии? \n CH - 更新到新版本？", "Update Available - " +newVersion, JOptionPane.YES_NO_OPTION);
			        			if (selectedOption == JOptionPane.YES_OPTION) {
			        				System.out.println("Starting to Download");
			        				try 
			        		        {
			        		            Desktop.getDesktop().browse(URI.create("https://github.com/gcclinux/orangecalc"));
			        		        }           
			        		        catch (Exception e) {}
			        			} 	
			        }
			   }
			   catch (Exception e)
			   {
				   connectivity = false;
			   }
			
			// END VERSION
			
			
			String ORANGE = "ORANGE";
			String LIME = "LIME";
			String DARKGREY = "DARKGREY";
			String BLACK = "BLACK";
			String PURPLE = "PURPLE";
			
			//newProperties.put("theme.colour", prop.getProperty("theme.colour"));
			
			if (System.getProperty("audio.status") == null){
				System.setProperty("audio.status", prop.getProperty("audio.status"));
			}
			

			// create String to assign result to mnuFile.colour
			String MNUFILE = prop.getProperty("mnuFile.colour");
			
			// Identify colour and assign colour
			setMenuItemColor(mnuFile, prop, "mnuFile.colour");
			
			
			setMenuItemColor(mb, prop, "mb.colour");
			setMenuItemColor(mnuEdit, prop, "mnuEdit.colour");
			setMenuItemColor(mnuHelp, prop, "mnuHelp.colour");
			setMenuItemColor(mnuItemQuit, prop, "mnuItemQuit.colour");
			setMenuItemColor(mnuItemAbout, prop, "mnuItemAbout.colour");
			setMenuItemColor(mnuItemHist, prop, "mnuItemHist.colour");
			setMenuItemColor(mnuItemHistOld, prop, "mnuItemHist.colour");
			setMenuItemColor(mnuClearHist, prop, "mnuClearHist.colour");
			setMenuItemColor(mnuSoundControl, prop, "mnuSoundControl.colour");
			setMenuItemColor(mnuItemSupport, prop, "mnuItemSupport.colour");


			//
			setMenuItemColor(mnuCopyResult, prop, "mnuCopyResult.colour");
			setMenuItemColor(mnuItemBack, prop, "mnuItemBack.colour");
			setMenuItemColor(mnuItemButtonStyle, prop, "mnuItemButtonStyle.colour");
			setMenuItemColor(mnuItemOrange, prop, "mnuItemOrange.colour");
			setMenuItemColor(mnuItemPurple, prop, "mnuItemPurple.colour");
			setMenuItemColor(mnuStyle, prop, "mnuStyle.colour");
			setMenuItemColor(mnuExport, prop, "mnuExport.colour");
			setMenuItemColor(mnuItemNoBoarder, prop, "mnuItemNoBoarder.colour");
			setMenuItemColor(mnuItemBorderCompound, prop, "mnuItemBorderCompound.colour");
			setMenuItemColor(mnuItemBorderLower, prop, "mnuItemBorderLower.colour");
			setMenuItemColor(mnuItemBorderRaised, prop, "mnuItemBorderRaised.colour");
			setMenuItemColor(mnuItemLime, prop, "mnuItemLime.colour");
			setMenuItemColor(mnuItemPlain, prop, "mnuItemPlain.colour");
			//
			String CALROW = prop.getProperty("row.colour");
			
			if (CALROW.equals(ORANGE)) {	
				for (int i = 0; i < 5; i++) {
					row[i].setBackground(CalcProperties.ORANGE);
				} 
				//\\ ONLY IN ORANGE AT THE MOMENT
				
				Map<Integer, Character> keystrokeMap = createKeystrokeMap();
				
				String STYLETYPE = prop.getProperty("style.type");	
				
				for (int i = 0; i < 24; i++) {
					button[i] = new JButton(); // Moved
					button[i].setBorder(getBorderByType(STYLETYPE));
					button[i].setContentAreaFilled(true); 
					button[i].setText(buttonString[i]);

					if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6
							|| i == 8 || i == 9 || i == 10 || i == 18) {
						button[i].setBackground(CalcProperties.DARKGREY);  // Moved
						button[i].setFont(FontTheme.size18i); // Moved
					} else if (i == 3 || i == 7 || i == 11 || i == 12 || i == 13
							|| i == 14 || i == 15 || i == 16 || i == 17 || i == 19
							|| i == 20 || i == 22 || i == 21 || i == 23) {
						button[i].setBackground(CalcProperties.Theme_C());
						button[i].setFont(FontTheme.size18i);
					}

					button[i].addActionListener(this);

					Character c = keystrokeMap.get(i);
					if (c != null) {
						KeyStroke keyStroke = KeyStroke.getKeyStroke(c, 0);
						InputMap inputMap = button[i]
								.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
						inputMap.put(keyStroke, ACTION_KEY);
						ActionMap actionMap = button[i].getActionMap();
						actionMap.put(ACTION_KEY, new KeyPressed(button[i]));
					}
				}
				
				//\\ ONLY IN ORANGE AT THE MOMENT MUST ADD TO LIME / DARKGRAY / BLACK
				
			} else if  (CALROW.equals("null")) {	
				for (int i = 0; i < 5; i++) {
					row[i].setBackground(null);
				} 
				//\\ ONLY IN ORANGE AT THE MOMENT
				
				Map<Integer, Character> keystrokeMap = createKeystrokeMap();
				
				String STYLETYPE = prop.getProperty("style.type");				
				
				for (int i = 0; i < 24; i++) {
					button[i] = new JButton(); // Moved
					button[i].setBorder(getBorderByType(STYLETYPE));
					button[i].setContentAreaFilled(true); 
					button[i].setText(buttonString[i]);

					if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6
							|| i == 8 || i == 9 || i == 10 || i == 18) {
						button[i].setBackground(CalcProperties.DARKGREY); 
						button[i].setFont(FontTheme.size18i); 
					} else if (i == 3 || i == 7 || i == 11 || i == 12 || i == 13
							|| i == 14 || i == 15 || i == 16 || i == 17 || i == 19
							|| i == 20 || i == 22 || i == 21 || i == 23) {
						button[i].setBackground(CalcProperties.Theme_C());
						button[i].setFont(FontTheme.size18i);
					}

					button[i].addActionListener(this);

					Character c = keystrokeMap.get(i);
					if (c != null) {
						KeyStroke keyStroke = KeyStroke.getKeyStroke(c, 0);
						InputMap inputMap = button[i]
								.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
						inputMap.put(keyStroke, ACTION_KEY);
						ActionMap actionMap = button[i].getActionMap();
						actionMap.put(ACTION_KEY, new KeyPressed(button[i]));
					}
				}
				
				//\\ ONLY IN ORANGE AT THE MOMENT MUST ADD TO LIME / DARKGRAY / BLACK
				
			} else if (CALROW.equals(LIME)) {	
				for (int i = 0; i < 5; i++) {
					row[i].setBackground(CalcProperties.LIME);
				} 
				
				Map<Integer, Character> keystrokeMap = createKeystrokeMap();
				
				String STYLETYPE = prop.getProperty("style.type");

				for (int i = 0; i < 24; i++) {
					button[i] = new JButton(); 
					button[i].setBorder(getBorderByType(STYLETYPE));
					button[i].setContentAreaFilled(true); 
					button[i].setText(buttonString[i]);

					if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6
							|| i == 8 || i == 9 || i == 10 || i == 18) {
						button[i].setBackground(CalcProperties.DARKGREY);  // Moved
						button[i].setFont(FontTheme.size18i); // Moved
					} else if (i == 3 || i == 7 || i == 11 || i == 12 || i == 13
							|| i == 14 || i == 15 || i == 16 || i == 17 || i == 19
							|| i == 20 || i == 22 || i == 21 || i == 23) {
						button[i].setBackground(CalcProperties.Theme_C());
						button[i].setFont(FontTheme.size18i);
						button[i].setForeground(CalcProperties.BLACK);
					}

					button[i].addActionListener(this);

					Character c = keystrokeMap.get(i);
					if (c != null) {
						KeyStroke keyStroke = KeyStroke.getKeyStroke(c, 0);
						InputMap inputMap = button[i]
								.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
						inputMap.put(keyStroke, ACTION_KEY);
						ActionMap actionMap = button[i].getActionMap();
						actionMap.put(ACTION_KEY, new KeyPressed(button[i]));
					}
				}
				
			} 	else if (CALROW.equals(BLACK)) {	
				for (int i = 0; i < 5; i++) {
					row[i].setBackground(CalcProperties.BLACK);
				} 
				Map<Integer, Character> keystrokeMap = createKeystrokeMap();
				
				String STYLETYPE = prop.getProperty("style.type");

				for (int i = 0; i < 24; i++) {
					button[i] = new JButton(); // Moved
					button[i].setBorder(getBorderByType(STYLETYPE));
					button[i].setContentAreaFilled(true); 
					button[i].setText(buttonString[i]);

					if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6
							|| i == 8 || i == 9 || i == 10 || i == 18) {
							// numeric buttons
							button[i].setBackground(CalcProperties.DARKGREY);
							button[i].setForeground(CalcProperties.BLACK);
							button[i].setFont(FontTheme.size18i);
							button[i].setContentAreaFilled(true);
					} else if (i == 3 || i == 7 || i == 11 || i == 12 || i == 13
							|| i == 14 || i == 15 || i == 16 || i == 17 || i == 19
							|| i == 20 || i == 22 || i == 21 || i == 23) {
							// non-numeric buttons
							button[i].setBackground(CalcProperties.BLACK);
							button[i].setForeground(CalcProperties.BLACK);
							button[i].setFont(FontTheme.size18i);
							button[i].setContentAreaFilled(true);
							if (i == 20) {
								button[i].setBorder(null);
							} else {
								button[i].setBorder(BorderFactory.createLineBorder(CalcProperties.WHITE));
							}
					}

					button[i].addActionListener(this);

					Character c = keystrokeMap.get(i);
					if (c != null) {
						KeyStroke keyStroke = KeyStroke.getKeyStroke(c, 0);
						InputMap inputMap = button[i]
								.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
						inputMap.put(keyStroke, ACTION_KEY);
						ActionMap actionMap = button[i].getActionMap();
						actionMap.put(ACTION_KEY, new KeyPressed(button[i]));
					}
				}
				
			} else if (CALROW.equals(PURPLE)) {	
				for (int i = 0; i < 5; i++) {
					row[i].setBackground(CalcProperties.PURPLE);
				} 
				
				Map<Integer, Character> keystrokeMap = createKeystrokeMap();
				
				String STYLETYPE = prop.getProperty("style.type");

				for (int i = 0; i < 24; i++) {
					button[i] = new JButton(); 
					button[i].setBorder(getBorderByType(STYLETYPE));
					button[i].setContentAreaFilled(true); 
					button[i].setText(buttonString[i]);

					if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6
							|| i == 8 || i == 9 || i == 10 || i == 18) {
						button[i].setBackground(CalcProperties.DARKGREY);
						button[i].setFont(FontTheme.size18i);
					} else if (i == 3 || i == 7 || i == 11 || i == 12 || i == 13
							|| i == 14 || i == 15 || i == 16 || i == 17 || i == 19
							|| i == 20 || i == 22 || i == 21 || i == 23) {
						button[i].setBackground(CalcProperties.PURPLE);
						button[i].setFont(FontTheme.size18i);
						button[i].setForeground(CalcProperties.WHITE);
					}

					button[i].addActionListener(this);

					Character c = keystrokeMap.get(i);
					if (c != null) {
						KeyStroke keyStroke = KeyStroke.getKeyStroke(c, 0);
						InputMap inputMap = button[i]
								.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
						inputMap.put(keyStroke, ACTION_KEY);
						ActionMap actionMap = button[i].getActionMap();
						actionMap.put(ACTION_KEY, new KeyPressed(button[i]));
					}
				}
				
			}
		
		}

		display.setFont(FontTheme.size40b);
		display.setEditable(false);
		display.setBorder((Border) compound);
		display.setBackground(Color.white);
		display.setForeground(Color.black);
		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setPreferredSize(displayDimension);

		displayMini.setFont(FontTheme.size15p);
		displayMini.setEditable(false);
		displayMini.setBorder((Border) compound);
		displayMini.setBackground(Color.white);
		displayMini.setForeground(Color.black);
		displayMini.setHorizontalAlignment(JTextField.LEFT);
		displayMini.setPreferredSize(displayMiniDimension);

		for (int i = 0; i < 24; i++) {
			button[i].setPreferredSize(regularDimension);
			button[17].setPreferredSize(extendedDimension);
		}

		row[0].add(display);
		row[0].add(displayMini);
		add(row[0]);

		for (int i = 0; i < 5; i++)
			row[1].add(button[i]);
		row[1].add(button[19]);
		row[1].add(button[14]);
		add(row[1]);

		for (int i = 4; i < 8; i++)
			row[2].add(button[i]);
		row[2].add(button[15]);
		row[2].add(button[21]);
		add(row[2]);

		for (int i = 8; i < 12; i++)
			row[3].add(button[i]);
		row[3].add(button[16]);
		row[3].add(button[22]);
		add(row[3]);

		row[4].add(button[18]);

		for (int i = 12; i < 14; i++)
			row[4].add(button[i]);
		row[4].add(button[17]);
		add(row[4]);

	URL imageURL1 = OrangeCalc.class.getResource("/images/mini.png");
	// Load the image synchronously via ImageIcon so we have the intrinsic dimensions
	ImageLoader img = new ImageLoader(new ImageIcon(imageURL1).getImage());
	Dimension origImgSize = img.getPreferredSize();
	Dimension halfSize = new Dimension(Math.max(1, origImgSize.width), Math.max(1, origImgSize.height));
	// Apply half-size to only this ImageLoader instance
	img.setPreferredSize(halfSize);
	// Ensure the hosting button uses a simple layout and matches the image preferred size
	button[20].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
	button[20].setPreferredSize(halfSize);
	button[20].add(img);
	button[20].setContentAreaFilled(false);
	button[20].setBorder(BorderFactory.createEmptyBorder());
	row[4].add(button[20]);
		// End Penguin icon

		// Finalize a fixed-size, non-resizable frame 550x550 and center it
		int w = 480; int h = 480;
		setSize(w, h);
		setMinimumSize(new Dimension(w, h));
		setMaximumSize(new Dimension(w, h));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		// start quit button

		mnuSoundControl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					SoundControl.setVisible();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		mnuItemQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		
		mnuCopyResult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				StringSelection stringSelection = new StringSelection (display.getText());
				Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
				clpbrd.setContents (stringSelection, null);
			}
		});
		

		mnuClearHist.addActionListener(new ClearHistAction(this));
		
		// START BORDER
		mnuItemBorderCompound.addActionListener(new BorderCompoundAction(button, (Border) compound));
		
		mnuItemBorderLower.addActionListener(new BorderLowerAction(button, (Border) loweredbevel));
		
		mnuItemNoBoarder.addActionListener(new NoBorderAction(button));
		
		mnuItemPlain.addActionListener(new PlainAction(this, button, row, (Border) compound, loweredbevel, raisedbevel));
		
		mnuItemLime.addActionListener(new LimeAction(this, button, row, (Border) compound, loweredbevel, raisedbevel));
	
		mnuItemOrange.addActionListener(new OrangeAction(this, button, row, (Border) compound, loweredbevel, raisedbevel));
		
		mnuItemPurple.addActionListener(new PurpleAction(this, button, row, (Border) compound, loweredbevel, raisedbevel));

		mnuItemHist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				File file = null;
				try {
					file = CalcProperties.historyFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileWriter resultfilestr = new FileWriter(CalcProperties.historyFile(), true);
						BufferedWriter outHist = new BufferedWriter(resultfilestr);
						outHist.write("<center><b><h1>\"Calculator History\"</h1></b></center>");
						outHist.newLine();
						outHist.write("<table width=\"490\" border cellpadding=\"2\">");
						outHist.close();
						
						/* Desktop.getDesktop().browse(
								CalcProperties.historyFile().toURI()); 
								*/
						DisplayReport.main(null);
					} catch (IOException e) {
					}
				} else {
					try {
						DisplayReport.main(null);
					} catch (IOException e) {
					}
				}

			}
		});
		
		mnuItemHistOld.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				File file = null;
				try {
					file = CalcProperties.historyOld();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		
				if (!file.exists()) {
				} else {
					try {
						Desktop.getDesktop().browse(CalcProperties.historyOld().toURI()); 
					} catch (IOException e) {
					}
				}

			}
		});	
		mnuItemSupport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					if (Desktop.isDesktopSupported()) {
						Desktop desktop = Desktop.getDesktop();
						if (desktop.isSupported(Desktop.Action.BROWSE)) {
							desktop.browse(new URI("https://github.com/gcclinux/orangecalc/discussions/"));
							return;
						}
					}
					// Fallback for environments without desktop support (like WSL)
					System.out.println("Browser not supported in this environment.");
					System.out.println("Please visit: https://github.com/gcclinux/orangecalc/discussions/");
				} catch (Exception e) {
					System.err.println("Failed to open browser: " + e.getMessage());
					System.out.println("Please visit: https://github.com/gcclinux/orangecalc/discussions/");
				}
			}
		});

		// start about button

		mnuItemAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				About.showAbout(OrangeCalc.this);
			}
		});
		// Select button focus, required for initial key stroke

		button[17].requestFocus();
		

	} // End super("Calculator XL")

	// ==================== HELPER METHODS ====================
	
	/**
	 * Play audio sound if enabled in settings
	 */
	private void playAudioIfEnabled() {
		try {
			if (CalcProperties.AudioTrue().contains("true")) {
				new Sound("typewriter.wav").start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set menu item color from properties
	 */
	private void setMenuItemColor(JComponent item, Properties prop, String propertyKey) {
		String colorName = prop.getProperty(propertyKey);
		Color color = switch(colorName) {
			case "ORANGE" -> CalcProperties.ORANGE;
			case "LIME" -> CalcProperties.LIME;
			case "DARKGREY" -> CalcProperties.DARKGREY;
			case "BLACK" -> CalcProperties.BLACK;
			default -> CalcProperties.ORANGE;
		};
		item.setForeground(color);
	}
	
	/**
	 * Get border by type name
	 */
	private Border getBorderByType(String styleType) {
		return switch(styleType) {
			case "compound" -> (Border) compound;
			case "loweredbevel" -> (Border) loweredbevel;
			case "raisedbevel" -> (Border) raisedbevel;
			case "null" -> null;
			default -> {
				System.out.println("NO BORDER FOUND, STYLE = " + styleType);
				yield (Border) compound;
			}
		};
	}
	
	/**
	 * Create keystroke map for keyboard shortcuts
	 */
	private Map<Integer, Character> createKeystrokeMap() {
		Map<Integer, Character> keystrokeMap = new HashMap<>();
		keystrokeMap.put(14, (char) KeyEvent.VK_ESCAPE);
		keystrokeMap.put(0, '7');
		keystrokeMap.put(1, '8');
		keystrokeMap.put(2, '9');
		keystrokeMap.put(3, '+');
		keystrokeMap.put(4, '4');
		keystrokeMap.put(5, '5');
		keystrokeMap.put(6, '6');
		keystrokeMap.put(7, '-');
		keystrokeMap.put(8, '1');
		keystrokeMap.put(9, '2');
		keystrokeMap.put(10, '3');
		keystrokeMap.put(11, '*');
		keystrokeMap.put(12, '.');
		keystrokeMap.put(13, '/');
		keystrokeMap.put(17, '=');
		keystrokeMap.put(17, (char) KeyEvent.VK_ENTER);
		keystrokeMap.put(18, '0');
		keystrokeMap.put(19, '%');
		return keystrokeMap;
	}
	
	/**
	 * Handle number button press
	 */
	private void handleNumberButton(String digit) {
		playAudioIfEnabled();
		
		if (allValues.getText().isEmpty()) {
			display.setText(digit);
			allValues.setText(digit);
		} else {
			if (checkResult) {
				if (display.getText().contains(".")) {
					display.setText(display.getText() + digit);
				} else {
					display.setText(digit);
				}
				allValues.setText(display.getText());
				checkResult = false;
			} else {
				display.setText(display.getText() + digit);
				allValues.setText(display.getText());
			}
		}
	}
	
	// ==================== END HELPER METHODS ====================

	public void clear() {
		playAudioIfEnabled();
		
		try {
			display.setText("");
			displayMini.setText("");
			allValues.setText("");
			checkResult = false;
			for (int i = 0; i < 4; i++)
				function[i] = false;
			for (int i = 0; i < 2; i++)
				temporary[i] = 0;
		} catch (NullPointerException e) {
		}
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public void getPower() {  // Power

		try {
			double value = Math.pow(Double.parseDouble(allValues.getText()),2);
			Double xx = Double.valueOf(value);
			int x = xx.intValue();

			FileWriter resultfilestr = new FileWriter(
					CalcProperties.historyFile(), true);
			BufferedWriter outHist = new BufferedWriter(resultfilestr);

			if (x == value) {
				display.setText(Integer.toString(x));
				playAudioIfEnabled();

				displayMini.setText("x\u00B2 " + allValues.getText() + " = " + Integer.toString(x));
				outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">" + "x\u00B2" + allValues.getText() + " = "+ Integer.toString(x) + "</td></tr>");
				outHist.newLine();
				outHist.close();

				allValues.setText(Integer.toString(x));

			} else {
				//round(value, 2); 
				display.setText(Double.toString(round(value, 2)));

				displayMini.setText("x\u00B2 " + allValues.getText() + " = " + Double.toString(round(value, 2)));
				outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">" + "x\u00B2" + allValues.getText() + " = "+ Double.toString(value) + "</td></tr>");
				outHist.newLine();
				outHist.close();
				playAudioIfEnabled();

				allValues.setText(Double.toString(round(value, 2)));
			}

		} catch (IOException e) {
		} catch (NumberFormatException e) {
		}
	}

	public void getSqrt() {

		try {
			double value = Math.sqrt(Double.parseDouble(allValues.getText()));
			Double xx = Double.valueOf(value);
			int x = xx.intValue();

			FileWriter resultfilestr = new FileWriter(
					CalcProperties.historyFile(), true);
			BufferedWriter outHist = new BufferedWriter(resultfilestr);

			if (x == value) {
				display.setText(Integer.toString(x));
				playAudioIfEnabled();

				displayMini.setText("Sq (\u221A) " + allValues.getText() + " = "
						+ Integer.toString(x));

				outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">" + "Sq  &radic; " + allValues.getText() + " = "+ Integer.toString(x) + "</td></tr>");
				outHist.newLine();
				outHist.close();

				allValues.setText(Integer.toString(x));

			} else {
				display.setText(Double.toString(value));

				displayMini.setText("Sq (\u221A)  " + allValues.getText() + " = "
						+ Double.toString(value));
				outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">" + "Sq  &radic; " + allValues.getText() + " = "+ Double.toString(value) + "</td></tr>");
				outHist.newLine();
				outHist.close();
				playAudioIfEnabled();

				allValues.setText(Double.toString(value));
			}

		} catch (IOException e) {
		} catch (NumberFormatException e) {
		}
	}

	public void getPosNeg() {
		try {
			double value = Double.parseDouble(display.getText());
			if (value != 0) {
				value = value * (-1);
				Double xx = Double.valueOf(value);
				int x = xx.intValue();
				if (x == value) {
					display.setText(Integer.toString(x));
					allValues.setText(Integer.toString(x));
					playAudioIfEnabled();
				} else {
					display.setText(Double.toString(value));
					allValues.setText(Double.toString(value));
				}
			} else {
			}
		} catch (NumberFormatException e) {
		}
	}

	public void getResult() {

		double result = 0;
		temporary[1] = Double.parseDouble(allValues.getText());
		String temp0 = Double.toString(temporary[0]);
		String temp1 = Double.toString(temporary[1]);
		try {
			if (temp0.contains("-")) {
				String[] temp00 = temp0.split("-", 2);
				temporary[0] = (Double.parseDouble(temp00[1]) * -1);
			}
			if (temp1.contains("-")) {
				String[] temp11 = temp1.split("-", 2);
				temporary[1] = (Double.parseDouble(temp11[1]) * -1);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (function[2] == true && temp0 != null && temp1 != null) {
				result = temporary[0] * temporary[1];
			} else if (function[3] == true && temp0 != null && temp1 != null) {
				result = temporary[0] / temporary[1];
			} else if (function[0] == true && temp0 != null && temp1 != null) {
				result = temporary[0] + temporary[1];
			} else if (function[1] == true && temp0 != null && temp1 != null) {
				result = temporary[0] - temporary[1];
			}

			for (int i = 0; i < 4; i++) {
				function[i] = false;
			}

			Double xx = Double.valueOf(result);
			int x = xx.intValue();
			if (x == result) {
				String str = allValues.getText();
				String mstr = displayMini.getText();
				display.setText(Integer.toString(x));
				allValues.setText(Integer.toString(x));

				FileWriter resultfilestr = new FileWriter(
						CalcProperties.historyFile(), true);
				BufferedWriter outHist = new BufferedWriter(resultfilestr);

				if (mstr.contains("=")) {
					if (mstr.contains("%")) {
						displayMini.setText(mstr + " = " + Integer.toString(x));
					} else {
						displayMini.setText(mstr + str + " = "
								+ Integer.toString(x));
					}


					if (checkResult == true) {
						if (mstr.contains("%")) {
							outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">" + mstr + " " + " = " + " " + Integer.toString(x) + "</td></tr>");
							outHist.newLine();
							outHist.close();
						} else {

							outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">" + mstr + " " + str + " " + " = " + " " + Integer.toString(x) + "</td></tr>");
							outHist.newLine();
							outHist.close();
						}
					}

				} else {

					if (mstr.contains("%")) {
						displayMini.setText(mstr + " = " + Integer.toString(x));
						if (checkResult == true) {
							outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">" + mstr + " " + " = " + " " + Integer.toString(x) + "</td></tr>");
							outHist.newLine();
							outHist.close();
						}
					} else {
						displayMini.setText(mstr + str + " = "
								+ Integer.toString(x));

						if (checkResult == true) {
							outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">" + mstr + " " + str + " " + " = " + " " + Integer.toString(x) + "</td></tr>");
							outHist.newLine();
							outHist.close();
						}
					}
				}
			} else {
				String str = allValues.getText();
				String mstr = displayMini.getText();
				result = Math.round(result * 100) / 100.0d;
				display.setText(Double.toString(result));
				allValues.setText(Double.toString(result));

				FileWriter resultfilestr = new FileWriter(
						CalcProperties.historyFile(), true);
				BufferedWriter outHist = new BufferedWriter(resultfilestr);

				if (mstr.contains("=")) {
					if (mstr.contains("%")) {
						displayMini.setText(mstr + " = "
								+ Double.toString(result)); // changed from x to
															// result
						if (checkResult == true) {
							outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">" + mstr + " " + " = " + " " + Double.toString(result) + "</td></tr>");
							outHist.newLine();
							outHist.close();
						}
					} else {
						displayMini.setText(mstr + str + " = "
								+ Double.toString(result)); // changed from x to
															// result
					}
				} else {
					if (mstr.contains("%")) {
						displayMini.setText(mstr + " = "
								+ Double.toString(result));
						if (checkResult == true) {
							outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">" + mstr + " " + " = " + " " + Double.toString(result) + "</td></tr>");
							outHist.newLine();
							outHist.close();
						}
					} else {
						displayMini.setText(mstr + str + " = "
								+ Double.toString(result));
						if (checkResult == true) {
							outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">" + mstr + " " + str + " " + " = " + " " + Double.toString(result) + "</td></tr>");
							outHist.newLine();
							outHist.close();
						}
					}
				}
			}
		} catch (NumberFormatException e) {
		} catch (IOException e) {
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		
		// Number buttons 0-9
		if (ae.getSource() == button[0]) { // Number SEVEN - 7
			handleNumberButton("7");
		}

		if (ae.getSource() == button[1]) { // Number EIGHT - 8
			handleNumberButton("8");
		}

		if (ae.getSource() == button[2]) { // Number NINE - 9
			handleNumberButton("9");
		}

		if (ae.getSource() == button[3]) { // add function[0] --> Adding numbers
											// now working... button 3

			String str = allValues.getText();
			String str9 = displayMini.getText();
			if (str9.contains("%") && str9.contains("(")
					&& checkResult == false) {
				// getResult();
			} else {

				if (function[1] == true || function[2] == true
						|| function[3] == true) {
					getResult();
					String str2 = allValues.getText();
					String str3 = displayMini.getText();
					temporary[0] = Double.parseDouble(allValues.getText());
					function[0] = true;
					display.setText(str2);
					displayMini.setText(str3 + " + ");
					allValues.setText("");
					playAudioIfEnabled();
				}

				if (function[0] == false) {
					if (!allValues.getText().isEmpty()) {
						temporary[0] = Double.parseDouble(allValues.getText());
						function[0] = true;
						display.setText("");
						displayMini.setText(str + " + ");
						allValues.setText(str);
						playAudioIfEnabled();
					}
				} else {
					getResult();
					String str2 = allValues.getText();
					String str3 = displayMini.getText();
					temporary[0] = Double.parseDouble(allValues.getText());
					function[0] = true;
					display.setText(str2);
					displayMini.setText(str3 + " + ");
					allValues.setText("");
					playAudioIfEnabled();
				}
			}
		}

		if (ae.getSource() == button[7]) { // subtraction function[1] -->
											// subtracting numbers now
											// working... 16-10-2013

			String str = allValues.getText();
			String str9 = displayMini.getText();

			if (str9.contains("%") && str9.contains("(")
					&& checkResult == false) {
			} else {

				if (function[0] == true || function[2] == true
						|| function[3] == true) {
					getResult();
					String str2 = allValues.getText();
					String str3 = displayMini.getText();
					temporary[0] = Double.parseDouble(allValues.getText());
					function[1] = true;
					display.setText(str2);
					displayMini.setText(str3 + " - ");
					allValues.setText("");
					playAudioIfEnabled();
				}

				if (function[1] == false) {
					if (!allValues.getText().isEmpty()) {
						temporary[0] = Double.parseDouble(allValues.getText());
						function[1] = true;
						display.setText("");
						displayMini.setText(str + " - ");
						allValues.setText(str);
						playAudioIfEnabled();
					}
				} else {
					getResult();
					String str2 = allValues.getText();
					String str3 = displayMini.getText();
					temporary[0] = Double.parseDouble(allValues.getText());
					function[1] = true;
					display.setText(str2);
					displayMini.setText(str3 + " - ");
					allValues.setText("");
					playAudioIfEnabled();
				}
			}
		}

		if (ae.getSource() == button[11]) { // multiplication function[2] --> 16-10-2013

			String str = allValues.getText();
			//String str8 = display.getText();
			String str9 = displayMini.getText();


			if (str9.contains("%") && str9.contains("(")
					&& checkResult == false) {
			} else {

				if (function[0] == true || function[1] == true
						|| function[3] == true) {
					getResult();
					String str2 = allValues.getText();
					String str3 = displayMini.getText();
					temporary[0] = Double.parseDouble(allValues.getText());
					function[2] = true;
					display.setText(str2);
					displayMini.setText(str3 + " x ");
					allValues.setText("");
					playAudioIfEnabled();
				}

				if (function[2] == false) {
					if (!allValues.getText().isEmpty()) {
						temporary[0] = Double.parseDouble(allValues.getText());
						function[2] = true;
						display.setText("");
						displayMini.setText(str + " x ");
						allValues.setText(str);
						playAudioIfEnabled();
					}
				} else {
					getResult();
					String str2 = allValues.getText();
					String str3 = displayMini.getText();
					temporary[0] = Double.parseDouble(allValues.getText());
					function[2] = true;
					display.setText(str2);
					displayMini.setText(str3 + " x ");
					allValues.setText("");
					playAudioIfEnabled();
				}
			}
		}
		
		

		if (ae.getSource() == button[13]) { // division function[3] --> divide
											// numbers now working... 16-10-2013

			String str = allValues.getText();
			String str9 = displayMini.getText();

			if (str9.contains("%") && str9.contains("(")
					&& checkResult == false) {
			} else {

				if (function[0] == true || function[1] == true
						|| function[2] == true) {
					getResult();
					String str2 = allValues.getText();
					String str3 = displayMini.getText();
					temporary[0] = Double.parseDouble(allValues.getText());
					function[3] = true;
					display.setText(str2);
					displayMini.setText(str3 + " / ");
					allValues.setText("");
					playAudioIfEnabled();
				}

				if (function[3] == false) {
					if (!allValues.getText().isEmpty()) {
						temporary[0] = Double.parseDouble(allValues.getText());
						function[3] = true;
						display.setText("");
						displayMini.setText(str + " / ");
						allValues.setText(str);
						playAudioIfEnabled();
					}
				} else {
					getResult();
					String str2 = allValues.getText();
					String str3 = displayMini.getText();
					temporary[0] = Double.parseDouble(allValues.getText());
					function[3] = true;
					display.setText(str2);
					displayMini.setText(str3 + " / ");
					allValues.setText("");
					playAudioIfEnabled();
				}
			}
		}

		if (ae.getSource() == button[19]) { // Button 19 == Percentage

			double result = 0;
			String strp = displayMini.getText();
			temporary[1] = Double.parseDouble(allValues.getText());
			String temp0 = Double.toString(temporary[0]);
			String temp1 = Double.toString(temporary[1]);

			
			/* if (function[2] == true) {
				System.out.println("CODE-008 - Function 2 = true");
			} else if (function[3] == true) {
				System.out.println("CODE-009 - Function 3 = true");
			} else if (function[0] == true) {
				System.out.println("CODE-010 - Function 0 = true");
			} else if (function[1] == true) {
				System.out.println("CODE-011 - Function 1 = true");
			} else {
				System.out.println("CODE-012 - No Function Active");
			}
			*/

			if (temp0.equals("0.0") || strp.contains("%") || (function[0] == false && function[3] == false && function[1] == false && function[2] == false)) {
			} else {
				try {
					if (temp0.contains("-")) {
						String[] temp00 = temp0.split("-", 2);
						temporary[0] = (Double.parseDouble(temp00[1]) * -1);
					}
					if (temp1.contains("-")) {
						String[] temp11 = temp1.split("-", 2);
						temporary[1] = (Double.parseDouble(temp11[1]) * -1);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				try {
					result = (temporary[0] / 100) * temporary[1];
					result = Math.round(result * 100) / 100.0d;
					

					Double xx = Double.valueOf(result);
					int x = xx.intValue();
					if (x == result) {
						String str = allValues.getText();
						String mstr = displayMini.getText();
						display.setText(Integer.toString(x));
						allValues.setText(Integer.toString(x));
						playAudioIfEnabled();

						FileWriter resultfilestr = new FileWriter(
								CalcProperties.historyFile(), true);
						BufferedWriter outHist = new BufferedWriter(
								resultfilestr);

						if (mstr.contains("=")) {
							
							displayMini.setText("(" + mstr + str + "%" + ") = "	+ Integer.toString(x));
							


							if (checkResult == true) {
								outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">"+ "(" + mstr + " " + str + " " + ")" + " = " + " " + Integer.toString(x) + "</td></tr>");
								outHist.newLine();
								outHist.close();
							}

						} else {
							displayMini.setText("(" + mstr + str + "%" + ") = "
									+ Integer.toString(x));

							if (checkResult == true) {
								outHist.write("<tr><td width=\"200px\" align=\"center\" bgcolor=\"#ff6600\">" + new Date().toString() + "</td><td align=\"center\" bgcolor=\"#ff6600\">"+ "(" + mstr + " " + str + " " + ")" + " = " + " " + Integer.toString(x) + "</td></tr>");
								outHist.newLine();
								outHist.close();
							}
						}
					} else {
						String str = allValues.getText();
						String mstr = displayMini.getText();
						display.setText(Double.toString(result));
						allValues.setText(Double.toString(result));
						playAudioIfEnabled();
						if (mstr.contains("=")) {
							displayMini.setText("(" + mstr + str + "%" + ") = "
									+ Double.toString(result));
						} else {
							displayMini.setText("(" + mstr + str + "%" + ") = "
									+ Double.toString(result));
						}
					}

					result = Math.round(result * 100) / 100.0d;
					temporary[1] = result;
				} catch (NumberFormatException e) {
				} catch (IOException e) {
				}

			}
		}

		if (ae.getSource() == button[5]) { // Number FIVE - 5
			handleNumberButton("5");
		}

		if (ae.getSource() == button[4]) { // Number FOUR - 4
			handleNumberButton("4");
		}

		if (ae.getSource() == button[6]) { // Number SIX - 6
			handleNumberButton("6");
		}

		if (ae.getSource() == button[8]) { // Number ONE - 1
			handleNumberButton("1");
		}

		if (ae.getSource() == button[9]) { // Number TWO - 2
			handleNumberButton("2");
		}

		if (ae.getSource() == button[10]) { // Number THREE - 3
			handleNumberButton("3");
		}

		if (ae.getSource() == button[12]) { // DOT / dot button
			String str = display.getText();
			if (!str.contains(".")) {
				if (str.isEmpty()) {
					str = "0";
				}
				display.setText(str + ".");
				allValues.setText(str + ".");
				playAudioIfEnabled();
			}
		}

		if (ae.getSource() == button[14]) {
			clear(); 
			button[17].requestFocus();
		}
			

		if (ae.getSource() == button[15])
			getSqrt();

		if (ae.getSource() == button[16])
			getPosNeg();

		if (ae.getSource() == button[17]) { // Equal button =
			
			if (checkResult == true){
				
			} else {

					if (function[0] == true || function[1] == true || function[2] == true || function[3] == true) {
						checkResult = true;
						getResult();
						playAudioIfEnabled();
					}
			}
		}

		if (ae.getSource() == button[18]) { // Number ZERO - 0
			handleNumberButton("0");
		}

		if (ae.getSource() == button[21]) { // Power of 2
			if (allValues.getText().isEmpty()) {

			} else {
				getPower();
				}
			}
		
		// Power of 3 END

		if (ae.getSource() == button[22]) { // Number of Pi - 3.14159
			if (allValues.getText().isEmpty()) {
				display.setText("3.14159265359");
				playAudioIfEnabled();
				allValues.setText(display.getText());
			} else {
				display.setText("");
				if (function[0] == false & function[1] == false
						&& function[2] == false && function[3] == false) {
					displayMini.setText("");
				}
				display.setText(display.getText() + "3.14159265359");
				allValues.setText(display.getText());
				playAudioIfEnabled();
			}
		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] arguments) throws IOException {
		OrangeCalc c = new OrangeCalc();
	}
}