package com.evowork.viewmodel.user;

public class UserDeviceModel {

    private String deviceId; //Device id
    private String deviceToken; //Device token

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
