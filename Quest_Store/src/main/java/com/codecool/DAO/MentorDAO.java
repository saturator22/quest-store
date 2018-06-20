package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.ClassRoom;
import com.codecool.Model.Mentor;
import com.codecool.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MentorDAO extends UserDAO {

    public Mentor extractUserFromRow(ResultSet resultSet) throws SQLException {
        Mentor mentor = new Mentor();

        mentor.setUserId(resultSet.getInt("user_id"));
        mentor.setFirstName(resultSet.getString("first_name"));
        mentor.setLastName(resultSet.getString("last_name"));
        mentor.setEmail(resultSet.getString("email"));
        mentor.setRoleId(resultSet.getInt("role_id"));
        mentor.setLogin(resultSet.getString("login"));

        return mentor;
    }

    public boolean addMentorToClassRoom(Mentor mentor, Integer id) {
        String
                addMentorToClassRoomQuery = "INSERT INTO mentors_classes (user_id, class_id)" +
                                            "VALUES(?, ?);";
        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(addMentorToClassRoomQuery);

            preparedStatement.setInt(1, mentor.getUserId());
            preparedStatement.setInt(2, id);

            int updateResult = preparedStatement.executeUpdate();

            if (updateResult == 1) {
                return true;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ClassRoom extractClassRoomFromRow(ResultSet resultSet) throws SQLException{
        ClassRoom classRoom = new ClassRoom();

        classRoom.setClassId(resultSet.getInt("class_id"));
        classRoom.setName(resultSet.getString("class_name"));

        return classRoom;
    }

    public List<ClassRoom> getClassRooms() {
        String
                getMentorsClassesQuery = "SELECT * FROM classes";

        List<ClassRoom> classRooms = new ArrayList<>();

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getMentorsClassesQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                classRooms.add(extractClassRoomFromRow(resultSet));
            }

            connection.close();
            preparedStatement.close();

            return classRooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ClassRoom> getMentorsClasses(Mentor mentor) {
        String
                getMentorsClassesQuery = "SELECT * FROM classes\n" +
                                         "JOIN mentors_classes\n" +
                                         "ON mentors_classes.class_id = classes.class_id\n" +
                                         "JOIN mentors\n" +
                                         "ON mentors.user_id = mentors_classes.user_id\n" +
                                         "WHERE mentors.user_id = ?;";

        List<ClassRoom> classRooms = new ArrayList<>();

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getMentorsClassesQuery);

            preparedStatement.setInt(1, mentor.getUserId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                classRooms.add(extractClassRoomFromRow(resultSet));
            }

            connection.close();
            preparedStatement.close();

            return classRooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
                mentor = extractUserFromRow(resultSet);
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
