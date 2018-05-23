package com.codecool.DAO;
import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.User;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DAO {

    private static final String
            INSERT = "INSERT INTO users (role_id, first_name, last_name, login, email, password)\n" +
            "VALUES (?, ?, ?, ?, ?, ?)\n" +
            "ON CONFLICT DO NOTHING;";
    private static final String
            SELECT = "SELECT role_name, first_name, last_name, login, email\n" +
                     "FROM users\n" +
                     "INNER JOIN roles\n" +
                     "ON users.role_id = roles.role_id;\n";
    private static final String
            UPDATE = "UPDATE users\n" +
                     "SET role_id = ?, first_name = ?, last_name = ?, login = ?, password = ?, email = ?" +
                     "WHERE user_id = ?";

    abstract List<User> extractUserFromRow();

    public List<User> getPersonalData() {
        List<User> listOfUsers = new ArrayList<>();

        try{
            Connection c = ConnectionBuilder.getConnection();
            PreparedStatement prst = c.prepareStatement(SELECT);

            ResultSet rset = prst.executeQuery();

            while(rset.next()) {
                listOfUsers.add(extractUserFromRow());
            }

            return listOfUsers;

            c.close();
            prst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean addUserData(User user) {
        try {
            Connection c = ConnectionBuilder.getConnection();
            PreparedStatement prst = c.prepareStatement(INSERT);

            prst.setString(2, user.getFirst_name());
            prst.setString(3, user.getLast_name());
            prst.setString(4, user.getLogin());
            prst.setString(5, user.getEmail());
            prst.setString(6, user.getPassword());
//            prst.setString(7, user.getEmail());

            if(user.getRole_id() != null) {
                prst.setInt(1, user.getRole_id());
            } else {
                prst.setNull(1, 0);
            }
            int i = prst.executeUpdate();
            System.out.println(i);
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
