package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.Quest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuestDAO {
    //TODO mark student's quest as done


    private boolean sendQuestQuery(String query, Quest quest) {
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

    public boolean addQuest(Quest quest) {
        String query = "INSERT INTO quests(quest_category, quest_name, reward) VALUES (?, ?, ?)";
        sendQuestQuery(query, quest);
    }

    public boolean updateQuest(Quest quest) {
        String query = "UPDATE quests SET quest_category=?, quest_name=?, reward=? " +
                "WHERE user_id=" + quest.getQuestId();
        sendQuestQuery(query, quest);
    }
}
