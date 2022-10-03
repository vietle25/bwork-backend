package com.ieltshub.model;

public class Members {

    private Boolean deleted_conversation;
    private String name;
    private Integer number_of_unseen_messages;

    public Members(Boolean deleted_conversation, String name, Integer number_of_unseen_messages) {
        this.deleted_conversation = deleted_conversation;
        this.name = name;
        this.number_of_unseen_messages = number_of_unseen_messages;
    }

    public Members() {
    }

    public Boolean getDeleted_conversation() {
        return deleted_conversation;
    }

    public void setDeleted_conversation(Boolean deleted_conversation) {
        this.deleted_conversation = deleted_conversation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber_of_unseen_messages() {
        return number_of_unseen_messages;
    }

    public void setNumber_of_unseen_messages(Integer number_of_unseen_messages) {
        this.number_of_unseen_messages = number_of_unseen_messages;
    }
}
