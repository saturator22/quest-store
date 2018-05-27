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


    public static User logIntoSystem() {
        User user = null;
        boolean signingDone = false;

        int triesLimit = 3;

        while (!signingDone) {
            System.out.println("----- Log into QuestStore 2.0 -----\n\n");

            for (int i = 0; i<triesLimit; i++) {
                String login = getLogin();
                Integer password = getPassword().hashCode();
                user = logIntoAccount(login, password);

                if (user != null) {
                    if(user.getLogin().equals(login)) {
                        System.out.println("Hello " + user.getFirstName());
                        return user;
                    }
                }
                System.out.println("Wrong login or password. " + (triesLimit - i - 1) + " tries left.");
            }
            System.out.println("Failed.Check your login and password.!");
            signingDone = true;
        }
        return user;
    }


}
