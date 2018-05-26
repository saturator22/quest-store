package com.codecool.Connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionBuilder {
    private static final String URL = "jdbc:postgresql://localhost:5432/Quest_Store";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String USERNAME = "filip";
    private static final String PASSWORD = "Myczkas91!";

    public static Connection getConnection() {
        Connection c = null;

        try {
            Class.forName(DRIVER);
            c = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return c;
    }
}
