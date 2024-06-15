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
        // Création d'un StringBuilder pour accumuler le contenu du fichier help.txt
        StringBuilder sb = new StringBuilder();

        // Utilisation d'un bloc try-with-resources pour assurer la fermeture automatique des flux sans avoir a faire .close()
        try (InputStream inputStream = getClass().getResourceAsStream("/help.txt");
             BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(inputStream))) {

            // Variable pour stocker chaque ligne lue du fichier
            String line;

            // Lecture ligne par ligne du fichier help.txt
            while ((line = reader.readLine()) != null) {
                // Ajout de chaque ligne au StringBuilder avec un saut de ligne
                sb.append(line).append("\n");
            }

            // Affectation du contenu du StringBuilder à la TextArea rules_txa
            rules_txa.setText(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
