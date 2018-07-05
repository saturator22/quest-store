package com.codecool.DAO;

import com.codecool.Model.Artifact;
import com.codecool.Model.ShopObject;
import org.junit.jupiter.api.*;
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


    @AfterEach
    public void cleanUp() {
        ArtifactDAO artifactDAO = new ArtifactDAO();
        List<Artifact> artifacts = artifactDAO.getAvailableArtifacts();
        artifactDAO.deleteArtifact(artifacts.get(0));
    }

    @DisplayName("Test add artifact")
    @Test
    public void addArtifactTest() {

        Artifact artifact = createArtifact();
        ArtifactDAO artifactDAO = new ArtifactDAO();

        int artifactsBefore = artifactDAO.getAvailableArtifacts().size();
        artifactDAO.addArtifact(artifact);
        List<Artifact> artifacts = artifactDAO.getAvailableArtifacts();

        int artifactsAfter = artifactDAO.getAvailableArtifacts().size();
        artifacts.stream().filter(c -> c.getName().equals("Test name"))
                          .filter(c -> c.getDescription().equals("Test description"))
                          .filter(c -> c.getPrice().equals(100));


        assertAll(() -> {
            assertEquals((int) artifacts.get(0).getPrice(), 100);
            assertEquals(artifacts.get(0).getName(), "Test name");
            assertEquals(artifacts.get(0).getDescription(), "Test description");
            assertEquals(artifactsBefore + 1, artifactsAfter);
        });

    }

    @DisplayName("Test edit artifact")
    @Test
    public void updateArtifactTest() {
        Artifact artifact = createArtifact();
        ArtifactDAO artifactDAO = new ArtifactDAO();
        artifactDAO.addArtifact(artifact);

        List<Artifact> artifacts = artifactDAO.getAvailableArtifacts();
        artifacts.stream().filter(c -> c.getName().equals("Test name"))
                .filter(c -> c.getDescription().equals("Test description"))
                .filter(c -> c.getPrice().equals(100));

        Artifact updateArtifact = artifacts.get(0);
        updateArtifact.setPrice(200);
        updateArtifact.setDescription("Updated description");
        updateArtifact.setName("Updated name");

        artifactDAO.updateArtifact(updateArtifact);

        assertAll(() -> {
            assertEquals((int) artifacts.get(0).getPrice(), 200);
            assertEquals(artifacts.get(0).getName(), "Updated name");
            assertEquals(artifacts.get(0).getDescription(), "Updated description");
        });


    }

    private Artifact createArtifact() {
        Artifact artifact = new Artifact();
        artifact.setName("Test name");
        artifact.setDescription("Test description");
        artifact.setPrice(100);

        return artifact;
    }

}
