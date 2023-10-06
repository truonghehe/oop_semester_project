package com.mycompany.app.DatabaseDict;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
    public Connection getMyConnection() throws SQLException, ClassNotFoundException {
        return MySQLConnUtils.getMySQLConnection();
    }
}
