package com.evowork.model;

public class MessageByConversation {

    private Long from_user_id, timestamp;
    private Boolean receiver_seen;
    private Integer receiver_resource_action, message_type;
    private String content;

    public MessageByConversation() {
    }

    public MessageByConversation(Long from_user_id, Long timestamp, Boolean receiver_seen,
                                 Integer receiver_resource_action, Integer message_type, String content) {
        this.from_user_id = from_user_id;
        this.timestamp = timestamp;
        this.receiver_seen = receiver_seen;
        this.receiver_resource_action = receiver_resource_action;
        this.message_type = message_type;
        this.content = content;
    }

    public Long getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(Long from_user_id) {
        this.from_user_id = from_user_id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getReceiver_seen() {
        return receiver_seen;
    }

    public void setReceiver_seen(Boolean receiver_seen) {
        this.receiver_seen = receiver_seen;
    }

    public Integer getReceiver_resource_action() {
        return receiver_resource_action;
    }

    public void setReceiver_resource_action(Integer receiver_resource_action) {
        this.receiver_resource_action = receiver_resource_action;
    }

    public Integer getMessage_type() {
        return message_type;
    }

    public void setMessage_type(Integer message_type) {
        this.message_type = message_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
