package com.ieltshub.viewmodel.salary;

import com.ieltshub.entity.Salary;

import java.math.BigDecimal;

public class SalaryModel {

    private Long id; // id of table salary
    private String period;
    private BigDecimal netAmount;
    private BigDecimal payAmount;
    private BigDecimal totalBonusAmount;
    private BigDecimal totalFineAmount;
    private BigDecimal totalTemporaryAmount;
    private BigDecimal totalWorkdays;
    private BigDecimal totalWorkingHours;
    private BigDecimal totalLackTime;
    private BigDecimal totalPlanWorkingHours;
    private BigDecimal salary;
    private Integer inputType;
    private Integer numDayOffInMonth;
    private Integer numDaySabbatical;
    private Integer numLateForWork;

    public SalaryModel(Salary salary) {
        this.id = salary.getId();
        this.period = salary.getPeriod();
        this.netAmount = salary.getNetAmount();
        this.payAmount = salary.getPayAmount();
        this.totalBonusAmount = salary.getTotalBonusAmount();
        this.totalFineAmount = salary.getTotalFineAmount();
    }

    public SalaryModel() {
    }

    public BigDecimal getTotalTemporaryAmount() {
        return totalTemporaryAmount;
    }

    public void setTotalTemporaryAmount(BigDecimal totalTemporaryAmount) {
        this.totalTemporaryAmount = totalTemporaryAmount;
    }

    public BigDecimal getTotalWorkdays() {
        return totalWorkdays;
    }

    public void setTotalWorkdays(BigDecimal totalWorkdays) {
        this.totalWorkdays = totalWorkdays;
    }

    public BigDecimal getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public void setTotalWorkingHours(BigDecimal totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }

    public BigDecimal getTotalLackTime() {
        return totalLackTime;
    }

    public void setTotalLackTime(BigDecimal totalLackTime) {
        this.totalLackTime = totalLackTime;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getTotalBonusAmount() {
        return totalBonusAmount;
    }

    public void setTotalBonusAmount(BigDecimal totalBonusAmount) {
        this.totalBonusAmount = totalBonusAmount;
    }

    public BigDecimal getTotalFineAmount() {
        return totalFineAmount;
    }

    public void setTotalFineAmount(BigDecimal totalFineAmount) {
        this.totalFineAmount = totalFineAmount;
    }

    public Integer getInputType() {
        return inputType;
    }

    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    public Integer getNumDayOffInMonth() {
        return numDayOffInMonth;
    }

    public void setNumDayOffInMonth(Integer numDayOffInMonth) {
        this.numDayOffInMonth = numDayOffInMonth;
    }

    public BigDecimal getTotalPlanWorkingHours() {
        return totalPlanWorkingHours;
    }

    public void setTotalPlanWorkingHours(BigDecimal totalPlanWorkingHours) {
        this.totalPlanWorkingHours = totalPlanWorkingHours;
    }

    public Integer getNumDaySabbatical() {
        return numDaySabbatical;
    }

    public void setNumDaySabbatical(Integer numDaySabbatical) {
        this.numDaySabbatical = numDaySabbatical;
    }

    public Integer getNumLateForWork() {
        return numLateForWork;
    }

    public void setNumLateForWork(Integer numLateForWork) {
        this.numLateForWork = numLateForWork;
    }
}
