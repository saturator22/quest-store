package com.codecool.DAO;
import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.User;

import java.sql.*;

public abstract class DAO {

    private static final String
            INSERT = "INSERT INTO users (role_id, first_name, last_name, login, password, email)\n" +
            "SELECT ?, ?, ?, ?, ?, ?\n" +
            "WHERE NOT EXISTS (SELECT 1 FROM mentors WHERE email = ?);";
    private static final String
            SELECT = "SELECT role_name, first_name, last_name, login, email\n" +
                     "FROM users\n" +
                     "INNER JOIN roles\n" +
                     "ON users.role_id = roles.role_id;\n";
    private static final String
            UPDATE = "UPDATE users\n" +
                     "SET role_id = ?, first_name = ?, last_name = ?, login = ?, password = ?, email = ?" +
                     "WHERE user_id = ?";

    public static boolean addUserData(User user) {
        try {
            Connection c = ConnectionBuilder.getConnection();
            PreparedStatement prst = c.prepareStatement(INSERT);

            prst.setString(2, user.getFirst_name());
            prst.setString(3, user.getLast_name());
            prst.setString(4, user.getLogin());
            prst.setString(5, user.getEmail());
            prst.setString(6, user.getPassword());

            if(user.getRole_id() != null) {
                prst.setInt(1, user.getRole_id());
            } else {
                prst.setNull(1, 0);
            }
            System.out.println(prst);
            int i = prst.executeUpdate();

            if(i == 1) {
                return true;
            }

            prst.close();
            c.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
