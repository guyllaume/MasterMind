package org.example.projet2mastermind;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.projet2mastermind.InitializeDB.getConnection;

public class UsagerDAO {
    public UsagerModel findUsager(String username, String password) {
        UsagerModel usager = null;
        String query = "SELECT * FROM usager WHERE nomusager = ? AND motpasse = ?";

        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                usager = new UsagerModel(
                        resultSet.getInt("idusager"),
                        resultSet.getString("nomusager"),
                        resultSet.getString("motpasse"),
                        resultSet.getString("nick")
                );
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return usager;
    }
}
