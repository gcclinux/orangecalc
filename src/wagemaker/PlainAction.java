package wagemaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Externalized action listener for the "Plain" style menu item.
 */
public class PlainAction implements ActionListener {
    private final OrangeCalc owner;
    private final JButton[] button;
    private final JPanel[] row;
    private final Border compound;
    private final Border loweredbevel;
    private final Border raisedbevel;
    public PlainAction(OrangeCalc owner, JButton[] button, JPanel[] row, Border compound, Border loweredbevel, Border raisedbevel) {
        this.owner = owner;
        this.button = button;
        this.row = row;
        this.compound = compound;
        this.loweredbevel = loweredbevel;
        this.raisedbevel = raisedbevel;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Reset menu foregrounds
    // static menu components accessed statically
    OrangeCalc.mb.setForeground(null);
    OrangeCalc.mnuFile.setForeground(null);
    OrangeCalc.mnuEdit.setForeground(null);
    OrangeCalc.mnuStyle.setForeground(null);
    OrangeCalc.mnuHelp.setForeground(null);
    // instance menu items accessed via owner
    owner.mnuItemQuit.setForeground(null);
    owner.mnuItemAbout.setForeground(null);
    owner.mnuItemHist.setForeground(null);
    owner.mnuClearHist.setForeground(null);
    owner.mnuSoundControl.setForeground(null);
    owner.mnuItemSupport.setForeground(null);

    owner.mnuCopyResult.setForeground(null);
    owner.mnuItemBack.setForeground(null);
    owner.mnuItemButtonStyle.setForeground(null);
    owner.mnuItemOrange.setForeground(null);

    OrangeCalc.mnuStyle.setForeground(null);
    owner.mnuExport.setForeground(null);
    owner.mnuItemNoBoarder.setForeground(null);
    owner.mnuItemBorderCompound.setForeground(null);
    owner.mnuItemBorderLower.setForeground(null);
    owner.mnuItemBorderRaised.setForeground(null);
    owner.mnuItemLime.setForeground(null);
    owner.mnuItemPlain.setForeground(null);

        for (int i = 0; i < 5; i++) {
            row[i].setBackground(null);
        }

        for (int i = 0; i < 24; i++) {
            if (button[0].getBorder() == compound) {
                button[i].setBorder((Border) compound);
                button[20].setBorder(null);
            } else if (button[0].getBorder() == loweredbevel) {
                button[i].setBorder((Border) loweredbevel);
                button[20].setBorder(null);
            } else if (button[0].getBorder() == raisedbevel) {
                button[i].setBorder((Border) raisedbevel);
                button[20].setBorder(null);
            } else if (button[0].getBorder() == null) {
                button[i].setBorder((Border) null);
                button[20].setBorder(null);
            }
            button[i].setForeground(CalcProperties.BLACK);
        }

        for (int i = 0; i < 24; i++) {
            if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6
                    || i == 8 || i == 9 || i == 10 || i == 18) {
                button[i].setBackground(CalcProperties.DARKGREY);
            } else if (i == 3 || i == 7 || i == 11 || i == 12 || i == 13
                    || i == 14 || i == 15 || i == 16 || i == 17 || i == 19
                    || i == 20 || i == 22 || i == 21 || i == 23) {
                button[i].setBackground(null);
            }
        }

        File file = null;
        try {
            file = new File(CalcProperties.workingDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties newProperties = new Properties(System.getProperties());

        newProperties.put("theme.colour", "null");
        newProperties.put("button.colour", "null");
        newProperties.put("button_c.colour", "null");
        newProperties.put("audio.status", "false");
        newProperties.put("mb.colour", "null");
        newProperties.put("mnuFile.colour", "null");
        newProperties.put("mnuEdit.colour", "null");
        newProperties.put("mnuHelp.colour", "null");
        newProperties.put("mnuItemQuit.colour", "null");
        newProperties.put("mnuItemAbout.colour", "null");
        newProperties.put("mnuItemHist.colour", "null");
        newProperties.put("mnuItemHistOld.colour", "null");
        newProperties.put("mnuClearHist.colour", "null");
        newProperties.put("mnuSoundControl.colour", "null");
        newProperties.put("mnuItemSupport.colour", "null");
        newProperties.put("mnuItemTwitter.colour", "null");
        newProperties.put("mnuItemPayPal.colour", "null");
        newProperties.put("mnuCopyResult.colour", "null");
        newProperties.put("mnuItemBack.colour", "null");
        newProperties.put("mnuItemButtonStyle.colour", "null");
        newProperties.put("mnuItemOrange.colour", "null");

        newProperties.put("mnuStyle.colour", "null");
        newProperties.put("mnuExport.colour", "null");
        newProperties.put("mnuItemNoBoarder.colour", "null");
        newProperties.put("mnuItemBorderCompound.colour", "null");
        newProperties.put("mnuItemBorderLower.colour", "null");
        newProperties.put("mnuItemBorderRaised.colour", "null");
        newProperties.put("mnuItemLime.colour", "null");
        newProperties.put("mnuItemPlain.colour", "null");
        newProperties.put("row.colour", "null");

        for (int i = 0; i < 1; i++) {
            if (button[0].getBorder() == compound) {
                newProperties.put("style.type", "compound");
            } else if (button[0].getBorder() == loweredbevel) {
                newProperties.put("style.type", "loweredbevel");
            } else if (button[0].getBorder() == raisedbevel) {
                newProperties.put("style.type", "raisedbevel");
                button[20].setBorder(null);
            } else if (button[0].getBorder() == null) {
                newProperties.put("style.type", "null");
                button[20].setBorder(null);
            }
        }

        System.setProperties(newProperties);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            newProperties.store(out, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
