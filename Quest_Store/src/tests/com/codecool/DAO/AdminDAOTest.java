package com.codecool.DAO;

import com.codecool.Model.ClassRoom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AdminDAOTest {
    @Test
    @DisplayName("Test adding class")
    public void addClassTest() {
        AdminDAO adminDAO = new AdminDAO();
        MentorDAO mentorDAO = new MentorDAO();

        ClassRoom classRoom = new ClassRoom();
        classRoom.setName("Test classroom");

        int classRoomsBefore = mentorDAO.getClassRooms().size();
        adminDAO.addClass(classRoom);
        int classRoomsAfter = mentorDAO.getClassRooms().size();

        List<ClassRoom> actualClassroom = mentorDAO.getClassRooms().stream()
                .filter(c -> c.getName().equals("Test classroom"))
                .collect(Collectors.toList());

        assertAll(() -> {
            assertEquals(classRoomsBefore + 1, classRoomsAfter);
            assertEquals(classRoom.getName(), actualClassroom.get(0).getName());
        });
    }
}