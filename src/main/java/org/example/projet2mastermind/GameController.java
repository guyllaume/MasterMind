package org.example.projet2mastermind;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class GameController {
    private Main mainApp;
    private Scene gameScene;
    @FXML
    private Label nickname_lbl;
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

    //Initialisation des éléments FXML de la scene et affectation des evenements
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
    //Setters
    public void setNickname(){
        nickname_lbl.setText(mainApp.getUsager().getNick());
    }
    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
        setupKeyEventHandlers(); // Ajout des gestionnaires d'événements clavier lorsque la scene est affichée
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    //ALL EVENTS
    //Event pour ouvrir le stage/scene Help
    public void openHelpStageEvent() {
        mainApp.showHelpStage();
    }
    //Event pour ouvrir le stage/scene Stats
    public void openStatsStageEvent() {
        mainApp.showStatsStage();
    }
    //Setter pour affecter des touches clavier a d'autre evenements existants
    private void setupKeyEventHandlers() {
        gameScene.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.F) { //CTRL+F
                openHelpStageEvent();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.N) { //CTRL+N
                enableGame();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.V) { //CTRL+V
                openStatsStageEvent();
            }
        });
    }
    //Setter d'événements pour les cercles de couleurs quand le curseur est sur eux et quand ils sont sortis
    public void setHoverCursor(Circle cercle){
        cercle.setOnMouseEntered(event -> cercle.setCursor(Cursor.HAND));
        cercle.setOnMouseExited(event -> cercle.setCursor(Cursor.DEFAULT));
    }
    //Setter d'événements pour les cercles de couleurs quand ils sont cliqués
    public void setClickCircle(Circle cercle){
        cercle.setOnMouseClicked(event -> {
            //boucle vérifiant qu'un cercle n'est pas encore choisi
            for (Circle cercleChoisi : cerclesChoisis) {
                if (!cercleChoisi.getFill().equals(Color.valueOf("#c6c6c6"))) { continue;}
                cercleChoisi.setFill(cercle.getFill());
                break;
            }
        });
    }
    // Event pour réinitialiser les cercles choisis
    public void resetCerclesChoisis(){
        for (Circle cercle : cerclesChoisis) {
            cercle.setFill(Color.valueOf("#c6c6c6"));
        }
    }
    // Event pour valider les couleurs choisis
    public void setValiderChoix_btnEvent() {
        // Vérifier que 4 couleurs ont été choisies avant de valider
        for (Circle cercle : cerclesChoisis) {
            if (cercle.getFill().equals(Color.valueOf("#c6c6c6"))) {
                // Afficher un message d'erreur s'il n'y a pas 4 couleurs choisis
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Erreur");
                error.setHeaderText("Veuillez choisir 4 couleurs avant de valider");
                error.showAndWait();
                // Fin de la fonction
                return;
            }
        }

        // Récupérer les couleurs choisies par l'utilisateur et les convertir en Color instead of Paint
        Color[] couleursChoisies = new Color[]{
                (Color) cerclesChoisis[0].getFill(),
                (Color) cerclesChoisis[1].getFill(),
                (Color) cerclesChoisis[2].getFill(),
                (Color) cerclesChoisis[3].getFill()
        };

        // Envoyer les couleurs choisies au GameMaster pour un nouvel essai
        gameMaster.nouvelleEssai(couleursChoisies);

        // Obtenir les noms des couleurs choisies en String
        String[] nomCouleur = getNomCouleur(couleursChoisies);

        // Mettre à jour l'affichage des essais avec les détails de l'essai actuel
        affichageEssai_txa.setText(affichageEssai_txa.getText() +
                "Essai " + gameMaster.getNbEssai() + " " +
                nomCouleur[0] + " " + nomCouleur[1] + " " + nomCouleur[2] + " " + nomCouleur[3] +
                " Noir:" + gameMaster.getBlack() + " Blanc:" + gameMaster.getWhite() + " Points:" + gameMaster.getNbPoints() + "\n");

        // Mettre à jour l'affichage du log avec les points perdus et gagnés pour cet essai
        affichageLog_txa.setText(affichageLog_txa.getText() +
                "Vous perdez 1 point(s) pour la ligne " + gameMaster.getNbEssai() +
                " et gagnez " + gameMaster.getNbPointsGagnésParEssai() + " point(s) Total de point(s): " + gameMaster.getNbPoints() + "\n" +
                "Il reste " + gameMaster.getNbEssaiRestants() + " coups\n");

        // Vérifier si le jeu est gagné
        if (gameMaster.isGameWon()) {
            // Mettre à jour l'affichage lorsque la partie est gagnée
            affichageEssai_txa.setText(affichageEssai_txa.getText() +
                    " Points pour la résolution: " + gameMaster.getNbPoints());
            affichageLog_txa.setText(affichageLog_txa.getText() +
                    "Vous avez 10 point(s) pour la résolution et vous gagnez avec " + gameMaster.getNbPoints() + " point(s)\n");
            disableGame(); // Désactiver le jeu si gagné
        }

        // Vérifier si le jeu est perdu
        if (gameMaster.isGameLost()) {
            // Mettre à jour l'affichage lorsque la partie est perdu
            affichageEssai_txa.setText(affichageEssai_txa.getText() +
                    "Voici la combinaison secrète: " + nomCouleur[0] + " " + nomCouleur[1] + " " + nomCouleur[2] + " " + nomCouleur[3]);
            affichageLog_txa.setText(affichageLog_txa.getText() +
                    "Vous avez perdu et vous gagnez 0 point(s)\n");
            disableGame(); // Désactiver le jeu si perdu
        }

        // Faire défiler les zones de texte vers le bas pour afficher les nouvelles entrées
        affichageEssai_txa.setScrollTop(Double.MAX_VALUE);
        affichageLog_txa.setScrollTop(Double.MAX_VALUE);

        // Réinitialiser les cercles choisis pour un nouvel essai
        resetCerclesChoisis();

        // Créer un modèle de statistiques avec les informations actuelles du jeu
        StatsModel stats = new StatsModel(
                mainApp.getUsager().getId(),
                gameMaster.getNbEssai(),
                couleursChoisies,
                gameMaster.getBlack(),
                gameMaster.getWhite(),
                gameMaster.getNbPoints()
        );

        // Insérer les statistiques dans la base de données
        new StatsDAO().insertStats(stats);
    }


    //METHODES
    //Methodes pour avoir les noms des couleurs en version String pour envoyer au DAO et au Model
    public String[] getNomCouleur(Color[] couleursChoisies){
        String[] nomCouleur = new String[4];

        //Conversion des couleurs en String selon la couleur donnée
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

    //Methodes pour activer et desactiver le jeu
    public void disableGame(){
        // Désactiver les boutons de validation et d'effacement
        validerChoix_btn.setDisable(true);
        effacerChoix_btn.setDisable(true);

        // Désactiver tous les cercles possibles
        for (Circle cercle : cerclesPossibles) {
            cercle.setDisable(true);
        }
        // Réinitialiser les cercles choisis
        resetCerclesChoisis();
    }
    public void enableGame(){
        // Initialiser un nouvel objet GameMaster, essentiellement une nouvelle partie
        gameMaster = new GameMaster();

        // Activer les boutons de validation et d'effacement
        validerChoix_btn.setDisable(false);
        effacerChoix_btn.setDisable(false);

        // Réinitialiser l'affichage des essais et du log
        affichageEssai_txa.setText("");
        affichageLog_txa.setText("");

        // Activer tous les cercles possibles
        for (Circle cercle : cerclesPossibles) {
            cercle.setDisable(false);
        }
    }
}
