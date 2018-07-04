package com.codecool.DAO;

import com.codecool.Model.Quest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestDAOTest {
    @Test
    @DisplayName("Test adding Quest to the database")
    public void addQuestTest() {
        Quest expectedQuest = new Quest();
        expectedQuest.setQuestCategory("Personal");
        expectedQuest.setQuestName("Test quest");
        expectedQuest.setQuestDescription("Testing add quest functionality");
        expectedQuest.setQuestReward(500);

        QuestDAO questDAO = new QuestDAO();
        assertTrue(questDAO.addQuest(expectedQuest));
    }
}