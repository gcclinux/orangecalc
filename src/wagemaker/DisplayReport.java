package wagemaker;

/******************************************************************************************************
 * @author by Ricardo Wagemaker (["java"] + "@" + "wagemaker.co.uk") 2013-2015
 * @version 1.5.3
 * @since   2013 - 2015
 ******************************************************************************************************/

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


class DisplayReport
		extends 	JFrame
		implements	HyperlinkListener
{
	private static final long serialVersionUID = 1L;

	private	JEditorPane	html;
	
	static JMenuBar mb = new JMenuBar(); // Menu bar
	static JMenu mnuFile = new JMenu("File"); // File Entry on Menu bar
	public JMenuItem mnuItemPrint = new JMenuItem("Print");
	
    private void loadFrameIcon() {
        URL imgUrl = null;
        ImageIcon imgIcon = null;
        
        imgUrl = DisplayReport.class.getResource("/images/orange.png");
        imgIcon = new ImageIcon(imgUrl);
        Image img = imgIcon.getImage();
        this.setIconImage(img);
    }
	
	public DisplayReport() throws IOException
	{
		
		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		// Determine the new location of the window
		int h = this.getSize().height;
		int y = (dim.height - h) / (2);
		
		setTitle(CalcProperties.Title);
		setSize( 810, y );
		setBackground(CalcProperties.DARKGREY);
		setLocationRelativeTo(null);
		loadFrameIcon();
		
		File filename = CalcProperties.historyFile();

		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel, BorderLayout.CENTER );
		
		//setJMenuBar(mb);
		//mb.add(mnuFile);
		//mnuFile.add(mnuItemPrint);
		
		//mnuFile.setMnemonic(KeyEvent.VK_F);
		
		//System.out.println(ScheduleTask.date);

    	try {
			URL url = filename.toPath().toUri().toURL();
		    html = new JEditorPane( url );
		    //html.setPreferredSize(new Dimension(800, 600));
		    html.setEditable( false );
		    JScrollPane scrollPane = new JScrollPane();
		    scrollPane.getViewport().add(html);
		    topPanel.add( scrollPane, BorderLayout.CENTER );
		    html.addHyperlinkListener( this );
		}
		catch( MalformedURLException e )
		{
		    System.out.println( "Malformed URL: " + e );
		}
		catch( IOException e )
		{
		    System.out.println( "IOException: " + e );
		}

    	
    	mnuItemPrint.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent event) {
    			try {

    				PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
    			    aset.add(OrientationRequested.LANDSCAPE);		
    			    html.print(null, null, true, null, aset, true);   				
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	});
    	
	}
	

    public void hyperlinkUpdate( HyperlinkEvent event )
    {
		if( event.getEventType() == HyperlinkEvent.EventType.ACTIVATED )
		{
			// Load some cursors
			Cursor cursor = html.getCursor();
			Cursor waitCursor = Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR );
			html.setCursor( waitCursor );

			// Handle the hyperlink change
			SwingUtilities.invokeLater( new PageLoader( html,event.getURL(), cursor ) );
		}
    }

	public static void main( String args[] ) throws IOException
	{
		// Create an instance of the test application
		DisplayReport mainFrame	= new DisplayReport();
		mainFrame.setVisible( true );
	}
}

