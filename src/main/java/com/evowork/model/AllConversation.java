package com.evowork.model;

public class AllConversation {

    private Long conversation_id;
    private Long from_user_id;
    private Long last_updated_at;
    private LastMessage last_messages;

    public AllConversation() {
    }

    public AllConversation(Long conversation_id, Long from_user_id, Long last_updated_at, LastMessage last_messages) {
        this.conversation_id = conversation_id;
        this.from_user_id = from_user_id;
        this.last_updated_at = last_updated_at;
        this.last_messages = last_messages;
    }

    public Long getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(Long conversation_id) {
        this.conversation_id = conversation_id;
    }

    public Long getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(Long from_user_id) {
        this.from_user_id = from_user_id;
    }

    public Long getLast_updated_at() {
        return last_updated_at;
    }

    public void setLast_updated_at(Long last_updated_at) {
        this.last_updated_at = last_updated_at;
    }

    public LastMessage getLast_messages() {
        return last_messages;
    }

    public void setLast_messages(LastMessage last_messages) {
        this.last_messages = last_messages;
    }
}
