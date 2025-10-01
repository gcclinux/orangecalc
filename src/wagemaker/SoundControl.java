package wagemaker;

/******************************************************************************************************
 * @author by Ricardo Wagemaker (["java"] + "@" + "wagemaker.co.uk") 2013-2015
 * @version 1.5.3
 * @since   2013 - 2015
 ******************************************************************************************************/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class SoundControl extends Thread{

	
	public static void setVisible() throws IOException{

			String workingDir = CalcProperties.workingDir(); 			// ZZ
			File file = new File(workingDir);
			Properties p = new Properties(System.getProperties());
			
			FileInputStream propFile = new FileInputStream(file);
			
			{
				p.load(propFile);
				System.setProperties(p);
				CalcProperties.setDesign();
			}
			
			UIManager.put("OptionPane.buttonOrientation", 0);		
			UIManager.put("OptionPane.buttonFont", FontTheme.size15b);
			UIManager.put("OptionPane.messageFont", FontTheme.size15p);
			UIManager.put("OptionPane.messageForeground",CalcProperties.BLACK);
			UIManager.put("Panel.background",  CalcProperties.ORANGE);
			UIManager.put("OptionPane.background", CalcProperties.ORANGE);
			
			int n = JOptionPane.showOptionDialog(null, 
			        "  Calculator Sound Settings ", "Sound Setting", 
			        JOptionPane.OK_CANCEL_OPTION, 
			        JOptionPane.PLAIN_MESSAGE, 
			        null, 
			        new String[]{" Disable ", " Enable "}, // this is the array
			        "default");

	        if(n == JOptionPane.YES_OPTION){
	        	
	            JOptionPane.showMessageDialog(null, "  Audio now Disabled ", null, JOptionPane.PLAIN_MESSAGE);
	            p.remove("audio.status");
				p.put("audio.status", "false");
				System.setProperties(p);
				//String Audio = System.getProperty("audio.status");
				//System.out.println("String Audio SET TRUE = System.getProperty = " + Audio);
	        }
	        else {
	        	
	        	
	            JOptionPane.showMessageDialog(null, "   Audio now Enabled ", null, JOptionPane.PLAIN_MESSAGE);
	            p.remove("audio.status");
				p.put("audio.status", "true");
				System.setProperties(p);
				//String Audio2 = System.getProperty("audio.status"); // ZZ
				//System.out.println("String Audio SET FALSE = System.getProperty = " + Audio2);
	        }
			FileOutputStream out = new FileOutputStream(file);
			p.store(out, "");
	    }
	}
