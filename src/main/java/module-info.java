module org.example.projet2mastermind {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.projet2mastermind to javafx.fxml;
    exports org.example.projet2mastermind;
}