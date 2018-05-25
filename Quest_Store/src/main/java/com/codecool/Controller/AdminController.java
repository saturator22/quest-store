package com.codecool.Controller;

import com.codecool.DAO.AdminDAO;
import com.codecool.DAO.MentorDAO;
import com.codecool.Model.ClassRoom;
import com.codecool.Model.Mentor;
import com.codecool.Model.User;
import com.codecool.View.View;
import com.codecool.input.UserInput;

public class AdminController implements IUserCreateable {

    private static final int MENTOR_ROLE = 2;

    public void addMentor() {
        User user = new Mentor();
        user = setUserAttributes(user);
        user.setRoleId(MENTOR_ROLE);

        Mentor mentor = (Mentor) user;
        MentorDAO mentorDAO = new MentorDAO();
        mentorDAO.insertMentorData(mentor);
    }

    public void viewMentorAccount() {
        UserInput userInput = new UserInput();
        int mentor_id = userInput.getInt("Enter mentor id: ");

        View view = new View();
        MentorDAO mentorDAO = new MentorDAO();
        view.displayUser(mentorDAO.getMentorById(mentor_id));
    }

    public void  addClass() {
        UserInput userInput = new UserInput();

        ClassRoom classRoom = new ClassRoom();
        classRoom.setName(userInput.getString("Enter class name: "));

        AdminDAO adminDAO = new AdminDAO();
        adminDAO.addClass(classRoom);
    }

//    public void addLevel() {
//        UserInput userInput = new UserInput();
//        AdminDAO adminDAO = new AdminDAO();
//        adminDAO.addLevel(userInput.getInt("Enter level required balance: "));
//    }
}