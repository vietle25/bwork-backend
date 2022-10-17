package com.evowork.viewmodel.sabbatical;

import com.evowork.viewmodel.common.Paging;

public class SabbaticalFilter {

    private Paging paging;
    private Long companyId;
    private Long branchId;
    private String stringSearch;
    private String day;
    private String month;
    private String deniedNote;

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

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

    public String getStringSearch() {
        return stringSearch;
    }

    public void setStringSearch(String stringSearch) {
        this.stringSearch = stringSearch;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDeniedNote() {
        return deniedNote;
    }

    public void setDeniedNote(String deniedNote) {
        this.deniedNote = deniedNote;
    }
}
