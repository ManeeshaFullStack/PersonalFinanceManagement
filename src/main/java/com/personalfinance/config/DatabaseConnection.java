package com.personalfinance.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@Maneesha:1521/FREE"; // Update as per your DB
    private static final String USER = "SYSTEM"; // Your DB username
    private static final String PASSWORD = "123"; // Your DB password

    static {
        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading Oracle JDBC Driver");
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Oracle Database!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed!");
        }
        return conn;
    }

    public static void main(String[] args) {
        getConnection(); // Test DB connection
    }
}