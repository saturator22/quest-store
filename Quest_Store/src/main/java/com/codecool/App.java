package com.codecool;

import com.codecool.DAO.DAO;
import com.codecool.DAO.StudentDAO;
import com.codecool.Model.Mentor;
import com.codecool.Model.Student;
import com.codecool.Model.User;

import java.util.List;
public class App 
{
    public static void main( String[] args )
    {
        StudentDAO studentDAO = new StudentDAO();
        Student student = new Student();
        student.setRoleId(1);
        student.setFirstName("MILOSZ");
        student.setLastName("Romanowski");
        student.setLogin("bartkp");
        student.setPassword(123);
        student.setEmail("wfwrfw@wsrdzd.com");
        student.setLevelId(1);
        student.setGithub("fwefwef");
        student.setClassId(1);


        studentDAO.insertStudentData((Student) student);
        List<Student> studentsList = studentDAO.getStudents();
        for(Student stud: studentsList) {
            System.out.println(stud);
        }
    }
}
