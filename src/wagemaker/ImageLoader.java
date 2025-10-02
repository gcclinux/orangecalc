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
			// prefer to respect container sizing so caller can setPreferredSize
			Dimension size = new Dimension(Math.max(1, img.getWidth(null)), Math.max(1, img.getHeight(null)));
			setPreferredSize(size);
			setLayout(null);
			// keep panel transparent so it doesn't draw an opaque background behind the image
			setOpaque(false);
		}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw the image scaled to this component's current size so preferredSize controls visual size
		int w = getWidth();
		int h = getHeight();
		if (w > 0 && h > 0) {
			g.drawImage(img, 0, 0, w, h, null);
		}
	}

}