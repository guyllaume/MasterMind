package org.example.projet2mastermind;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController {

    @FXML
    private TextField username_txt;
    @FXML
    private PasswordField password_txt;
    private Main mainApp;
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    public void LoginButtonClick(){
        String username = username_txt.getText();
        String password = password_txt.getText();

        try {
            UsagerModel usager = new UsagerDAO().findUsager(username, password);
            if (usager == null) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Login Error");
                error.setHeaderText("Le nom d'utilisateur ou le mot de passe n'existe pas");
                error.showAndWait();
            } else {
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Login Réussi");
                infoAlert.setHeaderText("Redirection vers le jeu...");
                infoAlert.showAndWait();
                mainApp.switchToGameScene();
            }
        }catch (IllegalArgumentException e){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Login Error");
            error.setHeaderText("Le nom d'utilisateur ou le mot de passe est incorrect");
            error.showAndWait();
        }
    }
}