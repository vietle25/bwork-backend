package com.evowork.viewmodel.timekeeping;

import java.sql.Timestamp;
import java.util.List;

public class TimekeepingModel {

    private Timestamp today;
    private List<TimekeepingRecordModel> timekeepingRecord;

    public Timestamp getToday() {
        return today;
    }

    public void setToday(Timestamp today) {
        this.today = today;
    }

    public List<TimekeepingRecordModel> getTimekeepingRecord() {
        return timekeepingRecord;
    }

    public void setTimekeepingRecord(List<TimekeepingRecordModel> timekeepingRecord) {
        this.timekeepingRecord = timekeepingRecord;
    }
}
