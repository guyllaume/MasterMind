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

    public GameMaster() {
        generateColors();
        nbEssai = 0;
        nbWhite = 0;
        nbBlack = 0;
        nbPoints = 5;
    }

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
    public void nouvelleEssai(Color[] colors) {
        if (colors.length != 4) {
            throw new IllegalArgumentException("Le tableau doit contenir 4 couleurs");
        }
        nbWhite = 0;
        nbBlack = 0;
        nbPointsGagnésParEssai = 0;
        nbEssai++;
        boolean indexAlreadyCheckedJ[] = {false, false, false, false};
        boolean indexAlreadyCheckedI[] = {false, false, false, false};
        for (int i = 0; i < colors.length; i++) {
            if (this.colors[i] == colors[i]){ nbBlack++; indexAlreadyCheckedJ[i] = true; indexAlreadyCheckedI[i] = true;}
        }
        for (int i = 0; i < colors.length; i++) {
            if (indexAlreadyCheckedI[i]) { continue; }
            for (int j = 0; j < colors.length; j++) {
                if (indexAlreadyCheckedJ[j]) { continue; }
                if (this.colors[i] == colors[j] && i != j){
                    indexAlreadyCheckedJ[j] = true;
                    nbWhite++;
                    break;
                }
            }
        }
        nbPoints = nbPoints - 1 + nbWhite + nbBlack * 2;
        nbPointsGagnésParEssai = nbWhite + nbBlack * 2;
        if (nbBlack == 4) { GameWon = true;}
    }

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

    public boolean isGameLost(){
        return nbEssai >= NB_ESSAI_MAX;
    }

    public boolean isGameWon() {
        if (GameWon) {
            nbPoints += 10;
        }
        return GameWon;
    }

    public int getNbPointsGagnésParEssai() {
        return nbPointsGagnésParEssai;
    }
}
