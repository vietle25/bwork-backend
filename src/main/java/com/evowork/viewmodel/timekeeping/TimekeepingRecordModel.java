package com.evowork.viewmodel.timekeeping;

import com.evowork.entity.TimekeepingRecord;
import com.evowork.viewmodel.timekeeping.wifi.WiFiConfigModel;

import java.sql.Timestamp;

public class TimekeepingRecordModel {

    private Long id;
    private WiFiConfigModel checkInWiFiConfig;
    private WiFiConfigModel checkOutWiFiConfig;
    private String checkInTime;
    private String checkOutTime;
    private Integer checkInSubmitType;
    private Integer checkOutSubmitType;
    private Integer checkInType;
    private Integer checkOutType;
    private Integer checkInApprovalStatus;
    private Integer checkOutApprovalStatus;
    private String checkInNote;
    private String checkOutNote;
    private Timestamp createdAt;
    private Integer status;
    private Long userId;
    private Double checkInGpsLatitude;
    private Double checkInGpsLongitude;
    private Double checkOutGpsLatitude;
    private Double checkOutGpsLongitude;

    public TimekeepingRecordModel(TimekeepingRecord timekeepingRecord) {
        this.id = timekeepingRecord.getId();
        if (timekeepingRecord.getCheckInWiFiConfig() != null) {
            this.checkInWiFiConfig = new WiFiConfigModel(timekeepingRecord.getCheckInWiFiConfig());
        }
        if (timekeepingRecord.getCheckOutWiFiConfig() != null) {
            this.checkOutWiFiConfig = new WiFiConfigModel(timekeepingRecord.getCheckOutWiFiConfig());
        }
        this.checkInTime = timekeepingRecord.getCheckInTime();
        this.checkOutTime = timekeepingRecord.getCheckOutTime();
        if (timekeepingRecord.getCheckInSubmitType() != null) {
            this.checkInSubmitType = timekeepingRecord.getCheckInSubmitType().getValue();
        }
        if (timekeepingRecord.getCheckOutSubmitType() != null) {
            this.checkOutSubmitType = timekeepingRecord.getCheckOutSubmitType().getValue();
        }
        if (timekeepingRecord.getCheckInType() != null) {
            this.checkInType = timekeepingRecord.getCheckInType().getValue();
        }
        if (timekeepingRecord.getCheckOutType() != null) {
            this.checkOutType = timekeepingRecord.getCheckOutType().getValue();
        }
        if (timekeepingRecord.getCheckInApprovalStatus() != null) {
            this.checkInApprovalStatus = timekeepingRecord.getCheckInApprovalStatus().getValue();
        }
        if (timekeepingRecord.getCheckOutApprovalStatus() != null) {
            this.checkOutApprovalStatus = timekeepingRecord.getCheckOutApprovalStatus().getValue();
        }
        this.checkInNote = timekeepingRecord.getCheckInNote();
        this.checkOutNote = timekeepingRecord.getCheckOutNote();
        this.createdAt = timekeepingRecord.getCreatedAt();
        if (timekeepingRecord.getStatus() != null) {
            this.status = timekeepingRecord.getStatus().getValue();
        }
        if (timekeepingRecord.getUser() != null) {
            userId = timekeepingRecord.getUser().getId();
        }
        this.checkInGpsLatitude = timekeepingRecord.getCheckInGpsLatitude();
        this.checkInGpsLongitude =timekeepingRecord.getCheckInGpsLongitude();
        this.checkOutGpsLatitude = timekeepingRecord.getCheckOutGpsLatitude();
        this.checkOutGpsLongitude = timekeepingRecord.getCheckOutGpsLongitude();
    }

    public TimekeepingRecordModel() {

    }

    public Double getCheckInGpsLatitude() {
        return checkInGpsLatitude;
    }

    public void setCheckInGpsLatitude(Double checkInGpsLatitude) {
        this.checkInGpsLatitude = checkInGpsLatitude;
    }

    public Double getCheckInGpsLongitude() {
        return checkInGpsLongitude;
    }

    public void setCheckInGpsLongitude(Double checkInGpsLongitude) {
        this.checkInGpsLongitude = checkInGpsLongitude;
    }

    public Double getCheckOutGpsLatitude() {
        return checkOutGpsLatitude;
    }

    public void setCheckOutGpsLatitude(Double checkOutGpsLatitude) {
        this.checkOutGpsLatitude = checkOutGpsLatitude;
    }

    public Double getCheckOutGpsLongitude() {
        return checkOutGpsLongitude;
    }

    public void setCheckOutGpsLongitude(Double checkOutGpsLongitude) {
        this.checkOutGpsLongitude = checkOutGpsLongitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WiFiConfigModel getCheckInWiFiConfig() {
        return checkInWiFiConfig;
    }

    public void setCheckInWiFiConfig(WiFiConfigModel checkInWiFiConfig) {
        this.checkInWiFiConfig = checkInWiFiConfig;
    }

    public WiFiConfigModel getCheckOutWiFiConfig() {
        return checkOutWiFiConfig;
    }

    public void setCheckOutWiFiConfig(WiFiConfigModel checkOutWiFiConfig) {
        this.checkOutWiFiConfig = checkOutWiFiConfig;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Integer getCheckInSubmitType() {
        return checkInSubmitType;
    }

    public void setCheckInSubmitType(Integer checkInSubmitType) {
        this.checkInSubmitType = checkInSubmitType;
    }

    public Integer getCheckOutSubmitType() {
        return checkOutSubmitType;
    }

    public void setCheckOutSubmitType(Integer checkOutSubmitType) {
        this.checkOutSubmitType = checkOutSubmitType;
    }

    public Integer getCheckInType() {
        return checkInType;
    }

    public void setCheckInType(Integer checkInType) {
        this.checkInType = checkInType;
    }

    public Integer getCheckOutType() {
        return checkOutType;
    }

    public void setCheckOutType(Integer checkOutType) {
        this.checkOutType = checkOutType;
    }

    public Integer getCheckInApprovalStatus() {
        return checkInApprovalStatus;
    }

    public void setCheckInApprovalStatus(Integer checkInApprovalStatus) {
        this.checkInApprovalStatus = checkInApprovalStatus;
    }

    public Integer getCheckOutApprovalStatus() {
        return checkOutApprovalStatus;
    }

    public void setCheckOutApprovalStatus(Integer checkOutApprovalStatus) {
        this.checkOutApprovalStatus = checkOutApprovalStatus;
    }

    public String getCheckInNote() {
        return checkInNote;
    }

    public void setCheckInNote(String checkInNote) {
        this.checkInNote = checkInNote;
    }

    public String getCheckOutNote() {
        return checkOutNote;
    }

    public void setCheckOutNote(String checkOutNote) {
        this.checkOutNote = checkOutNote;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
