module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires java.desktop;


    opens com.example.javafx to javafx.fxml;
    opens com.example.javafx.Helpers to javafx.base;
    exports com.example.javafx;
}