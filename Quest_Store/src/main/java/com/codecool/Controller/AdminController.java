package com.codecool.Controller;

import com.codecool.Model.Mentor;
import com.codecool.Model.User;
import com.codecool.input.UserInput;

public class AdminController implements IUserCreateable {

    private static final int MENTOR_ROLE = 2;

    public void addMentor() {
        UserInput userInput = new UserInput();
        User mentor = new Mentor();
        mentor = setUserAttibutes(mentor);
        mentor.setRoleId(MENTOR_ROLE);

        mentor = (Mentor) mentor;
        MentorDAO mentorDAO = new MentorDAO();
        mentorDAO.addMentor(mentor);
    }

    public void viewMentorAccount() {
        UserInput userInput = new UserInput();
        int mentor_id = userInput.getInt("Enter mentor id: ");

        View view = new View();
        MentorDAO mentorDAO = new MentorDAO();
        view.displayUser(mentorDAO.getMentorById(mentor_id));
    }

}
