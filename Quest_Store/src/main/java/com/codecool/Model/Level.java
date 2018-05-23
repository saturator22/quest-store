package com.codecool.Model;

public class Level {
    private Integer levelId;
    private Integer levelRequiredBalance;

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelRequiredBalance(Integer levelRequiredBalance) {
        this.levelRequiredBalance = levelRequiredBalance;
    }

    public Integer getLevelRequiredBalance() {
        return levelRequiredBalance;
    }
}
