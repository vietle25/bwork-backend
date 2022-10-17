package com.evowork.viewmodel.company;

public class DashboardModel {

    private Integer totalPersonnel;
    private Integer totalCheckin;
    private Integer totalLateForWork;
    private Integer totalSabbatical;
    private Integer totalNotCheckin;

    public Integer getTotalPersonnel() {
        return totalPersonnel;
    }

    public void setTotalPersonnel(Integer totalPersonnel) {
        this.totalPersonnel = totalPersonnel;
    }

    public Integer getTotalCheckin() {
        return totalCheckin;
    }

    public void setTotalCheckin(Integer totalCheckin) {
        this.totalCheckin = totalCheckin;
    }

    public Integer getTotalLateForWork() {
        return totalLateForWork;
    }

    public void setTotalLateForWork(Integer totalLateForWork) {
        this.totalLateForWork = totalLateForWork;
    }

    public Integer getTotalSabbatical() {
        return totalSabbatical;
    }

    public void setTotalSabbatical(Integer totalSabbatical) {
        this.totalSabbatical = totalSabbatical;
    }

    public Integer getTotalNotCheckin() {
        return totalNotCheckin;
    }

    public void setTotalNotCheckin(Integer totalNotCheckin) {
        this.totalNotCheckin = totalNotCheckin;
    }
}
