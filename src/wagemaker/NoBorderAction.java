package wagemaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;

/**
 * Externalized action listener for the "No Border" menu item.
 */
public class NoBorderAction implements ActionListener {
    private final JButton[] button;

    public NoBorderAction(JButton[] button) {
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        CalcProperties.setDesign();

        // CREATE CONFIG ######################################
        File file = null;
        try {
            file = new File(CalcProperties.workingDir());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties prop = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            prop.load(input);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Properties newProperties = new Properties(System.getProperties());

        newProperties.put("theme.colour", prop.getProperty("theme.colour"));
        newProperties.put("button.colour", prop.getProperty("button.colour"));
        newProperties.put("button_c.colour", prop.getProperty("button_c.colour"));
        newProperties.put("audio.status", prop.getProperty("audio.status"));
        newProperties.put("mb.colour", prop.getProperty("mb.colour"));
        newProperties.put("mnuFile.colour", prop.getProperty("mnuFile.colour"));
        newProperties.put("mnuEdit.colour", prop.getProperty("mnuEdit.colour"));
        newProperties.put("mnuHelp.colour", prop.getProperty("mnuHelp.colour"));
        newProperties.put("mnuItemQuit.colour", prop.getProperty("mnuItemQuit.colour"));
        newProperties.put("mnuItemAbout.colour", prop.getProperty("mnuItemAbout.colour"));
        newProperties.put("mnuItemHist.colour", prop.getProperty("mnuItemHist.colour"));
        newProperties.put("mnuItemHistOld.colour", prop.getProperty("mnuItemHistOld.colour"));
        newProperties.put("mnuClearHist.colour", prop.getProperty("mnuClearHist.colour"));
        newProperties.put("mnuSoundControl.colour", prop.getProperty("mnuSoundControl.colour"));
        newProperties.put("mnuItemSupport.colour", prop.getProperty("mnuItemSupport.colour"));
        newProperties.put("mnuItemTwitter.colour", prop.getProperty("mnuItemTwitter.colour"));
        newProperties.put("mnuItemPayPal.colour", prop.getProperty("mnuItemPayPal.colour"));
        newProperties.put("mnuCopyResult.colour", prop.getProperty("mnuCopyResult.colour"));
        newProperties.put("mnuItemBack.colour", prop.getProperty("mnuItemBack.colour"));
        newProperties.put("mnuItemButtonStyle.colour", prop.getProperty("mnuItemButtonStyle.colour"));
        newProperties.put("mnuItemOrange.colour", prop.getProperty("mnuItemOrange.colour"));
        newProperties.put("mnuItemPurple.colour", prop.getProperty("mnuItemPurple.colour"));

        newProperties.put("mnuStyle.colour", prop.getProperty("mnuStyle.colour"));
        newProperties.put("mnuExport.colour", prop.getProperty("mnuExport.colour"));
        newProperties.put("mnuItemNoBoarder.colour", prop.getProperty("mnuItemNoBoarder.colour"));
        newProperties.put("mnuItemBorderCompound.colour", prop.getProperty("mnuItemBorderCompound.colour"));
        newProperties.put("mnuItemBorderLower.colour", prop.getProperty("mnuItemBorderLower.colour"));
        newProperties.put("mnuItemBorderRaised.colour", prop.getProperty("mnuItemBorderRaised.colour"));
        newProperties.put("mnuItemLime.colour", prop.getProperty("mnuItemLime.colour"));
        newProperties.put("mnuItemPlain.colour", prop.getProperty("mnuItemPlain.colour"));
        newProperties.put("row.colour", prop.getProperty("row.colour"));
        newProperties.put("style.type", "null");

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

        // END CONFIG   ######################################
        for (int i = 0; i < 24; i++) {
            button[i].setBorder(null);
        }
    }
}
