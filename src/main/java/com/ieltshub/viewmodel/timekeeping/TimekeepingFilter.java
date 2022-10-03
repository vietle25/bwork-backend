package com.ieltshub.viewmodel.timekeeping;

import com.ieltshub.viewmodel.user.DeviceInfo;

public class TimekeepingFilter {

    private Long wiFiConfigId;
    private DeviceInfo deviceInfo;
    private Integer submitType;
    private Integer type;
    private String note;
    private Double gpsLatitude;
    private Double gpsLongitude;
    private TimekeepingMetaFilter timekeepingMeta;
    private Boolean checkingIn;
    private String imgPath;

    public Long getWiFiConfigId() {
        return wiFiConfigId;
    }

    public void setWiFiConfigId(Long wiFiConfigId) {
        this.wiFiConfigId = wiFiConfigId;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public Integer getSubmitType() {
        return submitType;
    }

    public void setSubmitType(Integer submitType) {
        this.submitType = submitType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(Double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public Double getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(Double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public TimekeepingMetaFilter getTimekeepingMeta() {
        return timekeepingMeta;
    }

    public void setTimekeepingMeta(TimekeepingMetaFilter timekeepingMeta) {
        this.timekeepingMeta = timekeepingMeta;
    }

    public Boolean getCheckingIn() {
        return checkingIn;
    }

    public void setCheckingIn(Boolean checkingIn) {
        this.checkingIn = checkingIn;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
