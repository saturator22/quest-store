package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class UserDAO implements DAO{

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



    public boolean updatePersonalData(User user) {
        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);

            preparedStatement.setInt(1, user.getRoleId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setInt(5, user.getPassword());
            preparedStatement.setString(6, user.getEmail());

            int executeResult = preparedStatement.executeUpdate();
            System.out.println(executeResult);
            if (executeResult == 1) {
                return true;
            }

            connection.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getPersonalData() {
        List<User> listOfUsers = new ArrayList<>();

        try{
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                listOfUsers.add(extractUserFromRow(resultSet));
            }

            connection.close();
            preparedStatement.close();

            return listOfUsers;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean addUserData(User user) {
        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);

            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setInt(6, user.getPassword());

            if(user.getRoleId() != null) {
                preparedStatement.setInt(1, user.getRoleId());
            } else {
                preparedStatement.setNull(1, 0);
            }
            int updateResult = preparedStatement.executeUpdate();
            System.out.println(updateResult);
            if(updateResult == 1) {
                return true;
            }

            preparedStatement.close();
            connection.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
