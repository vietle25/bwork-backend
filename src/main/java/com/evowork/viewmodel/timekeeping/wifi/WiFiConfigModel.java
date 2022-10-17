package com.evowork.viewmodel.timekeeping.wifi;

import com.evowork.entity.CheckInWiFiConfig;

public class WiFiConfigModel {

    Long id;
    String wiFiName;
    String modemMacAddress;
    Double gpsLatitude;
    Double gpsLongitude;

    public WiFiConfigModel(CheckInWiFiConfig checkInWiFiConfig) {
        this.id = checkInWiFiConfig.getId();
        this.wiFiName = checkInWiFiConfig.getWifiName();
        this.modemMacAddress = checkInWiFiConfig.getModemMacAddress();
        this.gpsLatitude = checkInWiFiConfig.getGpsLatitude();
        this.gpsLongitude = checkInWiFiConfig.getGpsLongitude();
    }

    public WiFiConfigModel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
