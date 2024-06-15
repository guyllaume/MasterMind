package org.example.projet2mastermind;

import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.*;

public class StatsModel {
    private int userId;
    private String essai;
    private String couleur1;
    private String couleur2;
    private String couleur3;
    private String couleur4;
    private final String[] couleurs = {couleur1, couleur2, couleur3, couleur4};
    private int noir;
    private int blanc;
    private int points;

    //Constructeur pour le controlleur
    public StatsModel(int userId,int essai, Color[] colors, int noir, int blanc, int points) {
        this.userId = userId;
        this.essai = String.valueOf(essai);
        this.noir = noir;
        this.blanc = blanc;
        this.points = points;
        for (int i = 0; i < colors.length; i++) {
            if(colors[i] == BLUE) couleurs[i] = "Bleu";
            else if(colors[i] == GREEN) couleurs[i] = "Vert";
            else if(colors[i] == RED) couleurs[i] = "Rouge";
            else if(colors[i] == ORANGE) couleurs[i] = "Orange";
            else if(colors[i] == YELLOW) couleurs[i] = "Jaune";
            else if(colors[i] == PURPLE) couleurs[i] = "Mauve";
        }
    }
    //Constructeur pour la table Stats dans la DB
    public StatsModel(int userId,String essai, String couleur1, String couleur2, String couleur3, String couleur4, int noir, int blanc, int points) {
        this.userId = userId;
        this.essai = essai;
        this.couleur1 = couleur1;
        this.couleur2 = couleur2;
        this.couleur3 = couleur3;
        this.couleur4 = couleur4;
        this.couleurs[0] = couleur1;
        this.couleurs[1] = couleur2;
        this.couleurs[2] = couleur3;
        this.couleurs[3] = couleur4;
        this.noir = noir;
        this.blanc = blanc;
        this.points = points;
    }

    //Getters
    public int getBlanc() {
        return blanc;
    }

    public int getNoir() {
        return noir;
    }

    public int getPoints() {
        return points;
    }

    public int getUserId() {
        return userId;
    }

    public String getEssai() {
        return essai;
    }

    public String[] getCouleurs() {
        return couleurs;
    }
    //On un usage avec le tableView meme s'ils ne sont pas appeller directement
    public String getCouleur1() { return couleur1;}
    public String getCouleur2() {return couleur2;}
    public String getCouleur3() {return couleur3;}
    public String getCouleur4() {return couleur4;}
}
