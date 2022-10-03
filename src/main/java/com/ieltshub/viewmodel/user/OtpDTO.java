package com.ieltshub.viewmodel.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OtpDTO implements Serializable {

    private String CodeResult;
    private String ErrorMessage;
    private String SMSID;

    public OtpDTO() {
    }

    public String getCodeResult() {
        return CodeResult;
    }

    public void setCodeResult(String codeResult) {
        CodeResult = codeResult;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getSMSID() {
        return SMSID;
    }

    public void setSMSID(String SMSID) {
        this.SMSID = SMSID;
    }
}
