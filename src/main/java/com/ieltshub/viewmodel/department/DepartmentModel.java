package com.ieltshub.viewmodel.department;

import com.ieltshub.entity.Department;

import java.sql.Timestamp;

public class DepartmentModel {

    private Long id;
    private String name;
    private Integer staffCount;
    private Timestamp createdAt;

    public DepartmentModel(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.staffCount = department.getStaffCount();
        this.createdAt = department.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStaffCount() {
        return staffCount;
    }

    public void setStaffCount(Integer staffCount) {
        this.staffCount = staffCount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
