package com.ieltshub.viewmodel.user;

import java.math.BigDecimal;

public class ConfigStaffFilter {

    private Integer salaryInputType;
    private BigDecimal salaryNumber;
    private Integer workingTimeShiftType;
    private Double numWorkingHour;
    private Integer numDayOffInMonth;
    private String startWorkingTime1;
    private String endWorkingTime1;
    private String startWorkingTime2;
    private String endWorkingTime2;
    private String validFrom;

    public Integer getSalaryInputType() {
        return salaryInputType;
    }

    public void setSalaryInputType(Integer salaryInputType) {
        this.salaryInputType = salaryInputType;
    }

    public BigDecimal getSalaryNumber() {
        return salaryNumber;
    }

    public void setSalaryNumber(BigDecimal salaryNumber) {
        this.salaryNumber = salaryNumber;
    }

    public Integer getWorkingTimeShiftType() {
        return workingTimeShiftType;
    }

    public void setWorkingTimeShiftType(Integer workingTimeShiftType) {
        this.workingTimeShiftType = workingTimeShiftType;
    }

    public Double getNumWorkingHour() {
        return numWorkingHour;
    }

    public void setNumWorkingHour(Double numWorkingHour) {
        this.numWorkingHour = numWorkingHour;
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

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }
}
