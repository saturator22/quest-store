package com.codecool.DAO;

import com.codecool.Model.Artifact;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        int artifactsAfter = artifactDAO.getAvailableArtifacts().size();

        List<Artifact> artifacts = artifactDAO.getAvailableArtifacts().stream()
                .filter(c -> (c.getName().equals("Test name")
                    && c.getDescription().equals("Test description")
                    && c.getPrice() == 100))
                .collect(Collectors.toList());

        assertAll(() -> {
            assertEquals(artifacts.get(0).getPrice(), Integer.valueOf(100));
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
            assertEquals(artifacts.get(0).getPrice(), Integer.valueOf(200));
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
