package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MentorDAO implements DAO {

    public boolean addStudent(Student student) {
        String query = "UPDATE students SET class_id=?, github=?, balance=0, earned_coolcoins=0 " +
                "WHERE user_id=" + student.getUser_id();

        addUserData(student);

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, student.getClassId());
            ps.setString(2, student.getGithub());

            int i = ps.executeUpdate();

            ps.close();
            connection.close();

            if(i == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Student getStudent(Integer userId);

    public boolean UpdateArtifact(Artifact artifact);

    public boolean addQuest();

    public boolean updateQuest(Integer questId);


}
