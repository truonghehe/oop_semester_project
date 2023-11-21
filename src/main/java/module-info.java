module com.mycompany.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpclient;
    requires mysql.connector.java;
    requires java.sql;
    requires javafx.web;
    requires freetts;
    requires java.desktop;
    opens com.mycompany.app to javafx.fxml;
    exports com.mycompany.app;
    exports com.mycompany.app.Controllers;
    opens com.mycompany.app.Controllers to javafx.fxml;
    exports com.mycompany.app.Controllers.DictionaryControllers;
    opens com.mycompany.app.Controllers.DictionaryControllers to javafx.fxml;
    exports com.mycompany.app.Controllers.GameControllers;
    opens com.mycompany.app.Controllers.GameControllers to javafx.fxml;
}