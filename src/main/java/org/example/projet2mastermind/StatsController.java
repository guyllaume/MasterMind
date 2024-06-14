package org.example.projet2mastermind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StatsController {
    private Main mainApp;
    @FXML
    private TableView<StatsModel> statsTable;
    @FXML
    private TableColumn<StatsModel, Integer> userID_tc;
    @FXML
    private TableColumn<StatsModel, String> essai_tc;
    @FXML
    private TableColumn<StatsModel, String> couleur1_tc;
    @FXML
    private TableColumn<StatsModel, String> couleur2_tc;
    @FXML
    private TableColumn<StatsModel, String> couleur3_tc;
    @FXML
    private TableColumn<StatsModel, String> couleur4_tc;
    @FXML
    private TableColumn<StatsModel, Integer> noir_tc;
    @FXML
    private TableColumn<StatsModel, Integer> blanc_tc;
    @FXML
    private TableColumn<StatsModel, Integer> points_tc;

    public void loadStats() {
        userID_tc.setCellValueFactory(new PropertyValueFactory<>("userId"));
        essai_tc.setCellValueFactory(new PropertyValueFactory<>("essai"));
        couleur1_tc.setCellValueFactory(new PropertyValueFactory<>("couleur1"));
        couleur2_tc.setCellValueFactory(new PropertyValueFactory<>("couleur2"));
        couleur3_tc.setCellValueFactory(new PropertyValueFactory<>("couleur3"));
        couleur4_tc.setCellValueFactory(new PropertyValueFactory<>("couleur4"));
        noir_tc.setCellValueFactory(new PropertyValueFactory<>("noir"));
        blanc_tc.setCellValueFactory(new PropertyValueFactory<>("blanc"));
        points_tc.setCellValueFactory(new PropertyValueFactory<>("points"));
        StatsDAO statsDAO = new StatsDAO();
        List<StatsModel> stats = statsDAO.loadStatsModel(mainApp.getUsager().getId());
        ObservableList<StatsModel> observableList = FXCollections.observableArrayList(stats);
        statsTable.setItems(observableList);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
