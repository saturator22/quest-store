package com.codecool.Model;

import java.util.Objects;

public class Quest extends ShopObject {
    private Integer questId;
    private String questCategory;
    private String questName;
    private String questDescription;
    private Integer questReward;
    private Boolean isUsed = false; /// TODO WAIT FOR APPROVAL;

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public void setQuestCategory(String questCategory) {
        this.questCategory = questCategory;
    }

    public String getQuestCategory() {
        return questCategory;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public Integer getQuestId() {

        return questId;
    }

    public void setQuestName(String questName) {

        this.questName = questName;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestDescription(String questDescription) {
        this.questDescription = questDescription;
    }

    public String getQuestDescription() {
        return questDescription;
    }

    public Integer getQuestReward() {
        return questReward;
    }

    public void setQuestReward(Integer questReward) {
        this.questReward = questReward;
    }


}
