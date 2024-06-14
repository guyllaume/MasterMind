package org.example.projet2mastermind;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class GameController {
    private Main mainApp;
    private Scene gameScene;
    @FXML
    private Button validerChoix_btn;
    @FXML
    private Button effacerChoix_btn;
    @FXML
    private TextArea affichageEssai_txa;
    @FXML
    private TextArea affichageLog_txa;
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
    @FXML
    private Circle cercleChoisi1;
    @FXML
    private Circle cercleChoisi2;
    @FXML
    private Circle cercleChoisi3;
    @FXML
    private Circle cercleChoisi4;
    private Circle[] cerclesPossibles;
    private Circle[] cerclesChoisis;
    private GameMaster gameMaster;
    private boolean isGameInProgress; // will probably not use
    @FXML
    public void initialize() {
        cerclesPossibles = new Circle[]{blueCircle, greenCircle, redCircle, orangeCircle, yellowCircle, purpleCircle};
        cerclesChoisis = new Circle[]{cercleChoisi1, cercleChoisi2, cercleChoisi3, cercleChoisi4};
        disableGame();
        for (Circle cercle : cerclesPossibles) {
            setHoverCursor(cercle);
            setClickCircle(cercle);
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
    public void setClickCircle(Circle cercle){
        cercle.setOnMouseClicked(event -> {
            for (Circle cercleChoisi : cerclesChoisis) {
                if (!cercleChoisi.getFill().equals(Color.valueOf("#c6c6c6"))) { continue;}
                cercleChoisi.setFill(cercle.getFill());
                break;
            }
        });
    }

    public void setValiderChoix_btnEvent(){
        for (Circle cercle : cerclesChoisis) {
            if (cercle.getFill().equals(Color.valueOf("#c6c6c6"))) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Erreur");
                error.setHeaderText("Veuillez choisir 4 couleurs avant de valider");
                error.showAndWait();
                return;
            }
        }
        Color[] couleursChoisies = new Color[]{(Color) cerclesChoisis[0].getFill(), (Color) cerclesChoisis[1].getFill(), (Color) cerclesChoisis[2].getFill(), (Color) cerclesChoisis[3].getFill()};
        gameMaster.nouvelleEssai(couleursChoisies);
        String[] nomCouleur = getNomCouleur(couleursChoisies);
        affichageEssai_txa.setText(affichageEssai_txa.getText() + "Essai " + gameMaster.getNbEssai() + " " +
                nomCouleur[0] + " " + nomCouleur[1] + " " + nomCouleur[2] + " " + nomCouleur[3] +
                " Noir:" + gameMaster.getBlack() + " Blanc:" + gameMaster.getWhite() + " Points:" + gameMaster.getNbPoints() + "\n");
        affichageEssai_txa.setScrollTop(Double.MAX_VALUE);
        if (gameMaster.isGameLost()){
            nomCouleur = getNomCouleur(gameMaster.getColors());
            affichageEssai_txa.setText(affichageEssai_txa.getText() + "Voici la combinaison secrète:" + nomCouleur[0] + " " + nomCouleur[1] + " " + nomCouleur[2] + " " + nomCouleur[3]);
            affichageEssai_txa.setScrollTop(Double.MAX_VALUE);
            disableGame();
        }
        if (gameMaster.isGameWon()){
            affichageEssai_txa.setText(affichageEssai_txa.getText() + " Points pour la résolution:" + gameMaster.getNbPoints());
            affichageEssai_txa.setScrollTop(Double.MAX_VALUE);
            disableGame();
        }
        resetCerclesChoisis();
    }
    public String[] getNomCouleur(Color[] couleursChoisies){
        String[] nomCouleur = new String[4];
        for (int i = 0; i < 4; i++) {
            if(couleursChoisies[i] == Color.BLUE) nomCouleur[i] = "Bleu";
            else if(couleursChoisies[i] == Color.GREEN) nomCouleur[i] = "Vert";
            else if(couleursChoisies[i] == Color.RED) nomCouleur[i] = "Rouge";
            else if(couleursChoisies[i] == Color.ORANGE) nomCouleur[i] = "Orange";
            else if(couleursChoisies[i] == Color.YELLOW) nomCouleur[i] = "Jaune";
            else if(couleursChoisies[i] == Color.PURPLE) nomCouleur[i] = "Violet";
        }
        return nomCouleur;
    }

    public void disableGame(){
        isGameInProgress = false;
        validerChoix_btn.setDisable(true);
        effacerChoix_btn.setDisable(true);
        for (Circle cercle : cerclesPossibles) {
            cercle.setDisable(true);
        }
        resetCerclesChoisis();
    }
    public void enableGame(){
        isGameInProgress = true;
        gameMaster = new GameMaster();
        validerChoix_btn.setDisable(false);
        effacerChoix_btn.setDisable(false);
        affichageEssai_txa.setText("");
        for (Circle cercle : cerclesPossibles) {
            cercle.setDisable(false);
        }
    }
    public void resetCerclesChoisis(){
        for (Circle cercle : cerclesChoisis) {
            cercle.setFill(Color.valueOf("#c6c6c6"));
        }
    }
}
