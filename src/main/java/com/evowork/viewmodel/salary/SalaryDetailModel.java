package com.evowork.viewmodel.salary;

import com.evowork.entity.SalaryDetail;
import com.evowork.enumeration.SalaryType;
import com.evowork.viewmodel.user.UserDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SalaryDetailModel {

    private Long id; // id of table salary
    private SalaryType type;
    private String reason;
    private BigDecimal amount;
    private Timestamp createAt;
    private UserDTO userModel;

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public SalaryDetailModel(SalaryDetail salaryDetail) {
        this.id = salaryDetail.getId();
        this.type = salaryDetail.getType();
        this.reason = salaryDetail.getReason();
        this.amount = salaryDetail.getAmount();
        this.createAt = salaryDetail.getCreatedAt();
        if(salaryDetail.getUser() != null) {
            this.userModel = new UserDTO(salaryDetail.getUser());
        }
    }

    public SalaryDetailModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SalaryType getType() {
        return type;
    }

    public void setType(SalaryType type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UserDTO getUserModel() {
        return userModel;
    }

    public void setUserModel(UserDTO userModel) {
        this.userModel = userModel;
    }
}
