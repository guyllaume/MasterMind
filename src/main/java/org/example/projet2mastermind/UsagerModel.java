package org.example.projet2mastermind;

public class UsagerModel {
    private int id;
    private String username;
    private String password;
    private String nick;

    //Constructeur d'un modele usager avec vérification des paramètres
    public UsagerModel(int id, String username, String password, String nick) {
        this.id = id;
        if (username.isEmpty() || username.length() > 45) {
            throw new IllegalArgumentException("Le nom d'utilisateur ne doit pas etre vide et ne doit pas dépasser 45 caractères");
        }
        this.username = username;
        if (password.isEmpty() || password.length() > 45) {
            throw new IllegalArgumentException("Le mot de passe ne doit pas etre vide et ne doit pas dépasser 45 caractères");
        }
        this.password = password;
        if (nick.isEmpty() || nick.length() > 45) {
            throw new IllegalArgumentException("Le nickname ne doit pas etre vide et ne doit pas dépasser 45 caractères");
        }
        this.nick = nick;
    }

    //Setters avec verification
    public void setUsername(String username) {
        if (username.isEmpty() || username.length() > 45) {
            throw new IllegalArgumentException("Le nom d'utilisateur ne doit pas etre vide et ne doit pas dépasser 45 caractères");
        }
        this.username = username;
    }
    public void setPassword(String password) {
        if (password.isEmpty() || password.length() > 45) {
            throw new IllegalArgumentException("Le mot de passe ne doit pas etre vide et ne doit pas dépasser 45 caractères");
        }
        this.password = password;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getNick() {
        return nick;
    }
    public int getId() {
        return id;
    }

}
