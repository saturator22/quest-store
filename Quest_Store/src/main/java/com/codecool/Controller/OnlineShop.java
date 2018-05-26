package com.codecool.Controller;

import com.codecool.DAO.ArtifactDAO;
import com.codecool.DAO.QuestDAO;
import com.codecool.Model.ShopObject;

import java.util.Set;

public class OnlineShop {
    public Set<ShopObject> getAvailableQuests() {
        QuestDAO questDao = new QuestDAO();
        return questDao.getAvailableQuests();
    }

    public Set<ShopObject> getAvailableArtifacts() {
        ArtifactDAO artifactDAO = new ArtifactDAO();
        return artifactDAO.getAvailableArtifacts();
    }
}
