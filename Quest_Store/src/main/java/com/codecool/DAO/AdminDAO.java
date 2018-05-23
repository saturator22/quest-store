package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.ClassRoom;
import com.codecool.Model.Level;
import com.codecool.Model.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDAO extends DAO {

    public boolean addMentor(Mentor mentor) {
        return addUserData(mentor);
    }

    public boolean addClass(ClassRoom classRoom) {
        String query = "INSERT INTO Classes(name) VALUES(?)";
        boolean updateSuccessful = false;

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, classRoom.getName());

            int intValue = ps.executeUpdate();
            updateSuccessful = updateQuerySuccessful(intValue);

            ps.close();
            connection.close();

            if (updateSuccessful) { return true; }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateSuccessful;
    }

    private boolean updateQuerySuccessful(int intValue) {
        if (intValue == 1) { return true; }
        return false;
    }

    public boolean addLevel(Level level) {
        String query = "INSERT INTO Levels (level_req_balance) VALUES (?)";
        boolean updateSuccessful = false;
        
    }

}
