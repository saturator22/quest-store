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


}
