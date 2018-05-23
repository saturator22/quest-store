package com.codecool.Model;

public class Student {

    Integer userId;
    Integer classId;
    Integer levelId;
    String github;
    Integer balance;
    Integer earned;

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

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return this.userId;
    }
}
