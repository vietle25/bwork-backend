package com.evowork.viewmodel.company;

import com.evowork.entity.Company;
import com.evowork.enumeration.CompanyType;
import com.evowork.viewmodel.department.DepartmentModel;
import com.evowork.viewmodel.location.BranchModel;
import com.evowork.viewmodel.user.CategoryModel;

import java.util.List;

public class CompanyModel {

    private Long id;
    private String name;
    private String address;
    private String phone1;
    private String website;
    private String description;
    private Integer employeeCount;
    private String avatarPath;
    private List<BranchModel> branches;
    private List<DepartmentModel> departments;
    private List<CategoryModel> staffs;
    private String idAlias;
    private Integer type;

    public CompanyModel(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.address = company.getAddress();
        this.phone1 = company.getPhone1();
        this.website = company.getWebsite();
        this.description = company.getDescription();
        this.employeeCount = company.getEmployeeCount();
        this.avatarPath = company.getAvatarPath();
        this.idAlias = company.getIdAlias();
        if (company.getType() != null) {
            this.type = company.getType().getValue();
        } else {
            this.type = CompanyType.BASIC.getValue();
        }
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public List<BranchModel> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchModel> branches) {
        this.branches = branches;
    }

    public List<DepartmentModel> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentModel> departments) {
        this.departments = departments;
    }

    public List<CategoryModel> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<CategoryModel> staffs) {
        this.staffs = staffs;
    }

    public String getIdAlias() {
        return idAlias;
    }

    public void setIdAlias(String idAlias) {
        this.idAlias = idAlias;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
