package com.evowork.response;

public class WebApiResponse {

    private Integer errorCode;
    private String data;
    private String msg;

    // body:
    private String childName, dadName, address, parentPhone, childDod, allergyNote;
    private Integer gender;

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getDadName() {
        return dadName;
    }

    public void setDadName(String dadName) {
        this.dadName = dadName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getChildDod() {
        return childDod;
    }

    public void setChildDod(String childDod) {
        this.childDod = childDod;
    }

    public String getAllergyNote() {
        return allergyNote;
    }

    public void setAllergyNote(String allergyNote) {
        this.allergyNote = allergyNote;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
