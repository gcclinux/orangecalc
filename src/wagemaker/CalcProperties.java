package wagemaker;

/******************************************************************************************************
 * @author by Ricardo Wagemaker (["java"] + "@" + "wagemaker.co.uk") 2013-2015
 * @version 1.5.3
 * @since   2013 - 2015
 ******************************************************************************************************/

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.UIManager;

public class CalcProperties {
	
	static String Separate = " - ";
	static String Name = "RW Collection";
	static String Developer = "Ricardo Wagemaker";

	static String Version = "1.5.6";
	static String Title = "Orange Calculator";
	static String configFile = "calculatorOrange.conf";
	static String homeDir = System.getProperty("user.home");
	public static String osName = System.getProperty("os.name").toLowerCase();
	static String history = "calcHistory.html";
	static String oldhistory = "CalculatorHistory.html";
	static String workingDir = null;
	static File historyFile = null;
	static File historyOld = null;
	static String versionFile = "https://github.com/gcclinux/orangecalc";

	static Map<String, Color> colourmap = new HashMap<String, Color>();

	static Color ORANGE = new Color(255, 105, 23);
	static Color PINK = new Color(158, 45, 205);
	static Color BLUE = new Color(49, 156, 231);
	static Color LIME = new Color(51, 255, 51);
	static Color BLACK = new Color(0, 0, 0);
	static Color DARKGREY = new Color(94, 94, 94);
	static Color LIGHTGREY = new Color(192, 192, 192);
	static Color WHITE = new Color(255, 255, 255);

	static Color Theme_ORANGE; // Create ThemeA Class
	static Color Theme_B; // Create ThemeB class
	static Color Theme_C; // Create ThemeC class

	static String AudioTrue;

	public static String workingDir() throws IOException {
		if (osName.indexOf("nux") >= 0 || osName.indexOf("nix") >= 0) {
			workingDir = (homeDir + "/" + ".config" + "/" + configFile);
		} else if (osName.equals("windows xp")) {
			workingDir = (homeDir + "\\Application Data\\" + configFile);
		} else if (osName.equals("windows 7")) {
			workingDir = (homeDir + "\\Application Data\\" + configFile);
		} else if (osName.equals("windows 8")) {
			workingDir = (homeDir + "\\AppData\\" + "Roaming\\" + configFile);
		} else if (osName.equals("windows 8.1")) {
			workingDir = (homeDir + "\\AppData\\" + "Roaming\\" + configFile);
		} else if (osName.indexOf("mac") >= 0) {
			workingDir = (homeDir + "/Library/" + configFile);
		} else {
			workingDir = (homeDir + "\\" + configFile);
		}
		return workingDir;
	}

	public static File historyFile() throws IOException {
		if (osName.indexOf("nux") >= 0 || osName.indexOf("nix") >= 0) {
			historyFile = new File(homeDir + "/" + ".config" + "/" + history);
		} else if (osName.equals("windows xp")) {
			historyFile = new File(homeDir + "\\Application Data\\" + history);
		} else if (osName.equals("windows 7")) {
			historyFile = new File(homeDir + "\\Application Data\\" + history);
		} else if (osName.equals("windows 8")) {
			historyFile = new File(homeDir + "\\AppData\\" + "Roaming\\" + history);
		} else if (osName.equals("windows 8.1")) {
			historyFile = new File(homeDir + "\\AppData\\" + "Roaming\\" + history);
		} else if (osName.indexOf("mac") >= 0) {
			historyFile = new File(homeDir + "/Library/" + history);
		} else {
			historyFile = new File(homeDir + "\\" + history);
		}
		return historyFile;
	}
	
	public static File historyOld() throws IOException {
		if (osName.indexOf("nux") >= 0 || osName.indexOf("nix") >= 0) {
			historyOld = new File(homeDir + "/" + ".config" + "/" + oldhistory);
		} else if (osName.equals("windows xp")) {
			historyOld = new File(homeDir + "\\Application Data\\" + oldhistory);
		} else if (osName.equals("windows 7")) {
			historyOld = new File(homeDir + "\\Application Data\\" + oldhistory);
		} else if (osName.equals("windows 8")) {
			historyOld = new File(homeDir + "\\AppData\\" + "Roaming\\" + oldhistory);
		} else if (osName.equals("windows 8.1")) {
			historyOld = new File(homeDir + "\\AppData\\" + "Roaming\\" + oldhistory);
		} else if (osName.indexOf("mac") >= 0) {
			historyOld = new File(homeDir + "/Library/" + oldhistory);
		} else {
			historyOld = new File(homeDir + "\\" + oldhistory);
		}
		return historyOld;
	}

