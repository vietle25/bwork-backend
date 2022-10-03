package com.ieltshub.viewmodel.sabbatical;

public class RegisterSabbaticalFilter {

    private String offReason;
    private Integer offType;
    private String offFromDate;
    private String offToDate;

    public String getOffReason() {
        return offReason;
    }

    public void setOffReason(String offReason) {
        this.offReason = offReason;
    }

    public Integer getOffType() {
        return offType;
    }

    public void setOffType(Integer offType) {
        this.offType = offType;
    }

    public String getOffFromDate() {
        return offFromDate;
    }

    public void setOffFromDate(String offFromDate) {
        this.offFromDate = offFromDate;
    }

    public String getOffToDate() {
        return offToDate;
    }

    public void setOffToDate(String offToDate) {
        this.offToDate = offToDate;
    }
}
