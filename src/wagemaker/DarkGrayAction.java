package wagemaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DarkGrayAction implements ActionListener {
    private final OrangeCalc owner;
    private final JButton[] button;
    private final JPanel[] row;
    private final Border compound;
    private final Border loweredbevel;
    private final Border raisedbevel;

    public DarkGrayAction(OrangeCalc owner, JButton[] button, JPanel[] row, Border compound, Border loweredbevel, Border raisedbevel) {
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

    OrangeCalc.mnuStyle.setForeground(CalcProperties.DARKGREY);
        owner.mnuExport.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemNoBoarder.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemBorderCompound.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemBorderLower.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemBorderRaised.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemLime.setForeground(CalcProperties.DARKGREY);
        owner.mnuItemPlain.setForeground(CalcProperties.DARKGREY);

        for (int i = 0; i < 5; i++) {
            row[i].setBackground(CalcProperties.BLACK);
        }

        for (int i = 0; i < 24; i++) {
            if (button[0].getBorder() == compound) {
                button[i].setBorder((Border) compound);
            } else if (button[0].getBorder() == loweredbevel) {
                button[i].setBorder((Border) loweredbevel);
            } else if (button[0].getBorder() == raisedbevel) {
                button[i].setBorder((Border) raisedbevel);
            } else if (button[0].getBorder() == null) {
                button[i].setBorder((Border) null);
            }

            if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6 || i == 8 || i == 9 || i == 10 || i == 18) {
                button[i].setBackground(CalcProperties.DARKGREY);
                button[i].setForeground(CalcProperties.BLACK);
                button[i].setContentAreaFilled(true);
                button[i].setFont(FontTheme.size18i);
            } else {
                button[i].setBackground(CalcProperties.BLACK);
                button[i].setForeground(CalcProperties.BLACK);
                button[i].setContentAreaFilled(true);
                button[i].setFont(FontTheme.size18i);
                if (i == 20) {
                    button[i].setBorder(null);
                } else {
                    button[i].setBorder(BorderFactory.createLineBorder(CalcProperties.WHITE));
                }
            }
        }
        button[20].setBorder(null);

        for (int i = 0; i < 24; i++) {
            if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6 || i == 8 || i == 9 || i == 10 || i == 18) {
                button[i].setBackground(CalcProperties.DARKGREY);
            } else if (i == 3 || i == 7 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15 || i == 16 || i == 17 || i == 19
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

        newProperties.put("theme.colour", "DARKGREY");
        newProperties.put("button.colour", "DARKGREY");
        newProperties.put("button_c.colour", "BLACK");
        newProperties.put("audio.status", "false");
        newProperties.put("mb.colour", "BLACK");
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

        newProperties.put("mnuStyle.colour", "DARKGREY");
        newProperties.put("mnuExport.colour", "DARKGREY");
        newProperties.put("mnuItemNoBoarder.colour", "DARKGREY");
        newProperties.put("mnuItemBorderCompound.colour", "DARKGREY");
        newProperties.put("mnuItemBorderLower.colour", "DARKGREY");
        newProperties.put("mnuItemBorderRaised.colour", "DARKGREY");
        newProperties.put("mnuItemLime.colour", "DARKGREY");
        newProperties.put("mnuItemPlain.colour", "DARKGREY");
        newProperties.put("row.colour", "BLACK");

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
