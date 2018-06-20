package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.LoginData;

import java.sql.*;

public class LoginDAO {

    public LoginData getLoginData(String email) {
        LoginData loginData = null;

        String query = "SELECT user_id, role_id, login, email, password FROM users WHERE email = ?;";

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                loginData = new LoginData(
                        resultSet.getInt("role_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
                connection.close();
                preparedStatement.close();
                return loginData;
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
        return loginData;
    }

    public LoginData getLoginData(Integer id) {
        LoginData loginData = null;

        String query = "SELECT user_id, role_id, login, email, password FROM users WHERE user_id = ?;";

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                loginData = new LoginData(
                        resultSet.getInt("role_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
                connection.close();
                preparedStatement.close();
                return loginData;
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
        return loginData;
    }

//    private LoginData deserialize(ResultSet rs) {
            //TODO: Implement!!!
//    }
}
