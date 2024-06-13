package org.example.projet2mastermind;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class GameController {
    private Main mainApp;
    private Scene gameScene;
    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
        setupKeyEventHandlers();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private void setupKeyEventHandlers() {
        gameScene.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.F) {
                openHelpStageEvent();
            }
        });
    }
    public void openHelpStageEvent() {
        mainApp.showHelpStage();
    }
}
