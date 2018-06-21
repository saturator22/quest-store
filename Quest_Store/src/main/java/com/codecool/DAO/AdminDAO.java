package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO extends UserDAO {

    public boolean addMentor(Mentor mentor) {
        return addUserData(mentor);
    }

    public User extractUserFromRow(ResultSet resultSet) throws SQLException {
        Admin admin = new Admin();

        admin.setUserId(resultSet.getInt("user_id"));
        admin.setRoleId(resultSet.getInt("role_id"));
        admin.setFirstName(resultSet.getString("first_name"));
        admin.setLastName(resultSet.getString("last_name"));
        admin.setLogin(resultSet.getString("login"));
        admin.setPassword(resultSet.getString("password"));
        admin.setEmail(resultSet.getString("email"));

        return admin;
    }

    public boolean addClass(ClassRoom classRoom) {
        String query = "INSERT INTO Classes(class_name) VALUES(?)";
        boolean insertSuccessful = false;

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, classRoom.getName());

            int intValue = ps.executeUpdate();
            insertSuccessful = executeSuccessful(intValue);

            ps.close();
            connection.close();

            if (insertSuccessful) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insertSuccessful;
    }

    public boolean addLevel(Integer levelId, Integer requiredBalance) {
        String query = "INSERT INTO levels (level_id, level_req_balance) VALUES (?, ?)";
        boolean insertSuccessful = false;

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, levelId);
            ps.setInt(2, requiredBalance);

            int intValue = ps.executeUpdate();
            insertSuccessful = executeSuccessful(intValue);

            ps.close();
            connection.close();

            if (insertSuccessful) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insertSuccessful;
    }

    public List <Level> getLevels() {
        List <Level> levels = new ArrayList <>();
        String query = "SELECT * FROM levels;";

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Level level = new Level();
                level.setLevelId(rs.getInt("level_id"));
                level.setLevelRequiredBalance(rs.getInt("level_req_balance"));
                levels.add(level);
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return levels;
    }

    private boolean executeSuccessful(int intValue) {
        if (intValue == 1) {
            return true;
        }
        return false;
    }
}
