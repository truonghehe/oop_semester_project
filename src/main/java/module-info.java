module com.mycompany.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpclient;
    requires mysql.connector.java;
    requires java.sql;
    opens com.mycompany.app to javafx.fxml;
    exports com.mycompany.app;
}