package org.example.projet2mastermind;

import javafx.scene.paint.Color;

import java.util.Random;

public class GameMaster {
    private Color[] colors;
    private int nbEssai;
    private final int NB_ESSAI_MAX = 12;
    private int nbWhite;
    private int nbBlack;
    private int nbPoints;
    private int nbPointsGagnésParEssai;
    private boolean GameWon;

    //Constructeur
    public GameMaster() {
        generateColors();
        nbEssai = 0;
        nbWhite = 0;
        nbBlack = 0;
        nbPoints = 5;
    }

    //Génération des couleurs aleatoires pour le jeu
    public void generateColors() {
        Random random = new Random();
        colors = new Color[4];
        for (int i = 0; i < 4; i++) {
            int randomNumber = random.nextInt(6);
            switch (randomNumber) {
                case 0:
                    colors[i] = Color.BLUE;
                    break;
                case 1:
                    colors[i] = Color.GREEN;
                    break;
                case 2:
                    colors[i] = Color.RED;
                    break;
                case 3:
                    colors[i] = Color.ORANGE;
                    break;
                case 4:
                    colors[i] = Color.YELLOW;
                    break;
                case 5:
                    colors[i] = Color.PURPLE;
                    break;
            }
        }
    }
    //Méthode pour l'essai
    public void nouvelleEssai(Color[] colors) {
        //Vérification que le tableau a 4 couleurs
        if (colors.length != 4) {
            throw new IllegalArgumentException("Le tableau doit contenir 4 couleurs");
        }

        //Réinitialisation des variables
        nbPointsGagnésParEssai = 0;
        nbWhite = 0;
        nbBlack = 0;

        //Incrémentation du nombre d'essai
        nbEssai++;

        //Vérification des couleurs choisis par l'utilisateur vs les couleurs du gameMaster
        //Tableau de booleen pour savoir si une couleur a deja ete verifiée
        boolean indexAlreadyCheckedJ[] = {false, false, false, false};
        boolean indexAlreadyCheckedI[] = {false, false, false, false};

        //Comptage des points noirs - enlevent les index trouvé si les couleurs sont identiques
        for (int i = 0; i < colors.length; i++) {
            if (this.colors[i] == colors[i]){ nbBlack++; indexAlreadyCheckedJ[i] = true; indexAlreadyCheckedI[i] = true;}
        }
        //Comptage des points blancs - enlevent les index si les couleurs sont identiques
        for (int i = 0; i < colors.length; i++) {
            if (indexAlreadyCheckedI[i]) { continue; } //Si index deja verifié, on passe à la suivante
            for (int j = 0; j < colors.length; j++) {
                if (indexAlreadyCheckedJ[j]) { continue; } //Si index deja verifié, on passe à la suivante
                if (this.colors[i] == colors[j] && i != j){ //Si les couleurs sont identiques et que l'index n'est pas le même
                    indexAlreadyCheckedJ[j] = true;
                    nbWhite++;
                    break;
                }
            }
        }
        //Mise à jour des points
        nbPoints = nbPoints - 1 + nbWhite + nbBlack * 2;
        //Mise à jour des points gagnés par essai
        nbPointsGagnésParEssai = nbWhite + nbBlack * 2;
        //S'il y a 4 points noirs, le jeu est gagné
        if (nbBlack == 4) { GameWon = true;}
    }

    //Getters
    public Color[] getColors() {
        return colors;
    }
    public int getWhite(){
        return nbWhite;
    }
    public int getBlack(){
        return nbBlack;
    }
    public int getNbEssai() {
        return nbEssai;
    }
    public int getNbEssaiRestants(){
        return NB_ESSAI_MAX - nbEssai;
    }
    public int getNbPoints(){
        return nbPoints;
    }
    public int getNbPointsGagnésParEssai() {
        return nbPointsGagnésParEssai;
    }

    //Méthode pour savoir si le jeu est perdu
    public boolean isGameLost(){
        return nbEssai >= NB_ESSAI_MAX && !GameWon;
    }

    //Méthode pour savoir si le jeu est gagné et ajuste le score
    public boolean isGameWon() {
        if (GameWon) {nbPoints += 10;}
        return GameWon;
    }
}
