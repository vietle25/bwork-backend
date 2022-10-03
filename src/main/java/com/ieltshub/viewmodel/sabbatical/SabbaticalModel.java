package com.ieltshub.viewmodel.sabbatical;

import com.ieltshub.entity.Sabbatical;
import com.ieltshub.viewmodel.department.DepartmentModel;
import com.ieltshub.viewmodel.timekeeping.wokingTime.WorkingTimeConfigModel;
import com.ieltshub.viewmodel.user.UserDTO;

import java.sql.Timestamp;

public class SabbaticalModel {

    private Long id;
    private Integer offType;
    private String offFromDate;
    private String offToDate;
    private WorkingTimeConfigModel workingTimeConfig;
    private String offReason;
    private Timestamp createdAt;
    private Integer approvalStatus;
    private String deniedNote;
    private String nameDepartment;
    private DepartmentModel departmentModel;
    private UserDTO userDTO;

    public SabbaticalModel(Sabbatical sabbatical) {
        this.id = sabbatical.getId();
        if (sabbatical.getOffType() != null) {
            this.offType = sabbatical.getOffType().getValue();
        }
        this.offFromDate = sabbatical.getOffFromDate().toString();
        if (sabbatical.getOffToDate() != null) {
            this.offToDate = sabbatical.getOffToDate().toString();
        }
        if (sabbatical.getUser() != null) {
            this.userDTO = new UserDTO(sabbatical.getUser());
        }
        if(sabbatical.getDepartment() != null) {
            this.departmentModel = new DepartmentModel(sabbatical.getDepartment());
            this.nameDepartment = this.departmentModel.getName();
        }
        if(sabbatical.getDeniedNote() != null) {
            this.deniedNote = sabbatical.getDeniedNote();
        }

        this.workingTimeConfig = new WorkingTimeConfigModel(sabbatical.getWorkingTimeConfig());
        this.offReason = sabbatical.getOffReason();
        this.createdAt = sabbatical.getCreatedAt();
        if (sabbatical.getApprovalStatus() != null) {
            this.approvalStatus = sabbatical.getApprovalStatus().getValue();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public WorkingTimeConfigModel getWorkingTimeConfig() {
        return workingTimeConfig;
    }

    public void setWorkingTimeConfig(WorkingTimeConfigModel workingTimeConfig) {
        this.workingTimeConfig = workingTimeConfig;
    }

    public String getOffReason() {
        return offReason;
    }

    public void setOffReason(String offReason) {
        this.offReason = offReason;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getDeniedNote() {
        return deniedNote;
    }

    public void setDeniedNote(String deniedNote) {
        this.deniedNote = deniedNote;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

}
