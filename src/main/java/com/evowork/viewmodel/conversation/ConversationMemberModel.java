package com.evowork.viewmodel.conversation;


import com.evowork.entity.User;
import com.evowork.enumeration.ConversationStatus;

public class ConversationMemberModel {

    private Long conversationId;
    private Long userId;        // user id of member chat
    private String avatarPath;  // avatar of member chat
    private String name;        // name of member chat
    private String tokenImage;  // token to show image
    private ConversationStatus statusConversation;
    private ConversationModel conversationModel;

    public ConversationStatus getStatusConversation() {
        return statusConversation;
    }

    public void setStatusConversation(ConversationStatus statusConversation) {
        this.statusConversation = statusConversation;
    }

    public String getTokenImage() {
        return tokenImage;
    }

    public void setTokenImage(String tokenImage) {
        this.tokenImage = tokenImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConversationMemberModel(User user) {
        this.userId = user.getId();
        this.avatarPath = user.getAvatarPath();
        this.name = user.getName();
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

}
