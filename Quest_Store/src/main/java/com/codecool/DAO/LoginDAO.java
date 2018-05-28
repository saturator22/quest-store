package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.LoginData;

import java.sql.*;

public class LoginDAO {

    public LoginData getLoginData(Integer password, String login) {
        LoginData loginData = new LoginData();

        String query = "SELECT user_id, role_id, login FROM users WHERE login = ? AND password = ?;";

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                loginData.setRoleId(resultSet.getInt("role_id"));
                loginData.setUserId(resultSet.getInt("user_id"));
                loginData.setLogin(resultSet.getString("login"));
                connection.close();
                preparedStatement.close();
                return loginData;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loginData;
    }
}
