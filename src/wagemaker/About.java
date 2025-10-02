package wagemaker;

/******************************************************************************************************
 * @author by Ricardo Wagemaker (["java"] + "@" + "wagemaker.co.uk") 2013-2015
 * @version 1.0.2
 * @since   2013 - 2015
 ******************************************************************************************************/

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class About {
	@SuppressWarnings({ "static-access" })
	public static void main(String args[]) {
	
		UIManager UI=new UIManager();
		UI.put("OptionPane.buttonOrientation", 0);		
		UI.put("OptionPane.buttonFont", FontTheme.size15b);
		UI.put("OptionPane.messageFont", FontTheme.size15i);
		UI.put("OptionPane.messageForeground",CalcProperties.BLACK);
		UI.put("Panel.background",  CalcProperties.ORANGE);
		UI.put("OptionPane.background", CalcProperties.ORANGE);
		
		JLabel label = new JLabel(CalcProperties.Title, JLabel.CENTER);
			label.setFont(FontTheme.size20b);
			label.setForeground(CalcProperties.WHITE);
	        label.setSize(300, 30);
	        label.setLocation(0, 15);
	        
			JLabel label2 = new JLabel("Version"+CalcProperties.Separate+CalcProperties.Version, JLabel.CENTER);
			label2.setFont(FontTheme.size15i);
			label2.setForeground(CalcProperties.WHITE);
	        label2.setSize(300, 30);
	        label2.setLocation(0, 40);
	        
			JLabel label3 = new JLabel("Created by", JLabel.CENTER);
			label3.setFont(FontTheme.size20b);
			label3.setForeground(CalcProperties.WHITE);
	        label3.setSize(300, 30);
	        label3.setLocation(0,90);
	        
			JLabel label4 = new JLabel(CalcProperties.Developer, JLabel.CENTER);
			label4.setFont(FontTheme.size15i);
			label4.setForeground(CalcProperties.WHITE);
	        label4.setSize(300, 20);
	        label4.setLocation(0, 120);
	        
			// Short description about the multi-colour calculator (centered, wrapped)
			JLabel desc = new JLabel(
				"<html><div style='text-align:center;'>"
			  + "Linux Inspired, tiny, multi-colour calculator with Dark Gray, Orange, Lime and Plain styles, "
			  + "keyboard shortcuts, optional sounds, and history display — simple and fast." 
			  + "</div></html>",
				JLabel.CENTER);
			desc.setFont(FontTheme.size15p);
			desc.setForeground(CalcProperties.WHITE);
			desc.setSize(280, 90);
			desc.setLocation(5, 160);
	       
			 
		JFrame About = new JFrame();
		
		About.setLayout(null);
		About.setVisible(true);
		About.setTitle(CalcProperties.Title);
		About.setSize(300, 300);
		About.setResizable(false);
		
		About.add(label);
		About.add(label2);
		About.add(label3);
		About.add(label4);
	About.add(desc);
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		About.setLocation(dim.width/2-About.getSize().width/2, dim.height/2-About.getSize().height/2);
		
		// Adding Application ICON
		 
		 ImageIcon ImageIcon = new ImageIcon(CalculatorOrangeLite.class.getResource("/images/gcclinux.png"));
		 Image Logo = ImageIcon.getImage();
		 About.setIconImage(Logo);
 
	}
	
}