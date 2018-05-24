package com.codecool.Controller;

import com.codecool.DAO.QuestDAO;
import com.codecool.DAO.StudentDAO;
import com.codecool.Model.Quest;
import com.codecool.Model.Student;
import com.codecool.Model.User;
import com.codecool.input.UserInput;

public class MentorController implements IUserCreateable {

    private static final int STUDENT_ROLE = 3;

    public void addStudent() {
        UserInput userInput = new UserInput();
        User user = new Student();

        user = setUserAttributes(user);
        user.setRoleId(STUDENT_ROLE);

        Student student = (Student) user;
        student.setClassId(userInput.getInt("Enter class id: "));
        student.setGithub(userInput.getString("Enter github: "));

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
}
