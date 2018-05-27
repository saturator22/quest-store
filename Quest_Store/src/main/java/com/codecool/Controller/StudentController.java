package com.codecool.Controller;

import com.codecool.DAO.StudentDAO;
import com.codecool.Model.Student;
import com.codecool.input.UserInput;

import java.util.ArrayList;
import java.util.List;

public class StudentController {

    UserInput userInput = new UserInput();
    StudentDAO studentDAO = new StudentDAO();

    public List<Student> getCrowdFundStudents() {
        List<Student> listOfClass = studentDAO.getStudents();
        List<Student> groupShoppers = new ArrayList<>();
        boolean isPicking = true;

        while(isPicking) {
            for(Student student: listOfClass) {
                System.out.println(student);
            }
            int studentId = userInput.getInt("Type student ID or 0 to back to menu: ");

            if (studentId == 0) {
                isPicking = false;
            } else {
                Student student = studentDAO.getStudentById(studentId);
                groupShoppers.add(student);
            }
        }
        return groupShoppers;
    }

    public int sumGroupShoppersBalance(List<Student> listOfGroupShoppers) {
        int groupBalance = 0;

        for(Student student: listOfGroupShoppers) {
            groupBalance += student.getBalance();
        }
        return groupBalance;
    }
}
