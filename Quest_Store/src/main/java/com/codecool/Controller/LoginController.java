package com.codecool.Controller;

import com.codecool.DAO.*;
import com.codecool.Model.LoginData;
import com.codecool.Model.User;
import com.codecool.input.UserInput;

public class LoginController {
    private Integer userId = null;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private static String getLogin() {
        UserInput input = new UserInput();
        String login = input.getString(" |  Enter login:");
        return login;
    }

    private static Integer getPassword() {
        UserInput input = new UserInput();
        String password = input.getString(" |  Enter password:");
        return password.hashCode();
    }



}
