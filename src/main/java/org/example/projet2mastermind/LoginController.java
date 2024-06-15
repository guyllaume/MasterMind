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
    //Setter du MainApp pour utiliser ces méthodes
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    //Event pour le bouton Login
    @FXML
    public void LoginButtonClick(){
        //Affectation du nom d'utilisateur et du mot de passe
        String username = username_txt.getText();
        String password = password_txt.getText();

        //Verification du nom d'utilisateur et du mot de passe dans la DB
        try {
            //Get usager from DB avec le DAO
            UsagerModel usager = new UsagerDAO().findUsager(username, password);
            //Si l'usager n'existe pas, on affiche un message d'erreur
            if (usager == null) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Login Error");
                error.setHeaderText("Le nom d'utilisateur ou le mot de passe n'existe pas");
                error.showAndWait();
            } else { // Sinon, on redirige l'usager vers le jeu
                //Affichage d'un message de redirection
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Login Réussi");
                infoAlert.setHeaderText("Redirection vers le jeu...");
                infoAlert.showAndWait();

                // On affecte l'usager dans le MainApp
                mainApp.setUsager(usager);
                // On redirige l'usager vers la scene de jeu
                mainApp.switchToGameScene();
            }
        }catch (IllegalArgumentException e){ // Le nom d'utilisateur ou le mot de passe est incorrect
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Login Error");
            error.setHeaderText("Le nom d'utilisateur ou le mot de passe est incorrect");
            error.showAndWait();
        }
    }
}