package wagemaker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearHistAction implements ActionListener {
    private OrangeCalc owner;

    public ClearHistAction(OrangeCalc owner) {
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        File historyOLD = null;
        try {
            historyOLD = CalcProperties.historyOld();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean successOld = historyOLD.delete();
        if (owner.mnuItemHistOld != null) {
            owner.mnuItemHistOld.setVisible(false);
        }

        File file = null;
        try {
            file = CalcProperties.historyFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean success = file.delete();
        if (!success) {
            try {
                file.createNewFile();
                FileWriter resultfilestr = new FileWriter(CalcProperties.historyFile(), true);
                BufferedWriter outHist = new BufferedWriter(resultfilestr);
                outHist.write("<center><b><h1>\"Calculator History\"</h1></b></center>");
                outHist.newLine();
                outHist.write("<table width=\"770\" border cellpadding=\"2\">");
                outHist.close();

                UIManager.put("OptionPane.buttonOrientation", 0);
                UIManager.put("OptionPane.buttonFont", FontTheme.size15b);
                UIManager.put("OptionPane.messageFont", FontTheme.size15p);
                UIManager.put("OptionPane.messageForeground", CalcProperties.BLACK);
                UIManager.put("Panel.background", CalcProperties.ORANGE);
                UIManager.put("OptionPane.background", CalcProperties.ORANGE);

                JOptionPane.showMessageDialog(null, "\n"
                        + "  FAILED TO DELETE CACULATION HISTORY "
                        + "\n" + "\n", CalcProperties.history,
                        JOptionPane.PLAIN_MESSAGE);
            } catch (Exception fz) {
            }
            ;
        } else {
            try {
                file.createNewFile();
                FileWriter resultfilestr = new FileWriter(CalcProperties.historyFile(), true);
                BufferedWriter outHist = new BufferedWriter(resultfilestr);
                outHist.write("<center><b><h1>\"Calculator History\"</h1></b></center>");
                outHist.newLine();
                outHist.write("<table width=\"770\" border cellpadding=\"2\">");
                outHist.close();

                UIManager.put("OptionPane.buttonOrientation", 0);
                UIManager.put("OptionPane.buttonFont", FontTheme.size15b);
                UIManager.put("OptionPane.messageFont", FontTheme.size15p);
                UIManager.put("OptionPane.messageForeground", CalcProperties.BLACK);
                UIManager.put("Panel.background", CalcProperties.ORANGE);
                UIManager.put("OptionPane.background", CalcProperties.ORANGE);

                JOptionPane.showMessageDialog(null, "\n"
                        + "  ALL CACULATION HISTORY NOW DELETED  "
                        + "\n" + "\n", CalcProperties.history,
                        JOptionPane.PLAIN_MESSAGE);
            } catch (Exception fz) {
            }
            ;
        }
    }
}