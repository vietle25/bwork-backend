package com.ieltshub.viewmodel.user;

import java.sql.Timestamp;

import com.ieltshub.entity.AppConfig;
import com.ieltshub.enumeration.StatusType;

public class AppConfigModel {

    public static String SPEAKING_TIME_REAL = "speaking_time_real";
    public static String SPEAKING_TIME_REAL_SETUP = "speaking_time_real_setup";
    private String name; // Name config
    private Double numericValue; // Numeric
    private String textValue; // Text value
    private Timestamp dateTimeWotzValue; // Date time
    private Timestamp dateTimeValue; // Date time
    private StatusType status;

    public AppConfigModel(AppConfig appConfig) {
        name = appConfig.getName();
        numericValue = appConfig.getNumericValue();
        textValue = appConfig.getTextValue();
        dateTimeValue = appConfig.getDateTimeValue();
        dateTimeWotzValue = appConfig.getDateTimeWotzValue();
        status = appConfig.getStatus();
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(Double numericValue) {
        this.numericValue = numericValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public Timestamp getDateTimeWotzValue() {
        return dateTimeWotzValue;
    }

    public void setDateTimeWotzValue(Timestamp dateTimeWotzValue) {
        this.dateTimeWotzValue = dateTimeWotzValue;
    }

    public Timestamp getDateTimeValue() {
        return dateTimeValue;
    }

    public void setDateTimeValue(Timestamp dateTimeValue) {
        this.dateTimeValue = dateTimeValue;
    }

}
