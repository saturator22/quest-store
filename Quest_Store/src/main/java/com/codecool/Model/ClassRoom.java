package com.codecool.Model;

public class ClassRoom {
    private Integer classId;
    private String name;

    public ClassRoom(String name) {
        this.name = name;
    }

    public ClassRoom() {}

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "classId=" + classId +
                ", name='" + name + '\'' +
                '}';
    }
}
