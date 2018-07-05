package com.codecool.DAO;

import com.codecool.Model.Quest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    @DisplayName("Test adding Group Quest to the database")
    public void addGroupQuestTest() {
        Quest expectedQuest = new Quest();
        expectedQuest.setQuestCategory("Group");
        expectedQuest.setQuestName("Test group quest");
        expectedQuest.setQuestDescription("Testing add quest functionality");
        expectedQuest.setQuestReward(600);

        QuestDAO questDAO = new QuestDAO();
        assertTrue(questDAO.addQuest(expectedQuest));
    }

    @Test
    @DisplayName("Test editing quest")
    public void editQuestTest() {
        QuestDAO questDAO = new QuestDAO();

        Quest questToAdd = new Quest();
        questToAdd.setQuestCategory("Personal");
        questToAdd.setQuestName("Quest to edit");
        questToAdd.setQuestDescription("Testing edit quest functionality");
        questToAdd.setQuestReward(100);

        questDAO.addQuest(questToAdd);

        Quest questToEdit;

        List<Quest> matchingQuests;
        matchingQuests = questDAO.getAvailableQuests().stream()
                .filter(q -> q.getQuestName().equals("Quest to edit"))
                .collect(Collectors.toList());

        questToEdit = matchingQuests.get(0);

        questToEdit.setQuestName("Edited quest name");
        questDAO.updateQuest(questToEdit);

        Quest editedQuest = questDAO.getAvailableQuests().stream()
                .filter(q -> q.getQuestName().equals("Edited quest name"))
                .collect(Collectors.toList())
                .get(0);

        assertEquals(questToEdit, editedQuest);
    }
}