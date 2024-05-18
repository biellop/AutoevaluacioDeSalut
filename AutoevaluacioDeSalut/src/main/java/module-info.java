module com.example.autoevaluaciodesalut {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.autoevaluaciodesalut to javafx.fxml;
    exports com.example.autoevaluaciodesalut;
}