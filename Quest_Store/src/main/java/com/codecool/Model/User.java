package com.codecool.Model;

public abstract class User {
    Integer role_id;
    String first_name;
    String last_name;
    String login;
    String email;
    Integer password;

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setFirst_name(String firts_name) {
        this.first_name = firts_name;
    }

    public String getFirst_name() {
        return first_name;
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

    public Integer getPassword() {
        return password;
    }

    public void setPassword (Integer password) {
        this.password = password;
    }
}
