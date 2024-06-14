package org.example.projet2mastermind;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatsDAO {

    public void insertStats(StatsModel stats) {
        String query = "INSERT INTO stats (userId, essai, couleur1, couleur2, couleur3, couleur4, noir, blanc, points) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try{
            Connection connection = InitializeDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, stats.getUserId());
            statement.setString(2, "Essai "+ stats.getEssai());
            statement.setString(3, stats.getCouleurs()[0]);
            statement.setString(4, stats.getCouleurs()[1]);
            statement.setString(5, stats.getCouleurs()[2]);
            statement.setString(6, stats.getCouleurs()[3]);
            statement.setInt(7, stats.getNoir());
            statement.setInt(8, stats.getBlanc());
            statement.setInt(9, stats.getPoints());
            statement.executeUpdate();
            System.out.println("Stats ajouté");
            statement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public List<StatsModel> loadStatsModel(int userId) {
        String query = "SELECT * FROM stats WHERE userId = ?";

        List<StatsModel> statsList = new ArrayList<>();
        try {
            Connection connection = InitializeDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return statsList;
    }
}
