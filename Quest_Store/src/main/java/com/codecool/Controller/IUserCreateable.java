package com.codecool.Controller;

import com.codecool.Model.User;
import com.codecool.input.UserInput;

public interface IUserCreateable {

    default User setUserAttibutes(User user) {
        UserInput userInput = new UserInput();
        user.setFirstName(userInput.getString("Enter first name: "));
        user.setLastName(userInput.getString("Enter last name: "));
        user.setLogin(userInput.getString("Enter login: "));
        user.setEmail(userInput.getString("Enter email: "));
        user.setPassword(userInput.getString("Enter password: "));
    }
}