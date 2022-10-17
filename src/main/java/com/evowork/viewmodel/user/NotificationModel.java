package com.evowork.viewmodel.user;

import com.evowork.entity.Notification;

import java.sql.Timestamp;

public class NotificationModel {

    private long id; // Id
    private Integer type; // Item type
    private Timestamp createdAt; // Create at
    private UserDTO user; //User
    private String title; //title
    private String content; //Content
    private Boolean isSeen; //Is View
    private String meta;

    public NotificationModel(Notification notification) {
        this.id = notification.getId();
        if (notification.getType() != null) {
            this.type = notification.getType().getValue();
        }
        this.createdAt = notification.getCreatedAt();
        if (notification.getUser() != null) {
            this.user = new UserDTO(notification.getUser());
        }
        this.title = notification.getTitle();
        this.content = notification.getContent();
        this.isSeen = notification.getIsSeen();
        this.meta = notification.getMeta();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getSeen() {
        return isSeen;
    }

    public void setSeen(Boolean seen) {
        isSeen = seen;
    }

    public UserDTO getUser() {
        return user;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
}
