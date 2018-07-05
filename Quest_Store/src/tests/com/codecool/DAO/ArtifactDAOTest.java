package com.codecool.DAO;

import com.codecool.Model.Artifact;
import com.codecool.Model.ShopObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArtifactDAOTest {


    @DisplayName("Test adding artifact")
    @Test
    public void addArtifactTest() {
        Artifact artifact = new Artifact();
        artifact.setName("Test name");
        artifact.setDescription("Test description");
        artifact.setPrice(100);

        ArtifactDAO artifactDAO = new ArtifactDAO();

        int artifactsBefore = artifactDAO.getAvailableArtifacts().size();
        artifactDAO.addArtifact(artifact);
        List<Artifact> artifacts = artifactDAO.getAvailableArtifacts();

        int artifactsAfter = artifactDAO.getAvailableArtifacts().size();
        artifacts.stream().filter(c -> c.getName().equals("Test name"))
                          .filter(c -> c.getDescription().equals("Test description"))
                          .filter(c -> c.getPrice().equals(100));
        List<Artifact> actualArtifact = new ArrayList<>(artifacts);


        assertAll(() -> {
            assertEquals((int) actualArtifact.get(0).getPrice(), 100);
            assertEquals(actualArtifact.get(0).getName(), "Test name");
            assertEquals(actualArtifact.get(0).getDescription(), "Test description");
            assertEquals(artifactsBefore + 1, artifactsAfter);
        });




    }
}
