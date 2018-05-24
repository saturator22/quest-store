package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.Mentor;
import com.codecool.Model.User;

import java.sql.*;

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

    public boolean insertMentorData(Mentor mentor) {
        String addPersonalData = "INSERT INTO users (role_id, first_name, last_name, login, email, password)\n" +
                "VALUES (" + mentor.getRoleId() + ", '" + mentor.getFirstName() + "', '" + mentor.getLastName() + "', '" +
                mentor.getLogin() + "', '" + mentor.getEmail() + "', " +
                mentor.getPassword() + ")\n" +
                "ON CONFLICT DO NOTHING;\n";

        String addMentorData = "INSERT INTO mentors (user_id)\n" +
                "VALUES ((SELECT user_id FROM users WHERE login = '" + mentor.getLogin() + "'))\n" +
                "ON CONFLICT DO NOTHING;";

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(addPersonalData + addMentorData);

            int i = ps.executeUpdate();

            ps.close();
            connection.close();

            if (i == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Mentor getMentorById(Integer mentorID) {
        String query = "SELECT user_id, role_id, first_name, last_name, login, email\n" +
                "FROM users\n" +
                "WHERE user_id=" + mentorID;

        Mentor mentor = null;

        try {
            Connection connection = ConnectionBuilder.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                mentor = extractMentor(resultSet);
            }

            statement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mentor;
    }
}
