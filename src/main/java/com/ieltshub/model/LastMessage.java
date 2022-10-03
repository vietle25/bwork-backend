package com.ieltshub.model;

public class LastMessage {

    private String content;
    private Integer message_type;
    private Long timestamp;

    public LastMessage() {
    }

    public LastMessage(String content, Integer message_type, Long timestamp) {
        this.content = content;
        this.message_type = message_type;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMessage_type() {
        return message_type;
    }

    public void setMessage_type(Integer message_type) {
        this.message_type = message_type;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
