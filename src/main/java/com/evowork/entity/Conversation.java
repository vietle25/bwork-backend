package com.evowork.entity;

import com.evowork.enumeration.ConversationStatus;
import com.evowork.enumeration.converter.ConversationStatusConverter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "conversation")
public class Conversation {

    @Id
    @SequenceGenerator(name = "conversation_id_seq", sequenceName = "conversation_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conversation_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "status")
    @Convert(converter = ConversationStatusConverter.class)
    private ConversationStatus status;

    @Column(name = "avatar_path")
    private String avatarPath;

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
