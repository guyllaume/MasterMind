package org.example.projet2mastermind;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static org.example.projet2mastermind.InitializeDB.initializeConnection;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            initializeConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("MasterMind");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}