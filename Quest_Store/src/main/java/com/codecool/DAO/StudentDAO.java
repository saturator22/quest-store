package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.Student;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

   private static final String
            SELECT_QUERY = "SELECT users.user_id, role_id, first_name, last_name, " +
            "login, email, class_id, level_id, github, balance, earned_coolcoins\n" +
            "FROM students\n" +
            "JOIN users\n" +
            "ON users.user_id = students.user_id;";

    public List<Student> getStudents() {
        try {
            List<Student> students = new ArrayList<>();

            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                students.add(extractUserFromRow(resultSet));
            }

            return students;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    Student extractUserFromRow(ResultSet resultSet) throws SQLException{
        Student student = new Student();

        student.setUserId(resultSet.getInt("user_id"));
        student.setRoleId(resultSet.getInt("role_id"));
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));
        student.setLogin(resultSet.getString("login"));
        student.setEmail(resultSet.getString("email"));
        student.setClassId(resultSet.getInt("class_id"));
        student.setLevelId(resultSet.getInt("level_id"));
        student.setGithub(resultSet.getString("github"));
        student.setBalance(resultSet.getInt("balance"));
        student.setEarned(resultSet.getInt("earned_coolcoins"));

        return student;
    }

    public boolean updateStudentData(Student student) {
        String updateStudentQuery = student.updateStudentQuery();

        try{
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateStudentQuery);

            int updateResult = preparedStatement.executeUpdate();

            if(updateResult == 1) {
                return true;
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean insertStudentData(Student student) {
        String addUserQuery = student.insertUserQuery();
        String addStudentQuery = student.insertStudentQuery();
        String insertQuery = addUserQuery + addStudentQuery;

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

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
