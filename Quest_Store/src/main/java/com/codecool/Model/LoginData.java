package com.codecool.Model;

public class LoginData {
    private Integer userId;
    private Integer roleId;
    private String login;
    private String password;

    public LoginData(Integer userId, Integer roleId, String login, String password) {
        this.userId = userId;
        this.roleId = roleId;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    @Override
    public String toString() {
        return this.userId + " " + this.login + " " + this.roleId + " " + this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
}
