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
        boolean insertSuccessful = false;

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, classRoom.getName());

            int intValue = ps.executeUpdate();
            insertSuccessful = executeSuccessful(intValue);

            ps.close();
            connection.close();

            if (insertSuccessful) { return true; }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insertSuccessful;
    }

    public boolean addLevel(Level level) {
        String query = "INSERT INTO Levels (level_req_balance) VALUES (?)";
        boolean insertSuccessful = false;

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, level.getLevelRequiredBalance());

            int intValue = ps.executeUpdate();
            insertSuccessful = executeSuccessful(intValue);

            ps.close();
            connection.close();

            if (insertSuccessful) { return true; }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insertSuccessful;
    }

    private boolean executeSuccessful(int intValue) {
        if (intValue == 1) { return true; }
        return false;
    }
}
