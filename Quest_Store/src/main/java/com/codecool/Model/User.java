package com.codecool.Model;

public abstract class User {
    private Integer userId;
    private Integer roleId;
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private Integer password;

    public Integer getUserId() {
        return this.userId;
    }

    public void setRoleId(Integer role_id) {
        this.roleId = role_id;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPassword() {
        return this.password;
    }

    public void setPassword (Integer password) {
        this.password = password;
    }

    public String addUserQuery() {
         String
                insertQuery = "INSERT INTO users (role_id, first_name, last_name, login, email, password)\n" +
                "VALUES ( " + getRoleId() + ", " + getFirstName() + ", " + getLastName() + ", " +
                 getLogin() + ", " + getEmail() + ", " + getPassword() + ")\n" +
                "ON CONFLICT DO NOTHING;";

         return insertQuery;
    }

    public String updateUserQuery() {
        String
                updateQuery = 
    }
}

