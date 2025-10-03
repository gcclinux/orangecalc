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
 * Action listener extracted from OrangeCalc for the Lime theme.
 */
public class LimeAction implements ActionListener {
    private final OrangeCalc owner;
    private final JButton[] button;
    private final JPanel[] row;
    private final Border compound;
    private final Border loweredbevel;
    private final Border raisedbevel;

    public LimeAction(OrangeCalc owner, JButton[] button, JPanel[] row, Border compound, Border loweredbevel, Border raisedbevel) {
        this.owner = owner;
        this.button = button;
        this.row = row;
        this.compound = compound;
        this.loweredbevel = loweredbevel;
        this.raisedbevel = raisedbevel;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        CalcProperties.setDesign();

        OrangeCalc.mb.setForeground(CalcProperties.DARKGREY);
        OrangeCalc.mnuFile.setForeground(CalcProperties.DARKGREY);
        OrangeCalc.mnuEdit.setForeground(CalcProperties.DARKGREY);
        OrangeCalc.mnuStyle.setForeground(CalcProperties.DARKGREY);
        OrangeCalc.mnuHelp.setForeground(CalcProperties.DARKGREY);

        owner.mnuItemQuit.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemAbout.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemHist.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemHistOld.setForeground(CalcProperties.DARKGREY);
        owner.mnuClearHist.setForeground(CalcProperties.DARKGREY);
        owner.mnuSoundControl.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemSupport.setForeground(CalcProperties.DARKGREY);
        owner.mnuCopyResult.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemBack.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemButtonStyle.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemOrange.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemPurple.setForeground(CalcProperties.DARKGREY);

        OrangeCalc.mnuStyle.setForeground(CalcProperties.DARKGREY);
        owner.mnuExport.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemNoBoarder.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemBorderCompound.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemBorderLower.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemBorderRaised.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemLime.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemPlain.setForeground(CalcProperties.DARKGREY);

        for (int i = 0; i < 5; i++) {
            row[i].setBackground(CalcProperties.LIME);
        }

        for (int i = 0; i < 24; i++) {
            if (button[0].getBorder() == compound) {
                button[i].setBorder(compound);
                if (button.length > 20) button[20].setBorder(null);
            } else if (button[0].getBorder() == loweredbevel) {
                button[i].setBorder(loweredbevel);
                if (button.length > 20) button[20].setBorder(null);
            } else if (button[0].getBorder() == raisedbevel) {
                button[i].setBorder(raisedbevel);
                if (button.length > 20) button[20].setBorder(null);
            }

            if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6 || i == 8 || i == 9 || i == 10 || i == 18) {
                button[i].setBackground(CalcProperties.DARKGREY);
            } else {
                button[i].setBackground(CalcProperties.LIME);
                button[i].setForeground(CalcProperties.BLACK);
            }
        }

        File file = null;
        try {
            file = new File(CalcProperties.workingDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties newProperties = new Properties(System.getProperties());

        newProperties.put("theme.colour", "LIME");
        newProperties.put("button.colour", "DarkGrey");
        newProperties.put("button_c.colour", "LIME");
        newProperties.put("audio.status", "false");
        newProperties.put("mb.colour", "LIME");
        newProperties.put("mnuFile.colour", "DARKGREY");
        newProperties.put("mnuEdit.colour", "DARKGREY");
        newProperties.put("mnuHelp.colour", "DARKGREY");
        newProperties.put("mnuItemQuit.colour", "DARKGREY");
        newProperties.put("mnuItemAbout.colour", "DARKGREY");
        newProperties.put("mnuItemHist.colour", "DARKGREY");
        newProperties.put("mnuItemHistOld.colour", "DARKGREY");
        newProperties.put("mnuClearHist.colour", "DARKGREY");
        newProperties.put("mnuSoundControl.colour", "DARKGREY");
        newProperties.put("mnuItemSupport.colour", "DARKGREY");
        newProperties.put("mnuItemTwitter.colour", "DARKGREY");
        newProperties.put("mnuItemPayPal.colour", "DARKGREY");
        newProperties.put("mnuCopyResult.colour", "DARKGREY");
        newProperties.put("mnuItemBack.colour", "DARKGREY");
        newProperties.put("mnuItemButtonStyle.colour", "DARKGREY");
        newProperties.put("mnuItemOrange.colour", "DARKGREY");
        newProperties.put("mnuItemPurple.colour", "DARKGREY");

        newProperties.put("mnuStyle.colour", "DARKGREY");
        newProperties.put("mnuExport.colour", "DARKGREY");
        newProperties.put("mnuItemNoBoarder.colour", "DARKGREY");
        newProperties.put("mnuItemBorderCompound.colour", "DARKGREY");
        newProperties.put("mnuItemBorderLower.colour", "DARKGREY");
        newProperties.put("mnuItemBorderRaised.colour", "DARKGREY");
        newProperties.put("mnuItemLime.colour", "DARKGREY");
        newProperties.put("mnuItemPlain.colour", "DARKGREY");
        newProperties.put("row.colour", "LIME");

        for (int i = 0; i < 1; i++) {
            if (button[0].getBorder() == compound) {
                newProperties.put("style.type", "compound");
            } else if (button[0].getBorder() == loweredbevel) {
                newProperties.put("style.type", "loweredbevel");
            } else if (button[0].getBorder() == raisedbevel) {
                newProperties.put("style.type", "raisedbevel");
                if (button.length > 20) button[20].setBorder(null);
            } else if (button[0].getBorder() == null) {
                newProperties.put("style.type", "null");
                if (button.length > 20) button[20].setBorder(null);
            }
        }

        System.setProperties(newProperties);
        try (FileOutputStream out = new FileOutputStream(file)) {
            newProperties.store(out, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}