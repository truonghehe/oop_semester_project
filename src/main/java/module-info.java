module com.mycompany.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpclient;
    requires mysql.connector.java;
    requires java.sql;
    requires javafx.web;
    opens com.mycompany.app to javafx.fxml;
    exports com.mycompany.app;
    exports CommandLine;
    opens CommandLine to javafx.fxml;
    exports Controller;
    opens Controller to javafx.fxml;
}