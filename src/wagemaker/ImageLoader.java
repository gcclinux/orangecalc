package wagemaker;

/******************************************************************************************************
 * @author by Ricardo Wagemaker (["java"] + "@" + "wagemaker.co.uk") 2013-2015
 * @version 1.5.3
 * @since   2013 - 2015
 ******************************************************************************************************/

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ImageLoader extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image img;

	public ImageLoader(String img) {
  	this(new ImageIcon(img).getImage());
	}

	 public ImageLoader(Image img) {
  		this.img = img;
  		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
  		setSize(size);
  		setLayout(null);
	}

	public void paintComponent(Graphics g) {
  		g.drawImage(img, 0, 0, null);
	}

}