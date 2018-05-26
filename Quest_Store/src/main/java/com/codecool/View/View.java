package com.codecool.View;

import com.codecool.Model.User;
import com.codecool.Model.ShopObject;

import java.util.Set;

public class View {

    public void displayUser(User user) {
        System.out.println(user);
    }

    public static void displaySetOfItems(Set<ShopObject> setOfItems) {
        for (ShopObject quest : setOfItems) {
            System.out.println(quest);
        }
    }

}
