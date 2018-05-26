package com.codecool.Model;

public class Student extends User{

    private Integer classId;
    private Integer levelId;
    private String github;
    private Integer balance = 0;
    private Integer earned = 0;

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return this.balance;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getClassId() {
        return this.classId;
    }

    public void setEarned(Integer earned) {
        this.earned = earned;
    }

    public Integer getEarned() {
        return this.earned;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getGithub() {
        return this.github;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getLevelId() {
        return this.levelId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "userId=" + getUserId() +
                ", roleId=" + getRoleId() +
                ", firstName=" +getFirstName() +
                ", lastName=" + getLastName() +
                ", email=" + getEmail() +
                ", classId=" + classId +
                ", levelId=" + levelId +
                ", github='" + github + '\'' +
                ", balance=" + balance +
                ", earned=" + earned +
                '}';
    }
}
