package wagemaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HistAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        File file = null;
        try {
            file = CalcProperties.historyFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                DisplayReport.main(null);
            } catch (IOException e) {
            }
        } else {
            try {
                DisplayReport.main(null);
            } catch (IOException e) {
            }
        }
    }
}
