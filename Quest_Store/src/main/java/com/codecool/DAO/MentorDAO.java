package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.Mentor;
import com.codecool.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MentorDAO extends DAO {


    private Mentor extractMentor (ResultSet resultSet) throws SQLException {
        Mentor mentor = new Mentor();

        mentor.setUserId(resultSet.getInt("user_id"));
        mentor.setFirstName(resultSet.getString("first_name"));
        mentor.setLastName(resultSet.getString("last_name"));
        mentor.setEmail(resultSet.getString("email"));
        mentor.setRoleId(resultSet.getInt("role_id"));
        mentor.setLogin(resultSet.getString("login"));

        return mentor;
    }

    public User extractUserFromRow(ResultSet resultSet) throws SQLException {
        return extractMentor(resultSet);
    }
}
