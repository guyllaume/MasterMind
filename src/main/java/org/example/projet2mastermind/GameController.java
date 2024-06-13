package org.example.projet2mastermind;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;

public class GameController {
    private Main mainApp;
    private Scene gameScene;
    @FXML
    private Button validerChoix_btn;
    @FXML
    private Button effacerChoix_btn;
    @FXML
    private Circle blueCircle;
    @FXML
    private Circle greenCircle;
    @FXML
    private Circle redCircle;
    @FXML
    private Circle orangeCircle;
    @FXML
    private Circle yellowCircle;
    @FXML
    private Circle purpleCircle;
    private Circle[] cercles;
    private boolean isGameInProgress;
    @FXML
    public void initialize() {
        cercles = new Circle[]{blueCircle, greenCircle, redCircle, orangeCircle, yellowCircle, purpleCircle};
        disableGame();
        for (Circle cercle : cercles) {
            setHoverCursor(cercle);
        }
    }
    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
        setupKeyEventHandlers();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void openHelpStageEvent() {
        mainApp.showHelpStage();
    }
    private void setupKeyEventHandlers() {
        gameScene.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.F) {
                openHelpStageEvent();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.N) {
                enableGame();
            }
        });
    }

    public void setHoverCursor(Circle cercle){
        cercle.setOnMouseEntered(event -> cercle.setCursor(Cursor.HAND));
        cercle.setOnMouseExited(event -> cercle.setCursor(Cursor.DEFAULT));
    }

    public void disableGame(){
        isGameInProgress = false;
        validerChoix_btn.setDisable(true);
        effacerChoix_btn.setDisable(true);
        for (Circle cercle : cercles) {
            cercle.setDisable(true);
        }
    }
    public void enableGame(){
        isGameInProgress = true;
        validerChoix_btn.setDisable(false);
        effacerChoix_btn.setDisable(false);
        for (Circle cercle : cercles) {
            cercle.setDisable(false);
        }
    }
}
