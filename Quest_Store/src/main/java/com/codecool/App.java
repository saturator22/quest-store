package com.codecool;

import com.codecool.Controller.AdminController;
import com.codecool.Controller.MentorController;
import com.codecool.DAO.DAO;
import com.codecool.DAO.StudentDAO;
import com.codecool.Model.Mentor;
import com.codecool.Model.Student;
import com.codecool.Model.User;
import com.codecool.input.UserInput;

import java.util.List;
public class App 
{
    public static void main( String[] args )
    {
        UserInput userInput = new UserInput();
        int option = -1;

        while (option != 0) {
            System.out.println("1. Admin: create mentor account\n" +
                    "2. Admin: check mentor account\n" +
                    "3. Admin: create class\n" +
                    "4. Admin: set lvl experience\n" +
                    "5. Mentor: add Student\n" +
                    "6. Mentor: create Quest\n" +
                    "7. Mentor: create artifact\n" +
                    "8. Mentor: update artifact\n" +
                    "9. Mentor: add artifact to store\n" +
                    "10. Mentor: set quest category\n" +
                    "11. Student: see exp lvl" +
                    "12. Student: see account info\n");

            if (option == 1) {
                AdminController adminController = new AdminController();
                adminController.addMentor();
            } else if (option == 2) {
                AdminController adminController = new AdminController();
                adminController.viewMentorAccount();
            } else if (option == 3) {
                AdminController adminController = new AdminController();
                adminController.addClass();
            } else if (option == 4) {
                AdminController adminController = new AdminController();
                adminController.addLevel();
            } else if (option == 5) {
                MentorController mentorController = new MentorController();
                mentorController.addStudent();
            } else if (option == 6) {
                MentorController mentorController = new MentorController();
                mentorController.addQuest();
            } else if (option == 7) {
                MentorController mentorController = new MentorController();
                mentorController.addArtifact();
            } else if (option == 8) {
                MentorController mentorController = new MentorController();
                mentorController.updateArtifact();
            } else if (option == 9) {
                MentorController mentorController = new MentorController();
                mentorController.setQuestCategory();
            }
        }
    }
}
