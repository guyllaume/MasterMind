module org.example.projet2mastermind {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.projet2mastermind to javafx.fxml;
    exports org.example.projet2mastermind;
}