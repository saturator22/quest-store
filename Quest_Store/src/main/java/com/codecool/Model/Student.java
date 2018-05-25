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

    public String insertStudentQuery() {
        String
                insertQuery = "INSERT INTO students (user_id, class_id, level_id, github, balance, earned_coolcoins)\n" +
                "VALUES ((SELECT user_id FROM users WHERE login = '" + getLogin() + "'), " + getClassId() + ", " + getLevelId() +
                ", '" + getGithub() + "', " + getBalance() + ", " + getEarned() + ")\n" +
                "ON CONFLICT DO NOTHING;";
        return  insertQuery;
    }

    public String updateStudentQuery() {
        String
                updateQuery = "UPDATE students\n" +
                "SET class_id = " + getClassId() + ", level_id = " + getLevelId() + ", github = " + getGithub() +
                ", balance = " + getBalance() + ", earned_coolcoins = " + getEarned() + "\n" +
                "WHERE user_id = ?";
        return updateQuery;
    }
}
