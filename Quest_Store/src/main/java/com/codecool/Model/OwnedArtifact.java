package com.codecool.Model;

public class OwnedArtifact extends Artifact{
    public Integer uniqueId;
    public Integer status;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getUniqueId() {
        return uniqueId;
    }
}


