package org.example.projet2mastermind;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.projet2mastermind.InitializeDB.getConnection;

public class UsagerDAO {
    //Recherche d'un usager dans la DB par nom d'utilisateur et mot de passe
    public UsagerModel findUsager(String username, String password) {
        // Creer un instance d'usager vide
        UsagerModel usager = null;

        // Query de recherche avec un prepared statement
        String query = "SELECT * FROM usager WHERE nomusager = ? AND motpasse = ?";

        // Connection a la DB et preparation de la requête
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            // Ajout des paramètres username et password dans la requête
            statement.setString(1, username);
            statement.setString(2, password);

            //Execution de la requête et recuperation du resultat dans le ResultSet
            ResultSet resultSet = statement.executeQuery();

            //Traitement du resultat s'il existe
            if (resultSet.next()) {
                //Creation d'un usager avec les informations du resultSet
                usager = new UsagerModel(
                        resultSet.getInt("idusager"),
                        resultSet.getString("nomusager"),
                        resultSet.getString("motpasse"),
                        resultSet.getString("nick")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Retour de l'usager
        return usager;
    }
}
