package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.Quest;

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

    public Student getStudent(Integer userId) {
        // not necessary
    }

    public boolean UpdateArtifact(Artifact artifact) {

    }

    public boolean addQuest(Quest quest) {
        String query = "INSERT INTO quests(quest_category, quest_name, reward) VALUES (?, ?, ?)";

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, quest.getQuestCategory());
            ps.setString(2, quest.getQuestName());
            ps.setInt(3, quest.getQuestReward());

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

    public boolean updateQuest(Quest quest){
        String query = "UPDATE quests SET quest_category=?, quest_name=?, reward=? " +
                "WHERE user_id=" + quest.getQuestId();
        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, quest.getQuestCategory());
            ps.setString(2, quest.getQuestName());
            ps.setInt(3, quest.getQuestReward());

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


}
