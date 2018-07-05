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

public class StudentDAO extends UserDAO{

   private static final String
            SELECT_QUERY =  "SELECT users.user_id, role_id, first_name, last_name, " +
                            "login, email, class_id, level_id, github, balance, earned_coolcoins\n" +
                            "FROM students\n" +
                            "JOIN users\n" +
                            "ON users.user_id = students.user_id;";

    public List<Student> getStudentsByClassName(String className) {
        String
                findByIdQuery = "SELECT users.user_id, role_id, first_name, last_name, login, email, password, " +
                "students.class_id, level_id, github, balance, earned_coolcoins\n" +
                "FROM students\n" +
                "JOIN users\n" +
                "ON users.user_id = students.user_id\n" +
                "JOIN classes\n" +
                "ON students.class_id = classes.class_id\n"+
                "WHERE classes.class_name = ?;";

        List<Student> studentsList = new ArrayList<>();

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(findByIdQuery);

            preparedStatement.setString(1, className);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                studentsList.add(extractUserFromRow(resultSet));
            }

            preparedStatement.close();
            connection.close();

            return studentsList;


        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

   public Student getStudentById(Integer id) {
        String
                findByIdQuery = "SELECT users.user_id, role_id, first_name, last_name, login, email, password, " +
                                "class_id, level_id, github, balance, earned_coolcoins\n" +
                                "FROM students\n" +
                                "JOIN users\n" +
                                "ON users.user_id = students.user_id\n" +
                                "WHERE users.user_id = ?;";
        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(findByIdQuery);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Student student = extractUserFromRow(resultSet);
                connection.close();
                preparedStatement.close();
                return student;
            } else {
                return null;
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

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

    public Student extractUserFromRow(ResultSet resultSet) throws SQLException{
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
       String
            updateStudentQuery =
                    "UPDATE users\n" +
                    "SET role_id = ?, first_name = ?, last_name = ?, login = ?, password = ?, email = ?\n" +
                    "WHERE user_id = ?";

        try{
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateStudentQuery);

            preparedStatement.setInt(1, student.getRoleId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setString(4, student.getLogin());
            preparedStatement.setString(5, student.getPassword());
            preparedStatement.setString(6, student.getEmail());
            preparedStatement.setInt(7, student.getUserId());

            int updateResult = preparedStatement.executeUpdate();

            if(updateResult == 1) {
                return true;
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateBalance(Student student) {
        String
                updateStudentQuery =
                "UPDATE students\n" +
                        "SET balance = ?, earned_coolcoins = ?\n" +
                        "WHERE user_id = ?";

        try{
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateStudentQuery);

            preparedStatement.setInt(1, student.getBalance());
            preparedStatement.setInt(2, student.getEarned());
            preparedStatement.setInt(3, student.getUserId());

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
        String
                insertQuery =
                "BEGIN;" +
                "INSERT INTO users (role_id, first_name, last_name, login, email, password)\n" +
                "VALUES (?, ?, ?, ?, ?, ?);\n" +
                "INSERT INTO students (user_id, class_id, level_id, github, balance, earned_coolcoins)\n" +
                "VALUES ((SELECT user_id FROM users WHERE login = ?), ?, ?, ?, ?, ?);\n" +
                "COMMIT;";

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, student.getRoleId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setString(4, student.getLogin());
            preparedStatement.setString(5, student.getEmail());
            preparedStatement.setString(6, student.getPassword());
            preparedStatement.setString(7, student.getLogin());
            preparedStatement.setInt(8, student.getClassId());
            preparedStatement.setInt(9, student.getLevelId());
            preparedStatement.setString(10, student.getGithub());
            preparedStatement.setInt(11, student.getBalance());
            preparedStatement.setInt(12, student.getEarned());

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

    public boolean removeStudent(Student student) {
        String
                deleteQuery =
                "BEGIN;" +
                "DELETE FROM users WHERE login=?;" +
                "DELETE FROM students WHERE user_id=(SELECT user_id FROM users WHERE login = ?);" +
                "COMMIT;";
        try {
            Connection connection = ConnectionBuilder.getConnection();
            String login = student.getLogin();

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, login);

            int deleteResult = preparedStatement.executeUpdate();

            if(deleteResult == 1) {
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
