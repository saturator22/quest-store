package com.codecool.Connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionBuilder {
    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/Quest_Store",
                            "filip", "Myczkas91!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        return c;
    }
}
