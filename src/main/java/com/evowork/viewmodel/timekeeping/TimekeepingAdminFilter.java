package com.evowork.viewmodel.timekeeping;

public class TimekeepingAdminFilter {

    private Long userId;
    private String day;
    private String checkinTime;
    private String checkoutTime;
    private Long checkinWifiId;
    private Long checkoutWifiId;
    private String checkinNote;
    private String checkoutNote;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public Long getCheckinWifiId() {
        return checkinWifiId;
    }

    public void setCheckinWifiId(Long checkinWifiId) {
        this.checkinWifiId = checkinWifiId;
    }

    public Long getCheckoutWifiId() {
        return checkoutWifiId;
    }

    public void setCheckoutWifiId(Long checkoutWifiId) {
        this.checkoutWifiId = checkoutWifiId;
    }

    public String getCheckinNote() {
        return checkinNote;
    }

    public void setCheckinNote(String checkinNote) {
        this.checkinNote = checkinNote;
    }

    public String getCheckoutNote() {
        return checkoutNote;
    }

    public void setCheckoutNote(String checkoutNote) {
        this.checkoutNote = checkoutNote;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
