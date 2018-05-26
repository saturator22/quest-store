package com.codecool.Model;

import java.util.Objects;

public class Artifact extends ShopObject {
    private Integer artifactId;
    private String name;
    private Integer price;
    private String description;
    private String category;

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

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artifact artifact = (Artifact) o;
        return Objects.equals(getName(), artifact.getName()) &&
                Objects.equals(getPrice(), artifact.getPrice()) &&
                Objects.equals(getDescription(), artifact.getDescription());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getPrice(), getDescription());
    }

    @Override
    public String toString() {
        return "Artifact{" +
                "artifactId=" + artifactId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
