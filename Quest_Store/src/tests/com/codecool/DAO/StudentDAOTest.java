package com.codecool.DAO;

import com.codecool.Model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOTest {

    @Test
    @DisplayName("Test adding student to the database")
    public void testAddingNewStudent() {
        StudentDAO studentDAO = new StudentDAO();
        Student expectedStudent = new Student();
        expectedStudent.setFirstName("Eryk");
        expectedStudent.setLastName("Dobkowski");
        expectedStudent.setRoleId(1);
        expectedStudent.setLogin("edobkowski");
        expectedStudent.setEmail("erykoslawdob@gmail.com");
        expectedStudent.setPassword("haslo");
        expectedStudent.setGithub("edobkowski.pl");
        expectedStudent.setClassId(1);
        expectedStudent.setLevelId(1);
        expectedStudent.setBalance(123);
        expectedStudent.setEarned(123);

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

}