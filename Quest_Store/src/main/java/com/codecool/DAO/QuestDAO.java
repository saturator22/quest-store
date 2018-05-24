package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.Quest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestDAO {

    public List<Quest> getStudentQuests(String ownerId) {
        List<Quest> studentQuests = new ArrayList<>();

        String query = "SELECT quests.*, students_quests.user_id " +
                "FROM quests JOIN students_quests " +
                "ON students_quests.quest_id = quests.quest_id " +
                "JOIN quests ON quests.user_id = students_quests.user_id WHERE students_quests.user_id = " + ownerId;

        try {
            Connection connection = ConnectionBuilder.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                studentQuests.add(extractQuest(resultSet));
            }

            statement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentQuests;
    }

    private Quest extractQuest(ResultSet resultSet) throws SQLException {
        Quest quest = new Quest();

        quest.setQuestId(resultSet.getInt("quest_id"));
        //TODO missing column        quest.setDescription(resultSet.getString("description"));
        quest.setQuestName(resultSet.getString("quest_name"));
        quest.setQuestReward(resultSet.getInt("quest_reward"));
        quest.setQuestCategory(resultSet.getString("quest_category"));
        quest.setQuestOwnerId(resultSet.getInt("user_id"));

        return quest;
    }

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

        return sendQuestQuery(query, quest);
    }

    public boolean updateQuest(Quest quest) {
        String query = "UPDATE quests SET quest_category=?, quest_name=?, reward=? " +
                "WHERE user_id=" + quest.getQuestId();

        return sendQuestQuery(query, quest);
    }
}
