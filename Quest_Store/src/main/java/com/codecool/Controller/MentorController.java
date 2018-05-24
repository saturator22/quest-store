package com.codecool.Controller;

import com.codecool.DAO.StudentDAO;
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
}
