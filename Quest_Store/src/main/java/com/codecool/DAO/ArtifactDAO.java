package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.Artifact;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        artifact.setDescription(resultSet.getString("description"));
        artifact.setName(resultSet.getString("name"));
        artifact.setPrice(resultSet.getInt("price"));
//        artifact.setOwner_id(resultSet.getInt("user_id"));

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

    public List<Artifact> getStudentArtifacts(String userId, String artifactId) {
        List<Artifact> studentArtifacts = new ArrayList<>();

        String query = "SELECT artifacts.*, students_artifacts.user_id " +
                "FROM artifacts JOIN students_artifacts " +
                "ON students_artifacts.artifact_id = artifacts.artifact_id " +
                "JOIN students ON students.user_id = students_artifacts.user_id WHERE students.user_id = " + userId;

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
}
