package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.Artifact;
import com.codecool.Model.ShopObject;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArtifactDAO {



    public Artifact getArtifactById(Integer artifactId) {
        String query = "SELECT * FROM artifacts WHERE artifact_id =" + artifactId;
        Artifact artifact = null;

        try {
            Connection connection = ConnectionBuilder.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                artifact = extractArtifact(resultSet);
            }

            statement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artifact;
    }

    public boolean updateArtifact(Artifact artifact) {
        String query = "UPDATE artifacts SET name=?, price=?, description=? " +
                "WHERE artifact_id=" + artifact.getArtifactId();

        return sendQuestQuery(artifact, query);
    }

    public boolean addArtifact(Artifact artifact) {
        String query = "INSERT INTO artifacts(name, price, description) VALUES (?, ?, ?)";

        return sendQuestQuery(artifact, query);
    }

    private Artifact extractArtifact(ResultSet resultSet) throws SQLException {
        Artifact artifact = new Artifact();

        artifact.setArtifactId(resultSet.getInt("artifact_id"));
        artifact.setDescription(resultSet.getString("artifact_description"));
        artifact.setName(resultSet.getString("artifact_name"));
        artifact.setPrice(resultSet.getInt("artifact_price"));
        artifact.setCategory(resultSet.getString("artifact_category"));
        return artifact;
    }

    private boolean sendQuestQuery(Artifact artifact, String query) {
        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, artifact.getName());
            ps.setInt(2, artifact.getPrice());
            ps.setString(3, artifact.getDescription());
            int i = ps.executeUpdate();

            ps.close();
            connection.close();

            if (i == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Artifact> getStudentArtifacts(Integer userId) {
        List<Artifact> studentArtifacts = new ArrayList<>();

        String query = "SELECT artifacts.*, students_artifacts.user_id " +
                "FROM artifacts JOIN students_artifacts " +
                "ON students_artifacts.artifact_id = artifacts.artifact_id " +
                "WHERE students_artifacts.user_id = " + userId;

        try {
            Connection connection = ConnectionBuilder.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                studentArtifacts.add(extractArtifact(resultSet));
            }

            statement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentArtifacts;
    }

    public boolean addArtifactToStudent(Artifact artifact, Integer user_id) {
        String query = "INSERT INTO students_artifacts(user_id, artifact_id, is_used) VALUES (?, ?, ?)";

        try {
            Connection connection = ConnectionBuilder.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user_id);
            ps.setInt(2, artifact.getArtifactId());
            ps.setBoolean(3, false);

            int i = ps.executeUpdate();

            ps.close();
            connection.close();

            if (i == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
