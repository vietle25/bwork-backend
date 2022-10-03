package com.ieltshub.viewmodel.timekeeping;

import com.ieltshub.viewmodel.common.Paging;

public class TimekeepingListFilter {

    private Long companyId;
    private Long branchId;
    private String day;
    private Paging paging;
    private String stringSearch;
    private Integer dashboardType;
    private Integer minuteAfterCheckIn1;
    private Integer minuteAfterCheckIn2;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public String getStringSearch() {
        return stringSearch;
    }

    public void setStringSearch(String stringSearch) {
        this.stringSearch = stringSearch;
    }

    public Integer getDashboardType() {
        return dashboardType;
    }

    public void setDashboardType(Integer dashboardType) {
        this.dashboardType = dashboardType;
    }

    public Integer getMinuteAfterCheckIn1() {
        return minuteAfterCheckIn1;
    }

    public void setMinuteAfterCheckIn1(Integer minuteAfterCheckIn1) {
        this.minuteAfterCheckIn1 = minuteAfterCheckIn1;
    }

    public Integer getMinuteAfterCheckIn2() {
        return minuteAfterCheckIn2;
    }

    public void setMinuteAfterCheckIn2(Integer minuteAfterCheckIn2) {
        this.minuteAfterCheckIn2 = minuteAfterCheckIn2;
    }
}
