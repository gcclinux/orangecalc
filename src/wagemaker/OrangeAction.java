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

public class OrangeAction implements ActionListener {
    private final OrangeCalc owner;
    private final JButton[] button;
    private final JPanel[] row;
    private final Border compound;
    private final Border loweredbevel;
    private final Border raisedbevel;

    public OrangeAction(OrangeCalc owner, JButton[] button, JPanel[] row, Border compound, Border loweredbevel, Border raisedbevel) {
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
        OrangeCalc.mb.setForeground(CalcProperties.ORANGE);
        OrangeCalc.mnuFile.setForeground(CalcProperties.ORANGE);
        OrangeCalc.mnuEdit.setForeground(CalcProperties.ORANGE);
        OrangeCalc.mnuStyle.setForeground(CalcProperties.ORANGE);
        OrangeCalc.mnuHelp.setForeground(CalcProperties.ORANGE);
        owner.mnuItemQuit.setForeground(CalcProperties.ORANGE);
        owner.mnuItemAbout.setForeground(CalcProperties.ORANGE);
        owner.mnuItemHist.setForeground(CalcProperties.ORANGE);
        owner.mnuItemHistOld.setForeground(CalcProperties.ORANGE);
        owner.mnuClearHist.setForeground(CalcProperties.ORANGE);
        owner.mnuSoundControl.setForeground(CalcProperties.ORANGE);
        owner.mnuItemSupport.setForeground(CalcProperties.ORANGE);
        owner.mnuCopyResult.setForeground(CalcProperties.ORANGE);
        owner.mnuItemBack.setForeground(CalcProperties.ORANGE);
        owner.mnuItemButtonStyle.setForeground(CalcProperties.ORANGE);
        owner.mnuItemOrange.setForeground(CalcProperties.ORANGE);
        owner.mnuItemPurple.setForeground(CalcProperties.ORANGE);

        OrangeCalc.mnuStyle.setForeground(CalcProperties.ORANGE);
        owner.mnuExport.setForeground(CalcProperties.ORANGE);
        owner.mnuItemNoBoarder.setForeground(CalcProperties.ORANGE);
        owner.mnuItemBorderCompound.setForeground(CalcProperties.ORANGE);
        owner.mnuItemBorderLower.setForeground(CalcProperties.ORANGE);
        owner.mnuItemBorderRaised.setForeground(CalcProperties.ORANGE);
        owner.mnuItemLime.setForeground(CalcProperties.ORANGE);
        owner.mnuItemPlain.setForeground(CalcProperties.ORANGE);

        for (int i = 0; i < 5; i++) {
            row[i].setBackground(CalcProperties.ORANGE);
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
                button[i].setBackground(CalcProperties.DARKGREY);
            } else if (i == 3 || i == 7 || i == 11 || i == 12 || i == 13
                    || i == 14 || i == 15 || i == 16 || i == 17 || i == 19
                    || i == 20 || i == 22 || i == 21 || i == 23) {
                button[i].setBackground(CalcProperties.ORANGE);
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

        newProperties.put("theme.colour", "ORANGE");
        newProperties.put("button.colour", "DARKGREY");
        newProperties.put("button_c.colour", "ORANGE");
        newProperties.put("audio.status", "false");
        newProperties.put("mb.colour", "ORANGE");
        newProperties.put("mnuFile.colour", "ORANGE");
        newProperties.put("mnuEdit.colour", "ORANGE");
        newProperties.put("mnuHelp.colour", "ORANGE");
        newProperties.put("mnuItemQuit.colour", "ORANGE");
        newProperties.put("mnuItemAbout.colour", "ORANGE");
        newProperties.put("mnuItemHist.colour", "ORANGE");
        newProperties.put("mnuItemHistOld.colour", "ORANGE");
        newProperties.put("mnuClearHist.colour", "ORANGE");
        newProperties.put("mnuSoundControl.colour", "ORANGE");
        newProperties.put("mnuItemSupport.colour", "ORANGE");
        newProperties.put("mnuItemTwitter.colour", "ORANGE");
        newProperties.put("mnuItemPayPal.colour", "ORANGE");
        newProperties.put("mnuCopyResult.colour", "ORANGE");
        newProperties.put("mnuItemBack.colour", "ORANGE");
        newProperties.put("mnuItemButtonStyle.colour", "ORANGE");
        newProperties.put("mnuItemOrange.colour", "ORANGE");
        newProperties.put("mnuItemPurple.colour", "ORANGE");

        newProperties.put("mnuStyle.colour", "ORANGE");
        newProperties.put("mnuExport.colour", "ORANGE");
        newProperties.put("mnuItemNoBoarder.colour", "ORANGE");
        newProperties.put("mnuItemBorderCompound.colour", "ORANGE");
        newProperties.put("mnuItemBorderLower.colour", "ORANGE");
        newProperties.put("mnuItemBorderRaised.colour", "ORANGE");
        newProperties.put("mnuItemLime.colour", "ORANGE");
        newProperties.put("mnuItemPlain.colour", "ORANGE");
        newProperties.put("row.colour", "ORANGE");

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
