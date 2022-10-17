package com.evowork.viewmodel.user;

import java.sql.Timestamp;

public class OTPModel {

    private String codeOTP;
    private Timestamp endAt; //End time otp
    private String status; // status

    //  Status:
    //•	- 1: Sent Success
    //•	- 2: Username/Password Invalid
    //•	- 3: Invalid Phonenumber
    //•	- 4: Exceeds the message’s length
    //•	- 6: Fail template
    //•	- 8: Spam
    //•	- 10: advertisement content
    //•	- 11: Send Unicode message without parameter
    //•	- 1: Other error (or BrandName)

    public OTPModel(String codeOTP) {
        this.codeOTP = codeOTP;
    }

    public OTPModel() {
    }

    public String getCodeOTP() {
        return codeOTP;
    }

    public void setCodeOTP(String codeOTP) {
        this.codeOTP = codeOTP;
    }

    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
