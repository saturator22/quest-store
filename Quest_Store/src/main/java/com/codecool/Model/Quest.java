package com.codecool.Model;

public class Quest {
    private Integer questId;
    private String questCategory;
    private String questName;
    private String questDescription;

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
}
