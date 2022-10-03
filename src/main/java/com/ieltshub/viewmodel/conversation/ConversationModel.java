package com.ieltshub.viewmodel.conversation;


import com.ieltshub.entity.Conversation;
import com.ieltshub.enumeration.ConversationStatus;

import java.sql.Timestamp;

public class ConversationModel {

    private Long id;
    private String name;
    private Timestamp createdAt;
    private String avatarPath;
    private ConversationStatus status;

    public ConversationModel(Conversation conversation) {
        this.id = conversation.getId();
        this.name = conversation.getName();
        this.createdAt = conversation.getCreatedAt();
        this.status = conversation.getStatus();
        this.avatarPath = conversation.getAvatarPath();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationStatus status) {
        this.status = status;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
