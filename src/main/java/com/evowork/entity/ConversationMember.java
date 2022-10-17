package com.evowork.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "conversation_member")
public class ConversationMember {

    @Id
    @SequenceGenerator(name = "conversation_member_id_seq", sequenceName = "conversation_member_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conversation_member_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    private Conversation conversation;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "deleted_conversation")
    private Boolean deletedConversation;

    @Column(name = "deleted_conversation_at")
    private Timestamp deletedConversationAt;

    @Column(name = "conversation_name")
    private String conversationName;

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public Boolean getDeletedConversation() {
        return deletedConversation;
    }

    public void setDeletedConversation(Boolean deletedConversation) {
        this.deletedConversation = deletedConversation;
    }

    public Timestamp getDeletedConversationAt() {
        return deletedConversationAt;
    }

    public void setDeletedConversationAt(Timestamp deletedConversationAt) {
        this.deletedConversationAt = deletedConversationAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
