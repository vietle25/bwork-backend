package com.evowork.viewmodel.common;

import com.evowork.entity.AppVersion;
import com.evowork.enumeration.StatusType;
import com.evowork.enumeration.VersionForceType;

import java.sql.Timestamp;

public class UpdateVersionModel {

    private long id; // Id
    private VersionForceType force; // Force
    private String description; // Description
    private String link; //Link
    private String version; // Version
    private StatusType status; //Status
    private Timestamp created_at; //Create at
//    private Timestamp created_by;
    private Timestamp updated_at; //Update at
    private Timestamp deleted_at; // Deleted at

    public UpdateVersionModel(AppVersion appVersion){
        if(appVersion != null){
            id = appVersion.getId();
            force = appVersion.getForce();
            description = appVersion.getDescription();
            link = appVersion.getLink();
            version = appVersion.getVersion();
            status = appVersion.getStatus();
            created_at = appVersion.getCreatedAt();
            updated_at = appVersion.getUpdatedAt();
            deleted_at = appVersion.getDeletedAt();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public VersionForceType getForce() {
        return force;
    }

    public void setForce(VersionForceType force) {
        this.force = force;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Timestamp deleted_at) {
        this.deleted_at = deleted_at;
    }
}
