package org.example.projet2mastermind;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.InputStream;

public class HelpController {
    @FXML
    private TextArea rules_txa;
    @FXML
    public void initialize() {
        StringBuilder sb = new StringBuilder();
        try(InputStream inputStream = getClass().getResourceAsStream("/help.txt");
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            rules_txa.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
