package com.evowork.viewmodel.timekeeping;

public class ApprovalTimekeepingFilter {

    private Integer approvalStatus;
    private Integer checkInType;

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getCheckInType() {
        return checkInType;
    }

    public void setCheckInType(Integer checkInType) {
        this.checkInType = checkInType;
    }
}
