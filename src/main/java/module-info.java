module com.mycompany.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpclient;
    requires mysql.connector.java;
    requires java.sql;
    requires javafx.web;
    opens com.mycompany.app to javafx.fxml;
    exports com.mycompany.app;
    opens CommandLine to javafx.fxml;
    opens Controller to javafx.fxml;
    exports com.mycompany.app.CommandLine;
    opens com.mycompany.app.CommandLine to javafx.fxml;
    exports com.mycompany.app.Controller;
    opens com.mycompany.app.Controller to javafx.fxml;
}