module com.example.frozengridpane {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.frozengridpane to javafx.fxml;
    exports com.example.frozengridpane;
}