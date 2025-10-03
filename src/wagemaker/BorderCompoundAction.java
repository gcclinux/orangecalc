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
import javax.swing.border.Border;

/**
 * Externalized action listener for the "Border Compound" menu item.
 */
public class BorderCompoundAction implements ActionListener {
    private final JButton[] button;
    private final Border compound;

    public BorderCompoundAction(JButton[] button, Border compound) {
        this.button = button;
        this.compound = compound;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        CalcProperties.setDesign();

        // CREATE/UPDATE CONFIG ######################################
        File file = null;
        try {
            file = new File(CalcProperties.workingDir());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties prop = new Properties();
        FileInputStream input = null;
        try {
            if (file != null) {
                input = new FileInputStream(file);
                prop.load(input);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
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
        newProperties.put("style.type", "compound");

        System.setProperties(newProperties);
        FileOutputStream out = null;
        try {
            if (file != null) {
                out = new FileOutputStream(file);
                newProperties.store(out, "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // END CONFIG   ######################################
        for (int i = 0; i < 24; i++) {
            if (button[i].getBackground() == CalcProperties.BLACK) {
                if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6 || i == 8 || i == 9 || i == 10 || i == 18) {
                    button[i].setBorder((Border) compound);
                } else if (i == 3 || i == 7 || i == 11 || i == 12 || i == 13
                        || i == 14 || i == 15 || i == 16 || i == 17 || i == 19
                        || i == 20 || i == 22 || i == 21 || i == 23) {
                    button[i].setBorder(null);
                }
            } else {
                button[i].setBorder((Border) compound);
            }
        }
        button[20].setBorder(null);
    }
}
