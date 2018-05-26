package com.codecool.View;

import com.codecool.Model.User;
import com.codecool.Model.ShopObject;
import com.codecool.input.UserInput;

import java.util.ArrayList;
import java.util.List;
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
    
    public static ShopObject selectShopObject(List<ShopObject> availableObjects) {
        int i = 1;

        ShopObject artifact = null;

        for (ShopObject art : availableObjects) {
            System.out.println(i + ": " + art);
            i++;
        }

        UserInput ui = new UserInput();
        int userChoice = ui.getInt("Select artifact: ");

        try {
            artifact = availableObjects.get(userChoice - 1);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return artifact;
    }

}
