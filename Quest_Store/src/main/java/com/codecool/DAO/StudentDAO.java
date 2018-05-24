package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private static final String
            INSERT_QUERY = "INSERT INTO students (user_id, class_id, level_id, github, balance, earned_coolcoins)\n" +
            "VALUES (?, ?, ?, ?, ?, ?)\n" +
            "ON CONFLICT DO NOTHING;";
    private static final String
            SELECT_QUERY = "SELECT class_name, level_id, github, balance, earned_coolcoins\n" +
            "FROM students\n" +
            "JOIN classes\n" +
            "ON students.class_id = classes.class_id;\n";
    private static final String
            UPDATE_QUERY = "UPDATE students\n" +
            "SET class_id = ?, github = ?, balance = ?, earned_coolcoins = ?" +
            "WHERE user_id = ?";

    public List<Student> getStudents() {
        try {
            List<Student> students = new ArrayList<>();

            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                students.add(extractUserFromRow(resultSet))
            }

            return students;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addStudentData(Student student) {
        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);

            preparedStatement.setInt(1, student.getUserId());
            preparedStatement.setInt(2, student.getClassId());
            preparedStatement.setInt(3, student.getLevelId());
            preparedStatement.setString(4, student.getGithub());
            preparedStatement.setInt(5, student.getBalance());
            preparedStatement.setInt(6, student.getEarned());


            int updateResult = preparedStatement.executeUpdate();

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
