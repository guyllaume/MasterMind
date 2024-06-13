package org.example.projet2mastermind;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static org.example.projet2mastermind.InitializeDB.initializeConnection;

public class Main extends Application {
    private Stage mainStage;
    private Stage helpStage;
    private Stage statsStage;
    private Scene helpScene;
    private Scene statsScene;
    private Scene loginScene;
    private Scene gameScene;
    private LoginController loginController;
    private GameController gameController;
    @Override
    public void start(Stage mainStage) throws IOException {
        //Initialisation de la DB
        try {
            initializeConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
        this.mainStage = mainStage;

        //Chargement des scenes
        FXMLLoader fxmlLoaderLogin = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        FXMLLoader fxmlLoaderGame = new FXMLLoader(Main.class.getResource("game-view.fxml"));
        FXMLLoader fxmlLoaderStats = new FXMLLoader(Main.class.getResource("stats-view.fxml"));
        FXMLLoader fxmlLoaderHelp = new FXMLLoader(Main.class.getResource("aide-view.fxml"));
        loginScene = new Scene(fxmlLoaderLogin.load());
        gameScene = new Scene(fxmlLoaderGame.load());
        statsScene = new Scene(fxmlLoaderStats.load());
        helpScene = new Scene(fxmlLoaderHelp.load());

        //Chargement du controller login pour le mainStage
        loginController = fxmlLoaderLogin.getController();
        loginController.setMainApp(this);

        //Chargement du controller game pour le mainStage
        gameController = fxmlLoaderGame.getController();
        gameController.setMainApp(this);
        gameController.setGameScene(gameScene);

        //Chargement du controller stats pour le statsStage (TODO)
        /*loginController = fxmlLoaderLogin.getController();
        loginController.setMainApp(this);*/

        //Chargement du controller help pour le helpstage (TODO)
        /*gameController = fxmlLoaderGame.getController();
        gameController.setMainApp(this);*/

        //MainStage settings
        mainStage.setTitle("Login");
        mainStage.setScene(loginScene);
        mainStage.show();
        
        //HelpStage settings
        helpStage = new Stage();
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        
        //StatsStage settings
        statsStage = new Stage();
        statsStage.setTitle("Stats");
        statsStage.setScene(statsScene);
    }

    public void switchToGameScene() {
        mainStage.setScene(gameScene);
        mainStage.setTitle("MasterMind");
    }

    public void showHelpStage() {
        helpStage.show();
    }
    public void hideHelpStage() {
        helpStage.hide();
    }
    public void showStatsStage() {
        statsStage.show();
    }
    public void hideStatsStage() {
        statsStage.hide();
    }

    public static void main(String[] args) {
        launch();
    }
}