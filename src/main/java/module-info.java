module org.example.moneytrackerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.moneytrackerapp to javafx.fxml;
    exports org.example.moneytrackerapp;
}