package com.evowork.viewmodel.timekeeping.history;

import java.sql.Timestamp;

public class TimekeepingHistoryModel {

    private String checkInTime;
    private String checkOutTime;
    private Timestamp createdAt;
    private Long realWorkingHours;
    private Long deficientWorkingHours;
    private Long planWorkingHours;
    private Long lateWorkingHours;

    public TimekeepingHistoryModel() {

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Long getRealWorkingHours() {
        return realWorkingHours;
    }

    public void setRealWorkingHours(Long realWorkingHours) {
        this.realWorkingHours = realWorkingHours;
    }

    public Long getDeficientWorkingHours() {
        return deficientWorkingHours;
    }

    public void setDeficientWorkingHours(Long deficientWorkingHours) {
        this.deficientWorkingHours = deficientWorkingHours;
    }

    public Long getPlanWorkingHours() {
        return planWorkingHours;
    }

    public void setPlanWorkingHours(Long planWorkingHours) {
        this.planWorkingHours = planWorkingHours;
    }

    public Long getLateWorkingHours() {
        return lateWorkingHours;
    }

    public void setLateWorkingHours(Long lateWorkingHours) {
        this.lateWorkingHours = lateWorkingHours;
    }
}
