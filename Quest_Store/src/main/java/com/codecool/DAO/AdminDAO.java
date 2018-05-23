package com.codecool.DAO;

import com.codecool.Model.Mentor;

public class AdminDAO implements DAO {

    public boolean addMentor(Mentor mentor) {
        boolean isDone = addUserData(mentor);
        return isDone;
    }

    

}