	public static Color Theme_ORANGE() throws IOException {

		File file = new File(workingDir());
		Properties newProperties = new Properties(System.getProperties());

		colourmap.put("Orange", ORANGE);
		colourmap.put("Pink", PINK);
		colourmap.put("Blue", BLUE);
		colourmap.put("Lime", LIME);
		colourmap.put("Black", BLACK);
		colourmap.put("DarkGrey", DARKGREY);
		colourmap.put("LighGrey", LIGHTGREY);

		if (!file.exists()) {
			newProperties.put("theme.colour", "ORANGE");
			newProperties.put("button.colour", "DarkGrey");
			newProperties.put("button_c.colour", "ORANGE");
			newProperties.put("audio.status", "false");
			newProperties.put("mb.colour", "ORANGE");
			newProperties.put("mnuFile.colour", "ORANGE");
			newProperties.put("mnuEdit.colour", "ORANGE");
			newProperties.put("mnuHelp.colour", "ORANGE");
			newProperties.put("mnuItemQuit.colour", "ORANGE");
			newProperties.put("mnuItemAbout.colour", "ORANGE");
			newProperties.put("mnuItemHist.colour", "ORANGE");
			newProperties.put("mnuClearHist.colour", "ORANGE");
			newProperties.put("mnuSoundControl.colour", "ORANGE");
			newProperties.put("mnuItemSupport.colour", "ORANGE");
			newProperties.put("mnuItemTwitter.colour", "ORANGE");
			newProperties.put("mnuItemPayPal.colour", "ORANGE");
			newProperties.put("mnuCopyResult.colour", "ORANGE");
			newProperties.put("mnuItemBack.colour", "ORANGE");
			newProperties.put("mnuItemButtonStyle.colour", "ORANGE");
			newProperties.put("mnuItemOrange.colour", "ORANGE");
			newProperties.put("mnuItemDarkGray.colour", "ORANGE");
			newProperties.put("mnuStyle.colour", "ORANGE");
			newProperties.put("mnuExport.colour", "ORANGE");
			newProperties.put("mnuItemNoBoarder.colour", "ORANGE");
			newProperties.put("mnuItemBorderCompound.colour", "ORANGE");
			newProperties.put("mnuItemBorderLower.colour", "ORANGE");
			newProperties.put("mnuItemBorderRaised.colour", "ORANGE");
			newProperties.put("mnuItemLime.colour", "ORANGE");
			newProperties.put("mnuItemPlain.colour", "ORANGE");
			newProperties.put("row.colour", "ORANGE");
			newProperties.put("style.type", "compound");

			System.setProperties(newProperties);
			FileOutputStream out = new FileOutputStream(file);
			newProperties.store(out, "");

		} else {

			FileInputStream propFile = new FileInputStream(file);
			newProperties.load(propFile);

			if (System.getProperty("theme.colour") == null) {
				newProperties.put("theme.colour", "ORANGE");
				System.setProperties(newProperties);
			}
			if (System.getProperty("button.colour") == null) {
				newProperties.put("button.colour", "DarkGrey");
				System.setProperties(newProperties);
			}
			if (System.getProperty("button_c.colour") == null) {
				newProperties.put("button_c.colour", "ORANGE");
				System.setProperties(newProperties);
			}
			if (System.getProperty("audio.status") == null) {
				newProperties.put("audio.status", "true");
				System.setProperties(newProperties);
			}

			FileOutputStream out = new FileOutputStream(file);
			newProperties.store(out, "");

			Theme_ORANGE = colourmap.get(System.getProperty("theme.colour"));
		}

		return Theme_ORANGE;
	}

	public static Color Theme_B() throws IOException {

		File file = new File(workingDir());
		Properties newProperties = new Properties(System.getProperties());

		colourmap.put("Orange", ORANGE);
		colourmap.put("Pink", PINK);
		colourmap.put("Blue", BLUE);
		colourmap.put("Lime", LIME);
		colourmap.put("Black", BLACK);
		colourmap.put("DarkGrey", DARKGREY);
		colourmap.put("LighGrey", LIGHTGREY);

		FileInputStream propFile = new FileInputStream(file);
		newProperties.load(propFile);

		Theme_B = colourmap.get(System.getProperty("button.colour"));

		return Theme_B;
	}

	public static Color Theme_C() throws IOException {

		File file = new File(workingDir());
		Properties newProperties = new Properties(System.getProperties());

		colourmap.put("Orange", ORANGE);
		colourmap.put("Pink", PINK);
		colourmap.put("Blue", BLUE);
		colourmap.put("Lime", LIME);
		colourmap.put("Black", BLACK);
		colourmap.put("DarkGrey", DARKGREY);
		colourmap.put("LighGrey", LIGHTGREY);

		FileInputStream propFile = new FileInputStream(file);
		newProperties.load(propFile);

		Theme_C = colourmap.get(System.getProperty("button_c.colour"));

		return Theme_C;
	}

	// Set Look and Feel and Environment settings

	public static void setDesign() {
		if (osName.indexOf("nux") >= 0 || osName.indexOf("nix") >= 0) {
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.basic");
			} catch (Exception e) {
			}
		} else if (osName.indexOf("win") >= 0) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e) {
			}
		} else if (osName.indexOf("mac") >= 0) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			} catch (Exception e) {
			}
		} 
	} // END Look and Feel and Environment settings 

	public static String AudioTrue() throws IOException {

		File file = new File(workingDir());
		Properties newProperties = new Properties(System.getProperties());

		FileInputStream propFile = new FileInputStream(file);
		newProperties.load(propFile);

		AudioTrue = System.getProperty("audio.status");
		// System.out.println("Getting audio.status now");
		return AudioTrue;
	}
}
