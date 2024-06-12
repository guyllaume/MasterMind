package org.example.projet2mastermind;
import java.sql.*;

public class InitializeDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "tp2";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "jnmpdtdmj13!";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
    }

    public static void initializeConnection() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //Create DB if not exists
            statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("DB "+ DB_NAME +" créé ou deja existante");

            //Create Table usager if not exists
            createTableUsager(connection);

            //Create Table stats if not exists
            createTableStats(connection);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement);
        }
    }

    private static void createTableUsager(Connection connection){
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String createUsagerTable = "CREATE TABLE IF NOT EXISTS `tp2`.`usager` (\n" +
                    "  `idusager` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `nomusager` VARCHAR(45) NOT NULL,\n" +
                    "  `motpasse` VARCHAR(45) NOT NULL,\n" +
                    "  `nick` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`idusager`),\n" +
                    "  UNIQUE INDEX `idusager_UNIQUE` (`idusager` ASC) VISIBLE,\n" +
                    "  UNIQUE INDEX `nomusager_UNIQUE` (`nomusager` ASC) VISIBLE,\n" +
                    "  UNIQUE INDEX `nick_UNIQUE` (`nick` ASC) VISIBLE);\n";
            String InsertUsagerTable = "INSERT INTO `tp2`.`usager` " +
                    "(`nomusager`, `motpasse`, `nick`) " +
                    "SELECT * FROM (SELECT 'Fred0001', 'P@ssword123', 'zegamer1235' " +
                    "UNION ALL " +
                    "SELECT 'Bob0001', 'P@ss123', 'leking0001' " +
                    "UNION ALL " +
                    "SELECT 'Zia0001', 'P@sse123', 'laprincesse0121' " +
                    "UNION ALL " +
                    "SELECT 'pichou0001', 'P@sse123', 'lapichou0121') AS tmp " +
                    "WHERE NOT EXISTS " +
                    "(SELECT nomusager FROM `tp2`.`usager` WHERE nomusager IN ('Fred0001', 'Bob0001', 'Zia0001', 'pichou0001'));";
            statement.executeUpdate(createUsagerTable);
            statement.executeUpdate(InsertUsagerTable);
            System.out.println("Table usager créé ou deja existante");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeStatement(statement);
        }
    }
    private static void createTableStats(Connection connection){
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String createStatsTable = "CREATE TABLE IF NOT EXISTS `tp2`.`stats` (\n" +
                    "  `idstats` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `userId` INT NOT NULL,\n" +
                    "  `essai` VARCHAR(45) NOT NULL,\n" +
                    "  `couleur1` VARCHAR(45) NOT NULL,\n" +
                    "  `couleur2` VARCHAR(45) NOT NULL,\n" +
                    "  `couleur3` VARCHAR(45) NOT NULL,\n" +
                    "  `couleur4` VARCHAR(45) NOT NULL,\n" +
                    "  `noir` INT NOT NULL,\n" +
                    "  `blanc` INT NOT NULL,\n" +
                    "  `points` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`idstats`),\n" +
                    "  UNIQUE INDEX `idstats_UNIQUE` (`idstats` ASC) VISIBLE,\n" +
                    "  UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `usagerid`\n" +
                    "    FOREIGN KEY (`userId`)\n" +
                    "    REFERENCES `tp2`.`usager` (`idusager`)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE NO ACTION);\n";
            statement.executeUpdate(createStatsTable);
            System.out.println("Table stats créé ou deja existante");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeStatement(statement);
        }
    }

    public static void close(Connection connection, Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(Statement statement){
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
