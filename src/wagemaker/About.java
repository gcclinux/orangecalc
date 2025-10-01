package wagemaker;

/******************************************************************************************************
 * @author by Ricardo Wagemaker (["java"] + "@" + "wagemaker.co.uk") 2013-2015
 * @version 1.0.2
 * @since   2013 - 2015
 ******************************************************************************************************/

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class About {
	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
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
	        label3.setLocation(0,110);
	        
			JLabel label4 = new JLabel(CalcProperties.Developer, JLabel.CENTER);
			label4.setFont(FontTheme.size15i);
			label4.setForeground(CalcProperties.WHITE);
	        label4.setSize(300, 30);
	        label4.setLocation(0, 140);
	        
	        JLabel support = new JLabel("License & Changelog", JLabel.CENTER);
	        support.setFont(FontTheme.size17p);
	        support.setForeground(CalcProperties.WHITE);
	        support.setSize(300, 30);
	        support.setLocation(0, 220);
 
	        Font font = support.getFont();
	        Map attributes = font.getAttributes();
	        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	        support.setFont(font.deriveFont(attributes));
	        
	        support.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        support.addMouseListener(new MouseAdapter() {
	          public void mouseClicked(MouseEvent e) {
	             if (e.getClickCount() > 0) {
	                 if (Desktop.isDesktopSupported()) {
	                       Desktop desktop = Desktop.getDesktop();
	                       
	                       
	                       try {
	                           //URI uri = new URI("file:/opt/smalltextpad/license.html");
	                    	   //URI uri = new URI("License.html");
	                    	   URI uri = new URI("http://www.gcclinux.co.uk/downloads/license/orangecalculator-license.html");
	                           desktop.browse(uri);
	                       } catch (IOException ex) {
	                           ex.printStackTrace();
	                       } catch (URISyntaxException ex) {
	                           ex.printStackTrace();
	                       }
	                       
	                       
	               }
	             }
	          }
	       });
	       
			 
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
		About.add(support);
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		About.setLocation(dim.width/2-About.getSize().width/2, dim.height/2-About.getSize().height/2);
		
		// Adding Application ICON
		 
		 ImageIcon ImageIcon = new ImageIcon(CalculatorOrangeLite.class.getResource("/images/gcclinux.png"));
		 Image Logo = ImageIcon.getImage();
		 About.setIconImage(Logo);
 
	}
	
}