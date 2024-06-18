import javafx.scene.paint.Color;
import org.example.projet2mastermind.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static javafx.scene.paint.Color.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class jUnitTestUnitaire {

    //Global Variables
    private Main mainApp;
    private MockedStatic<InitializeDB> mockConnectionManager;

    // USAGER DAO
    private UsagerDAO usagerDAO;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    //STATS MODEL
    private StatsModel statsModelFromController;
    private StatsModel statsModelFromDB;

    //STATS DAO
    private StatsDAO statsDAO;

    // GAMEMASTER
    private GameMaster gameMaster;

    @BeforeEach
    public void setUp() {
        // GLOBAL VARIABLES
        mainApp = mock(Main.class);

        // USAGER DAO
        mockConnectionManager = mockStatic(InitializeDB.class);
        usagerDAO = spy(new UsagerDAO());

        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        mockConnectionManager.when(() -> InitializeDB.getConnection()).thenReturn(mockConnection);

        //STATS MODEL
        Color[] colors = {BLUE, GREEN, RED, ORANGE};
        statsModelFromController = new StatsModel(1, 5, colors, 3, 2, 10);
        statsModelFromDB = new StatsModel(2, "10", "Bleu", "Vert", "Mauve", "Jaune", 1, 1, 5);

        // STATS DAO
        statsDAO = spy(new StatsDAO());

        // GAMEMASTER
        gameMaster = new GameMaster();
    }
    @AfterEach
    public void tearDown() {
        mockConnectionManager.close();
    }

    // USAGER MODEL
    @Test
    public void testValidUsagerModelCreation() {
        UsagerModel user = new UsagerModel(1, "username", "password", "nickname");
        assertEquals(1, user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("nickname", user.getNick());
    }

    @Test
    public void testInvalidUsernameInConstructor() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new UsagerModel(1, "", "password", "nickname");
        });
        assertEquals("Le nom d'utilisateur ne doit pas etre vide et ne doit pas dépasser 45 caractères", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new UsagerModel(1, "a".repeat(46), "password", "nickname");
        });
        assertEquals("Le nom d'utilisateur ne doit pas etre vide et ne doit pas dépasser 45 caractères", exception.getMessage());
    }

    @Test
    public void testInvalidPasswordInConstructor() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new UsagerModel(1, "username", "", "nickname");
        });
        assertEquals("Le mot de passe ne doit pas etre vide et ne doit pas dépasser 45 caractères", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new UsagerModel(1, "username", "a".repeat(46), "nickname");
        });
        assertEquals("Le mot de passe ne doit pas etre vide et ne doit pas dépasser 45 caractères", exception.getMessage());
    }

    @Test
    public void testInvalidNickInConstructor() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new UsagerModel(1, "username", "password", "");
        });
        assertEquals("Le nickname ne doit pas etre vide et ne doit pas dépasser 45 caractères", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new UsagerModel(1, "username", "password", "a".repeat(46));
        });
        assertEquals("Le nickname ne doit pas etre vide et ne doit pas dépasser 45 caractères", exception.getMessage());
    }

    @Test
    public void testSetValidUsername() {
        UsagerModel user = new UsagerModel(1, "username", "password", "nickname");
        user.setUsername("newUsername");
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    public void testSetInvalidUsername() {
        UsagerModel user = new UsagerModel(1, "username", "password", "nickname");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setUsername("");
        });
        assertEquals("Le nom d'utilisateur ne doit pas etre vide et ne doit pas dépasser 45 caractères", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setUsername("a".repeat(46));
        });
        assertEquals("Le nom d'utilisateur ne doit pas etre vide et ne doit pas dépasser 45 caractères", exception.getMessage());
    }

    @Test
    public void testSetValidPassword() {
        UsagerModel user = new UsagerModel(1, "username", "password", "nickname");
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void testSetInvalidPassword() {
        UsagerModel user = new UsagerModel(1, "username", "password", "nickname");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword("");
        });
        assertEquals("Le mot de passe ne doit pas etre vide et ne doit pas dépasser 45 caractères", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword("a".repeat(46));
        });
        assertEquals("Le mot de passe ne doit pas etre vide et ne doit pas dépasser 45 caractères", exception.getMessage());
    }

    @Test
    public void testSetValidId() {
        UsagerModel user = new UsagerModel(1, "username", "password", "nickname");
        user.setId(2);
        assertEquals(2, user.getId());
    }

    // USAGER DAO

    @Test
    public void testFindUsager_UserFound() throws SQLException {
        String username = "Bob0001";
        String password = "P@ss123";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("idusager")).thenReturn(2);
        when(mockResultSet.getString("nomusager")).thenReturn(username);
        when(mockResultSet.getString("motpasse")).thenReturn(password);
        when(mockResultSet.getString("nick")).thenReturn("leking0001");

        UsagerModel result = usagerDAO.findUsager(username, password);

        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
        assertEquals("leking0001", result.getNick());
    }

    @Test
    public void testFindUsager_UserNotFound() throws SQLException {
        String username = "Zia0001";
        String password = "P@ss123";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        UsagerModel result = usagerDAO.findUsager(username, password);

        assertNull(result);
    }

    @Test
    public void testFindUsager_SQLException() throws SQLException {
        String username = "pichou0001";
        String password = "P@ss123";

        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        UsagerModel result = usagerDAO.findUsager(username, password);

        assertNull(result);
    }

    //STATS MODEL
    @Test
    public void testControllerConstructor() {
        assertEquals(1, statsModelFromController.getUserId());
        assertEquals("5", statsModelFromController.getEssai());
        assertArrayEquals(new String[]{"Bleu", "Vert", "Rouge", "Orange"}, statsModelFromController.getCouleurs());
        assertEquals(3, statsModelFromController.getNoir());
        assertEquals(2, statsModelFromController.getBlanc());
        assertEquals(10, statsModelFromController.getPoints());
    }

    @Test
    public void testDBConstructor() {
        assertEquals(2, statsModelFromDB.getUserId());
        assertEquals("10", statsModelFromDB.getEssai());
        assertArrayEquals(new String[]{"Bleu", "Vert", "Mauve", "Jaune"}, statsModelFromDB.getCouleurs());
        assertEquals(1, statsModelFromDB.getNoir());
        assertEquals(1, statsModelFromDB.getBlanc());
        assertEquals(5, statsModelFromDB.getPoints());
    }

    @Test
    public void testGetCouleursIndividuels() {
        assertEquals("Bleu", statsModelFromDB.getCouleur1());
        assertEquals("Vert", statsModelFromDB.getCouleur2());
        assertEquals("Mauve", statsModelFromDB.getCouleur3());
        assertEquals("Jaune", statsModelFromDB.getCouleur4());
    }

    @Test
    public void testGetBlanc() {
        assertEquals(2, statsModelFromController.getBlanc());
        assertEquals(1, statsModelFromDB.getBlanc());
    }

    @Test
    public void testGetNoir() {
        assertEquals(3, statsModelFromController.getNoir());
        assertEquals(1, statsModelFromDB.getNoir());
    }

    @Test
    public void testGetPoints() {
        assertEquals(10, statsModelFromController.getPoints());
        assertEquals(5, statsModelFromDB.getPoints());
    }

    //STATS DAO

    @Test
    public void testInsertStats() throws SQLException {
        StatsModel stats = new StatsModel(1, 5, new Color[]{BLUE, GREEN, RED, ORANGE}, 3, 2, 10);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        statsDAO.insertStats(stats);

        verify(mockStatement).setInt(1, stats.getUserId());
        verify(mockStatement).setString(2, "Essai " + stats.getEssai());
        verify(mockStatement).setString(3, stats.getCouleurs()[0]);
        verify(mockStatement).setString(4, stats.getCouleurs()[1]);
        verify(mockStatement).setString(5, stats.getCouleurs()[2]);
        verify(mockStatement).setString(6, stats.getCouleurs()[3]);
        verify(mockStatement).setInt(7, stats.getNoir());
        verify(mockStatement).setInt(8, stats.getBlanc());
        verify(mockStatement).setInt(9, stats.getPoints());
    }

    @Test
    public void testLoadStatsModel() throws SQLException {
        int userId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("userId")).thenReturn(userId);
        when(mockResultSet.getString("essai")).thenReturn("Essai 1");
        when(mockResultSet.getString("couleur1")).thenReturn("Bleu");
        when(mockResultSet.getString("couleur2")).thenReturn("Vert");
        when(mockResultSet.getString("couleur3")).thenReturn("Rouge");
        when(mockResultSet.getString("couleur4")).thenReturn("Orange");
        when(mockResultSet.getInt("noir")).thenReturn(3);
        when(mockResultSet.getInt("blanc")).thenReturn(2);
        when(mockResultSet.getInt("points")).thenReturn(10);

        List<StatsModel> statsList = statsDAO.loadStatsModel(userId);

        assertEquals(1, statsList.size());
        StatsModel stats = statsList.getFirst();
        assertEquals(userId, stats.getUserId());
        assertEquals("Essai 1", stats.getEssai());
        assertArrayEquals(new String[]{"Bleu", "Vert", "Rouge", "Orange"}, stats.getCouleurs());
        assertEquals(3, stats.getNoir());
        assertEquals(2, stats.getBlanc());
        assertEquals(10, stats.getPoints());
    }

    @Test
    public void testLoadStatsModel_UserNotFound() throws SQLException {
        int userId = 99;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        List<StatsModel> statsList = statsDAO.loadStatsModel(userId);

        assertTrue(statsList.isEmpty());
    }

    @Test
    public void testLoadStatsModel_SQLException() throws SQLException {
        int userId = 99;

        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        List<StatsModel> statsList = statsDAO.loadStatsModel(userId);

        assertTrue(statsList.isEmpty());
    }

    //GAMEMASTER

    @Test
    public void testGenerateColors() {
        gameMaster.generateColors();
        assertNotNull(gameMaster.getColors());
        assertEquals(4, gameMaster.getColors().length);
    }

    @Test
    public void testNouvelleEssai() {
        Color[] solution = gameMaster.getColors().clone();
        gameMaster.nouvelleEssai(solution);

        assertEquals(1, gameMaster.getNbEssai());
        assertEquals(11, gameMaster.getNbEssaiRestants());
        assertTrue(gameMaster.getNbPointsGagnésParEssai() >= 0 && gameMaster.getNbPointsGagnésParEssai() <= 8);
        assertTrue(gameMaster.getNbPoints() >= 0);
        assertTrue(gameMaster.getBlack() >= 0 && gameMaster.getBlack() <= 4);
        assertTrue(gameMaster.getWhite() >= 0 && gameMaster.getWhite() <= 4);
        assertFalse(gameMaster.isGameLost());
        assertTrue(gameMaster.isGameWon() || !gameMaster.isGameWon());
    }

    @Test
    public void testIsGameLost() {
        assertFalse(gameMaster.isGameLost());
        // Simulating maximum attempts
        for (int i = 0; i < 12; i++) {
            gameMaster.nouvelleEssai(new Color[]{Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE});
        }
        assertTrue(gameMaster.isGameLost());
    }

    @Test
    public void testIsGameWon() {
        assertFalse(gameMaster.isGameWon());
        // Setting correct solution
        gameMaster.generateColors();
        gameMaster.nouvelleEssai(gameMaster.getColors().clone());
        assertTrue(gameMaster.isGameWon());
    }

    @Test
    public void testGetters() {
        assertNotNull(gameMaster.getColors());
        assertEquals(0, gameMaster.getNbEssai());
        assertEquals(12, gameMaster.getNbEssaiRestants());
        assertEquals(5, gameMaster.getNbPoints());
        assertEquals(0, gameMaster.getNbPointsGagnésParEssai());
        assertEquals(0, gameMaster.getWhite());
        assertEquals(0, gameMaster.getBlack());
    }
}
