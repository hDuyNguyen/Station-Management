package com.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456789";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
    }

}
