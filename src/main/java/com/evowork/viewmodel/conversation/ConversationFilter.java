package com.evowork.viewmodel.conversation;


import java.util.List;

public class ConversationFilter {

    private List<Long> conversationIds; //ConversationIds
    private Long userMemberChatId; // use for create conversation and check exist conversation
    private String paramsSearch;
    private Long conversationId;
    private String content;
    private List<Long> userInvited;
    private String name; // name group
    private String avatarGroup; // avatar group
    private Integer typeMessage;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(Integer typeMessage) {
        this.typeMessage = typeMessage;
    }

    public String getParamsSearch() {
        return paramsSearch;
    }

    public void setParamsSearch(String paramsSearch) {
        this.paramsSearch = paramsSearch;
    }

    public Long getUserMemberChatId() {
        return userMemberChatId;
    }

    public void setUserMemberChatId(Long userMemberChatId) {
        this.userMemberChatId = userMemberChatId;
    }

    public List<Long> getConversationIds() {
        return conversationIds;
    }

    public void setConversationIds(List<Long> conversationIds) {
        this.conversationIds = conversationIds;
    }

    public List<Long> getUserInvited() {
        return userInvited;
    }

    public void setUserInvited(List<Long> userInvited) {
        this.userInvited = userInvited;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarGroup() {
        return avatarGroup;
    }

    public void setAvatarGroup(String avatarGroup) {
        this.avatarGroup = avatarGroup;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }
}
