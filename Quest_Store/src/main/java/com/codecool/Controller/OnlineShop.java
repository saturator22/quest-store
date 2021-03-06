package com.codecool.Controller;

import com.codecool.DAO.ArtifactDAO;
import com.codecool.DAO.QuestDAO;
import com.codecool.Model.Artifact;
import com.codecool.Model.ShopObject;
import com.codecool.Model.Student;
import com.codecool.input.UserInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OnlineShop {
    private Student currentAccount;

    public void setCurrenctAccount(Student currenctAccount) {
        this.currentAccount = currenctAccount;
    }

    public Student getCurrenctAccount() {
        return currentAccount;
    }

    public Set<ShopObject> getAvailableQuests() {
        QuestDAO questDao = new QuestDAO();
        return questDao.getAvailableQuests();
    }

    public Set<ShopObject> getAvailableArtifacts() {
        ArtifactDAO artifactDAO = new ArtifactDAO();
        return artifactDAO.getAvailableArtifacts();
    }

    public boolean buyArtifact(Artifact artifact) {
        Boolean isSuccesfull = null;
        ArtifactDAO artifactDAO = new ArtifactDAO();
        Integer price = artifact.getPrice();
        Integer balance = currentAccount.getBalance();

        if (balance >= price) {
            artifactDAO.addArtifactToStudent(artifact, currentAccount.getClassId());
            isSuccesfull = true;
            System.out.println("Shoping succesfull, item has been added!");
        } else {
            System.out.println("Not enough £££. Complete more quests.");
            isSuccesfull = false;
        }
        return isSuccesfull;
    }

}
