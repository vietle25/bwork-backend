package com.ieltshub.viewmodel.user;

import com.ieltshub.entity.UserLoginLog;
import com.ieltshub.enumeration.PlatformType;

public class UserLoginLogModel {

    private String deviceId;
    private String os;
    private String osVersion;
    private String model;
    private String serial;

    public UserLoginLogModel(UserLoginLog userLoginLog) {
        this.deviceId = userLoginLog.getDeviceId();
        if(userLoginLog.getOsType().getValue() == PlatformType.ANDROID.getValue()){
            this.os = "android";
        }else if(userLoginLog.getOsType().getValue() == PlatformType.IOS.getValue()){
            this.os = "ios";
        }
        this.osVersion = userLoginLog.getOsVersion();
        this.model = userLoginLog.getModel();
        this.serial = userLoginLog.getSerial();
    }

    public UserLoginLogModel() {

    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
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
}
