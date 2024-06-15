package org.example.projet2mastermind;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatsDAO {

    //Ajout d'une nouvelle stat dans la DB
    public void insertStats(StatsModel stats) {
        //Query d'insertion avec un prepared statement
        String query = "INSERT INTO stats (userId, essai, couleur1, couleur2, couleur3, couleur4, noir, blanc, points) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //Execution de la requête d'insertion dans le try catch pour automatiquement fermer la connexion
        try(Connection connection = InitializeDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            //Ajout des paramètres stats dans la requête
            statement.setInt(1, stats.getUserId());
            statement.setString(2, "Essai "+ stats.getEssai());
            statement.setString(3, stats.getCouleurs()[0]);
            statement.setString(4, stats.getCouleurs()[1]);
            statement.setString(5, stats.getCouleurs()[2]);
            statement.setString(6, stats.getCouleurs()[3]);
            statement.setInt(7, stats.getNoir());
            statement.setInt(8, stats.getBlanc());
            statement.setInt(9, stats.getPoints());

            //Execution de la requête et Log de l'insertion
            statement.executeUpdate();
            System.out.println("Stats ajouté");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    //Chargement de toutes les stats d'un utilisateur donné
    public List<StatsModel> loadStatsModel(int userId) {
        //Query de chargement avec un prepared statement
        String query = "SELECT * FROM stats WHERE userId = ?";


        List<StatsModel> statsList = new ArrayList<>();

        // Connection a la DB et preparation de la requête
        try (Connection connection = InitializeDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            //Ajout des paramètres userId dans la requête
            statement.setInt(1, userId);

            //Execution de la requête et recuperation des resultats dans le ResultSet
            ResultSet resultSet = statement.executeQuery();

            //Traitement des resultats dans une boucle while
            while (resultSet.next()) {
                //Ajout des resultats dans la liste de stats
                statsList.add(new StatsModel(
                        resultSet.getInt("userId"),
                        resultSet.getString("essai"),
                        resultSet.getString("couleur1"),
                        resultSet.getString("couleur2"),
                        resultSet.getString("couleur3"),
                        resultSet.getString("couleur4"),
                        resultSet.getInt("noir"),
                        resultSet.getInt("blanc"),
                        resultSet.getInt("points")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Retour de la liste de stats
        return statsList;
    }
}
