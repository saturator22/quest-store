package com.codecool.DAO;

import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.Artifact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArtifactDAO {
    
    public boolean UpdateArtifact(Artifact artifact) {
        String query = "UPDATE artifacts SET name=?, price=?, description=? " +
                "WHERE artifact_id=?=" + artifact.getArtifactId();

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
    }
}
