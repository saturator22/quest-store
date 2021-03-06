package com.codecool.Controller;

import com.codecool.DAO.ArtifactDAO;
import com.codecool.DAO.MentorDAO;
import com.codecool.DAO.QuestDAO;
import com.codecool.DAO.StudentDAO;
import com.codecool.Model.*;
import com.codecool.input.UserInput;

import java.util.List;

public class MentorController implements IUserCreateable {

    private static final int STUDENT_ROLE = 3;
    private static final int STARTING_LVL = 1;

    public List<ClassRoom> getMentorsClassRooms() {
        MentorDAO mentorDAO = new MentorDAO();

        return mentorDAO.getMentorsClasses(mentorDAO.getMentorById(21));
    }



    public void addStudent() {
        UserInput userInput = new UserInput();
        User user = new Student();

        user = setUserAttributes(user);
        user.setRoleId(STUDENT_ROLE);

        Student student = (Student) user;
        student.setClassId(userInput.getInt("Enter class id: "));
        student.setGithub(userInput.getString("Enter github: "));
        student.setLevelId(STARTING_LVL);

        StudentDAO studentDAO = new StudentDAO();
        studentDAO.insertStudentData(student);
    }

    public void addQuest() {
        UserInput userInput = new UserInput();
        Quest quest = new Quest();

        quest.setQuestCategory(userInput.getString("Enter category: "));
        quest.setQuestName(userInput.getString("Enter name: "));
        quest.setQuestDescription(userInput.getString("Enter description: "));
        quest.setQuestReward(userInput.getInt("Enter reward: "));

        QuestDAO questDAO = new QuestDAO();
        questDAO.addQuest(quest);
    }

    public void setQuestCategory() {
        UserInput userInput = new UserInput();
        QuestDAO questDAO = new QuestDAO();

        Quest quest = questDAO.getQuestByQuestId(userInput.getInt("Enter id: "));
        quest.setQuestCategory(userInput.getString("Enter new category: "));

        questDAO.updateQuest(quest);
    }

    public void addArtifact() {

        Artifact artifact = new Artifact();
        artifact = setArtifactAttributes(artifact);

        ArtifactDAO artifactDAO = new ArtifactDAO();
        artifactDAO.addArtifact(artifact);
    }

    public void updateArtifact() {
        UserInput userInput = new UserInput();
        ArtifactDAO artifactDAO = new ArtifactDAO();

        Artifact artifact = artifactDAO.getArtifactById(userInput.getInt("Enter id: "));
        artifact = setArtifactAttributes(artifact);

        artifactDAO.updateArtifact(artifact);
    }

    private Artifact setArtifactAttributes(Artifact artifact) {
        UserInput userInput = new UserInput();

        artifact.setName(userInput.getString("Enter name: "));
        artifact.setPrice(userInput.getInt("Enter price: "));
        artifact.setDescription(userInput.getString("Enter description: "));

        return artifact;
    }
}
