package com.ieltshub.viewmodel.timekeeping;

public class TimekeepingMetaFilter {

    private Integer deviceErrorCode;
    private String wiFiName;
    private String modemMacAddress;
    private Double personalIdAccuracy;

    public Integer getDeviceErrorCode() {
        return deviceErrorCode;
    }

    public void setDeviceErrorCode(Integer deviceErrorCode) {
        this.deviceErrorCode = deviceErrorCode;
    }

    public String getWiFiName() {
        return wiFiName;
    }

    public void setWiFiName(String wiFiName) {
        this.wiFiName = wiFiName;
    }

    public String getModemMacAddress() {
        return modemMacAddress;
    }

    public void setModemMacAddress(String modemMacAddress) {
        this.modemMacAddress = modemMacAddress;
    }

    public Double getPersonalIdAccuracy() {
        return personalIdAccuracy;
    }

    public void setPersonalIdAccuracy(Double personalIdAccuracy) {
        this.personalIdAccuracy = personalIdAccuracy;
    }
}
