package com.codecool.DAO;

import com.codecool.Model.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOTest {

    @AfterAll
    public static void cleanUp() {
        StudentDAO studentDAO = new StudentDAO();
        Student student = createStudent();
        studentDAO.removeStudent(student);
    }

    @Test
    @DisplayName("Test adding student to the database")
    public void testAddingNewStudent() {
        StudentDAO studentDAO = new StudentDAO();
        Student expectedStudent = createStudent();

        int studentListSizeBefore = studentDAO.getStudents().size();
        studentDAO.insertStudentData(expectedStudent);
        int studentListSizeAfter = studentDAO.getStudents().size();

        List<Student> actualStudent = studentDAO.getStudents().stream()
                .filter(c -> c.getName().equals("Eryk Dobkowski"))
                .collect(Collectors.toList());

        assertAll(() -> {
            assertEquals(studentListSizeBefore + 1, studentListSizeAfter);
            assertEquals(expectedStudent.getName(), actualStudent.get(0).getName());
        });
    }

    @Test
    @DisplayName("Test editing existing student")
    public void testEditStudent() {
        StudentDAO studentDAO = new StudentDAO();
        Student expectedStudent = getStudentByLogin("edobkowski");
        expectedStudent.setLastName("Lastname");

        int studentListSizeBefore = studentDAO.getStudents().size();
        studentDAO.updateStudentData(expectedStudent);
        int studentListSizeAfter = studentDAO.getStudents().size();

        Student actualStudent = getStudentByLogin("edobkowski");

        assertAll(() -> {
            assertEquals(studentListSizeBefore, studentListSizeAfter);
            assertEquals(expectedStudent.getName(), actualStudent.getName());
        });
    }

    private static Student createStudent() {
        Student student = new Student();

        student.setFirstName("Eryk");
        student.setLastName("Dobkowski");
        student.setRoleId(1);
        student.setLogin("edobkowski");
        student.setEmail("erykoslawdob@gmail.com");
        student.setPassword("haslo");
        student.setGithub("edobkowski.pl");
        student.setClassId(1);
        student.setLevelId(1);
        student.setBalance(123);
        student.setEarned(123);

        return student;
    }

    private Student getStudentByLogin(String login) {
        StudentDAO studentDAO = new StudentDAO();
        return studentDAO.getStudents().stream()
                .filter(c -> c.getLogin().equals(login))
                .collect(Collectors.toList())
                .get(0);
    }
}