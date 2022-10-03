package com.ieltshub.entity;

import com.ieltshub.enumeration.StatusType;
import com.ieltshub.enumeration.UserResourceImageSideType;
import com.ieltshub.enumeration.UserResourceType;
import com.ieltshub.enumeration.converter.StatusTypeConverter;
import com.ieltshub.enumeration.converter.UserResourceImageSideTypeConverter;
import com.ieltshub.enumeration.converter.UserResourceTypeConverter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_resource")

public class UserResource {

    @Id
    @SequenceGenerator(name = "user_resource_id_seq", sequenceName = "user_resource_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_resource_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "type")
    @Convert(converter = UserResourceTypeConverter.class)
    private UserResourceType type;

    @Column(name = "status")
    @Convert(converter = StatusTypeConverter.class)
    private StatusType status;

    @Column(name = "path_to_resource")
    private String pathToResource;

    @ManyToOne()
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne()
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private User updatedBy;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @ManyToOne()
    @JoinColumn(name = "deleted_by", referencedColumnName = "id")
    private User deletedBy;

    @Column(name = "image_side")
    @Convert(converter = UserResourceImageSideTypeConverter.class)
    private UserResourceImageSideType imageSide;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserResourceType getType() {
        return type;
    }

    public void setType(UserResourceType type) {
        this.type = type;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getPathToResource() {
        return pathToResource;
    }

    public void setPathToResource(String pathToResource) {
        this.pathToResource = pathToResource;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public User getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(User deletedBy) {
        this.deletedBy = deletedBy;
    }

    public UserResourceImageSideType getImageSide() {
        return imageSide;
    }

    public void setImageSide(UserResourceImageSideType imageSide) {
        this.imageSide = imageSide;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }
}
