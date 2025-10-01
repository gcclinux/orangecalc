package wagemaker;

/******************************************************************************************************
 * @author by Ricardo Wagemaker (["java"] + "@" + "wagemaker.co.uk") 2013-2015
 * @version 1.5.3
 * @since   2013 - 2015
 ******************************************************************************************************/

import java.io.IOException; 
import javax.sound.sampled.AudioFormat; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.DataLine; 
import javax.sound.sampled.FloatControl; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.SourceDataLine; 
import javax.sound.sampled.UnsupportedAudioFileException; 
import java.net.URL; 

public class Sound extends Thread { 
	 
    private String filename;
 
    private Position curPosition;
 
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb 
 
    enum Position { 
        LEFT, RIGHT, NORMAL
	    };
 
    public Sound(String wavfile) { 
        filename = wavfile;
        curPosition = Position.NORMAL;
    } 
 
    public Sound(String wavfile, Position p) { 
        filename = wavfile;
        curPosition = p;
    } 
 
    public void run() { 
 
	String fullFileName = "/audio/" + filename;

	URL url = null;
	try {
	    url = getClass().getResource(fullFileName);
	} catch (Exception e) {
            e.printStackTrace();
            return;
	}

	if (url==null) {
	    System.err.println("Error: " + fullFileName + " could not be found through getClass().getResource() (i.e. in the JAR file or classpath)");
	    return;
	}
	
        AudioInputStream audioInputStream = null;
        try { 
            audioInputStream = AudioSystem.getAudioInputStream(url);
        } catch (UnsupportedAudioFileException e1) { 
            e1.printStackTrace();
            return;
        } catch (IOException e1) { 
            e1.printStackTrace();
            return;
        } 
 
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
        try { 
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) { 
            e.printStackTrace();
            return;
        } catch (Exception e) { 
            e.printStackTrace();
            return;
        } 
 
        if (auline.isControlSupported(FloatControl.Type.PAN)) { 
            FloatControl pan = (FloatControl) auline
		.getControl(FloatControl.Type.PAN);
            if (curPosition == Position.RIGHT) 
                pan.setValue(1.0f);
            else if (curPosition == Position.LEFT) 
                pan.setValue(-1.0f);
        } 
 
        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
 
        try { 
            while (nBytesRead != -1) { 
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0) 
                    auline.write(abData, 0, nBytesRead);
            } 
        } catch (IOException e) { 
            e.printStackTrace();
            return;
        } finally { 
            auline.drain();
            auline.close();
        } 
 
    } 
} 
