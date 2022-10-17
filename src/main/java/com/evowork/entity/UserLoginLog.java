package com.evowork.entity;

import com.evowork.enumeration.PlatformType;
import com.evowork.enumeration.converter.PlatformTypeConverter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_login_log")
public class UserLoginLog {

    @Id
    @SequenceGenerator(name = "user_login_log_id_seq", sequenceName = "user_login_log_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_login_log_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "os_type")
    @Convert(converter = PlatformTypeConverter.class)
    private PlatformType osType;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "model")
    private String model;

    @Column(name = "serial")
    private String serial;

    @Column(name = "imei1")
    private String imei1;

    @Column(name = "imei2")
    private String imei2;

    @Column(name = "owner")
    private Boolean owner;

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }
}
