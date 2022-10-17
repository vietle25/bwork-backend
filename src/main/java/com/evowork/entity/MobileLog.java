package com.evowork.entity;

import com.evowork.enumeration.PlatformType;
import com.evowork.enumeration.converter.PlatformTypeConverter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the user database table.
 */
@Entity
@Table(name = "mobile_log")
public class MobileLog {

    @Id
    @SequenceGenerator(name = "mobile_log_id_seq", sequenceName = "mobile_log_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mobile_log_id_seq")
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne()
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @Column(name = "os_type")
    @Convert(converter = PlatformTypeConverter.class)
    private PlatformType osType;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "class_name")
    private String className;

    @Column(name = "exception")
    private String exception;

    @Column(name = "app_version")
    private String appVersion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public PlatformType getOsType() {
        return osType;
    }

    public void setOsType(PlatformType osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}

