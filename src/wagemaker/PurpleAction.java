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

public class PurpleAction implements ActionListener {
    private final OrangeCalc owner;
    private final JButton[] button;
    private final JPanel[] row;
    private final Border compound;
    private final Border loweredbevel;
    private final Border raisedbevel;

    public PurpleAction(OrangeCalc owner, JButton[] button, JPanel[] row, Border compound, Border loweredbevel, Border raisedbevel) {
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
        OrangeCalc.mb.setForeground(CalcProperties.PURPLE);
        OrangeCalc.mnuFile.setForeground(CalcProperties.PURPLE);
        OrangeCalc.mnuEdit.setForeground(CalcProperties.PURPLE);
        OrangeCalc.mnuStyle.setForeground(CalcProperties.PURPLE);
        OrangeCalc.mnuHelp.setForeground(CalcProperties.PURPLE);
        owner.mnuItemQuit.setForeground(CalcProperties.PURPLE);
        owner.mnuItemAbout.setForeground(CalcProperties.PURPLE);
        owner.mnuItemHist.setForeground(CalcProperties.PURPLE);
        owner.mnuItemHistOld.setForeground(CalcProperties.PURPLE);
        owner.mnuClearHist.setForeground(CalcProperties.PURPLE);
        owner.mnuSoundControl.setForeground(CalcProperties.PURPLE);
        owner.mnuItemSupport.setForeground(CalcProperties.PURPLE);
        owner.mnuCopyResult.setForeground(CalcProperties.PURPLE);
        owner.mnuItemBack.setForeground(CalcProperties.PURPLE);
        owner.mnuItemButtonStyle.setForeground(CalcProperties.PURPLE);
        owner.mnuItemOrange.setForeground(CalcProperties.PURPLE);
        owner.mnuItemPurple.setForeground(CalcProperties.PURPLE);

        OrangeCalc.mnuStyle.setForeground(CalcProperties.PURPLE);
        owner.mnuExport.setForeground(CalcProperties.PURPLE);
        owner.mnuItemNoBoarder.setForeground(CalcProperties.PURPLE);
        owner.mnuItemBorderCompound.setForeground(CalcProperties.PURPLE);
        owner.mnuItemBorderLower.setForeground(CalcProperties.PURPLE);
        owner.mnuItemBorderRaised.setForeground(CalcProperties.PURPLE);
        owner.mnuItemLime.setForeground(CalcProperties.PURPLE);
        owner.mnuItemPlain.setForeground(CalcProperties.PURPLE);

        for (int i = 0; i < 5; i++) {
            row[i].setBackground(CalcProperties.PURPLE);
            button[i].setForeground(CalcProperties.PURPLE);
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
            if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6
                    || i == 8 || i == 9 || i == 10 || i == 18) {
                button[i].setBackground(CalcProperties.PURPLE);
            } else if (i == 3 || i == 7 || i == 11 || i == 12 || i == 13
                    || i == 14 || i == 15 || i == 16 || i == 17 || i == 19
                    || i == 20 || i == 22 || i == 21 || i == 23) {
                button[i].setBackground(CalcProperties.PURPLE);
                button[i].setForeground(CalcProperties.PURPLE);
            }
        }

        File file = null;
        try {
            file = new File(CalcProperties.workingDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties newProperties = new Properties(System.getProperties());

        newProperties.put("theme.colour", "PURPLE");
        newProperties.put("button.colour", "PURPLE");
        newProperties.put("button_c.colour", "PURPLE");
        newProperties.put("audio.status", "false");
        newProperties.put("mb.colour", "PURPLE");
        newProperties.put("mnuFile.colour", "PURPLE");
        newProperties.put("mnuEdit.colour", "PURPLE");
        newProperties.put("mnuHelp.colour", "PURPLE");
        newProperties.put("mnuItemQuit.colour", "PURPLE");
        newProperties.put("mnuItemAbout.colour", "PURPLE");
        newProperties.put("mnuItemHist.colour", "PURPLE");
        newProperties.put("mnuItemHistOld.colour", "PURPLE");
        newProperties.put("mnuClearHist.colour", "PURPLE");
        newProperties.put("mnuSoundControl.colour", "PURPLE");
        newProperties.put("mnuItemSupport.colour", "PURPLE");
        newProperties.put("mnuItemTwitter.colour", "PURPLE");
        newProperties.put("mnuItemPayPal.colour", "PURPLE");
        newProperties.put("mnuCopyResult.colour", "PURPLE");
        newProperties.put("mnuItemBack.colour", "PURPLE");
        newProperties.put("mnuItemButtonStyle.colour", "PURPLE");
        newProperties.put("mnuItemOrange.colour", "PURPLE");
        newProperties.put("mnuItemPurple.colour", "PURPLE");

        OrangeCalc.mnuStyle.setForeground(CalcProperties.PURPLE);
        newProperties.put("mnuStyle.colour", "PURPLE");
        newProperties.put("mnuExport.colour", "PURPLE");
        newProperties.put("mnuItemNoBoarder.colour", "PURPLE");
        newProperties.put("mnuItemBorderCompound.colour", "PURPLE");
        newProperties.put("mnuItemBorderLower.colour", "PURPLE");
        newProperties.put("mnuItemBorderRaised.colour", "PURPLE");
        newProperties.put("mnuItemLime.colour", "PURPLE");
        newProperties.put("mnuItemPlain.colour", "PURPLE");
        newProperties.put("row.colour", "PURPLE");

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