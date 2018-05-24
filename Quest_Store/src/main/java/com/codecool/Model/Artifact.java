package com.codecool.Model;

public class Artifact {
    private Integer artifactId;
    private String name;
    private Integer price;
    private String description;
    private Integer owner_id;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Integer getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
    }

    public void setOwner_id(Integer student_id) {
        this.owner_id = student_id;
    }

    public Integer getOwner_id() {
        return owner_id;
    }
}
