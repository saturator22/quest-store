package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.Quest;
import com.codecool.Model.ShopObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuestDAO {

    public Set<ShopObject> getAvailableQuests() {
        Set<ShopObject> allQuests = new HashSet<>();
        String query = "SELECT * FROM quests";

        try {
            Connection connection = ConnectionBuilder.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Quest quest = extractQuest(resultSet);
                allQuests.add(quest);
            }

            statement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allQuests;
    }

    public Quest getQuestByQuestId(Integer questId) {
        String query = "SELECT * FROM quests WHERE quest_id =" + questId;
        Quest quest = null;

        try {
            Connection connection = ConnectionBuilder.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                quest = extractQuest(resultSet);
            }

            statement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quest;
    }

    public List<Quest> getStudentQuests(Integer ownerId) {
        List<Quest> studentQuests = new ArrayList<>();

        String query = "SELECT quests.*, students_quests.user_id " +
                "FROM quests JOIN students_quests " +
                "ON students_quests.quest_id = quests.quest_id " +
                "WHERE students_quests.user_id = " + ownerId;

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
        quest.setQuestDescription(resultSet.getString("quest_description"));
        quest.setQuestName(resultSet.getString("quest_name"));
        quest.setQuestReward(resultSet.getInt("quest_reward"));
        quest.setQuestCategory(resultSet.getString("quest_category"));
//        quest.setQuestOwnerId(resultSet.getInt("user_id"));

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
        String query = "INSERT INTO quests(quest_category, quest_name, quest_reward) VALUES (?, ?, ?)";

        return sendQuestQuery(query, quest);
    }

    public boolean updateQuest(Quest quest) {
        String query = "UPDATE quests SET quest_category=?, quest_name=?, reward=? " +
                "WHERE user_id=" + quest.getQuestId();

        return sendQuestQuery(query, quest);
    }

    public boolean addQuestToStudent(Quest quest, Integer user_id) {
        String query = "INSERT INTO students_quests(user_id, quest_id, is_completed) VALUES (?, ?, ?)";

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user_id);
            ps.setInt(2, quest.getQuestId());
            ps.setBoolean(3, false);

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


}
