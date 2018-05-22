package com.codecool.Model;

abstract class User {
    Integer role;
    String firts_name;
    String last_name;
    String login;
    String email;
    String password;

    public void setRole(Integer role) {
        role = role;
    }

    public Integer getRole() {
        return role;
    }

    public void setFirts_name(String firts_name) {
        this.firts_name = firts_name;
    }

    public String getFirts_name() {
        return firts_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
