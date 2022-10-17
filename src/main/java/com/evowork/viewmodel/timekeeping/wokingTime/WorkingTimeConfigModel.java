package com.evowork.viewmodel.timekeeping.wokingTime;

import com.evowork.entity.WorkingTimeConfig;

public class WorkingTimeConfigModel {

    private Long id;
    private Integer type;
    private String name;
    private Double numWorkingHours;
    private Integer numDayOffInMonth;
    private String startWorkingTime1;
    private String endWorkingTime1;
    private String startWorkingTime2;
    private String endWorkingTime2;
    private String validFrom;
    private String day;
    private Integer shiftType;
    private WorkingTimeConfigModel nextDay;

    public WorkingTimeConfigModel(WorkingTimeConfig workingTimeConfig) {
        this.id = workingTimeConfig.getId();
        if (workingTimeConfig.getWorkingTimeType() != null) {
            this.type = workingTimeConfig.getWorkingTimeType().getValue();
        }
        this.name = workingTimeConfig.getName();
        this.numWorkingHours = workingTimeConfig.getNumWorkingHours();
        this.numDayOffInMonth = workingTimeConfig.getNumDayOffInMonth();
        this.startWorkingTime1 = workingTimeConfig.getStartWorkingTime1();
        this.endWorkingTime1 = workingTimeConfig.getEndWorkingTime1();
        this.startWorkingTime2 = workingTimeConfig.getStartWorkingTime2();
        this.endWorkingTime2 = workingTimeConfig.getEndWorkingTime2();
        this.validFrom = workingTimeConfig.getValidFrom();
        if (workingTimeConfig.getShiftType() != null) {
            this.shiftType = workingTimeConfig.getShiftType().getValue();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNumWorkingHours() {
        return numWorkingHours;
    }

    public void setNumWorkingHours(Double numWorkingHours) {
        this.numWorkingHours = numWorkingHours;
    }

    public Integer getNumDayOffInMonth() {
        return numDayOffInMonth;
    }

    public void setNumDayOffInMonth(Integer numDayOffInMonth) {
        this.numDayOffInMonth = numDayOffInMonth;
    }

    public String getStartWorkingTime1() {
        return startWorkingTime1;
    }

    public void setStartWorkingTime1(String startWorkingTime1) {
        this.startWorkingTime1 = startWorkingTime1;
    }

    public String getEndWorkingTime1() {
        return endWorkingTime1;
    }

    public void setEndWorkingTime1(String endWorkingTime1) {
        this.endWorkingTime1 = endWorkingTime1;
    }

    public String getStartWorkingTime2() {
        return startWorkingTime2;
    }

    public void setStartWorkingTime2(String startWorkingTime2) {
        this.startWorkingTime2 = startWorkingTime2;
    }

    public String getEndWorkingTime2() {
        return endWorkingTime2;
    }

    public void setEndWorkingTime2(String endWorkingTime2) {
        this.endWorkingTime2 = endWorkingTime2;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public Integer getShiftType() {
        return shiftType;
    }

    public void setShiftType(Integer shiftType) {
        this.shiftType = shiftType;
    }

    public WorkingTimeConfigModel getNextDay() {
        return nextDay;
    }

    public void setNextDay(WorkingTimeConfigModel nextDay) {
        this.nextDay = nextDay;
    }
}
