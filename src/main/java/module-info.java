module com.example.quizapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires junit;
    requires org.junit.jupiter.api;


    opens menu to javafx.fxml;
    exports menu;

    opens menu2 to javafx.fxml;
    exports menu2;

    opens model to javafx.fxml;
    exports model;

    opens application to javafx.fxml;
    exports application;

    opens dbUtil to javafx.fxml;
    exports dbUtil;
}