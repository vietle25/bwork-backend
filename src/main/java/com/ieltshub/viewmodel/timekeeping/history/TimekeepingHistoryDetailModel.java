package com.ieltshub.viewmodel.timekeeping.history;

import com.ieltshub.viewmodel.timekeeping.TimekeepingRecordModel;

import java.util.List;

public class TimekeepingHistoryDetailModel {

    List<TimekeepingRecordModel> timekeepingRecords;
    TimekeepingHistoryModel timekeepingHistory;

    public List<TimekeepingRecordModel> getTimekeepingRecords() {
        return timekeepingRecords;
    }

    public void setTimekeepingRecords(List<TimekeepingRecordModel> timekeepingRecords) {
        this.timekeepingRecords = timekeepingRecords;
    }

    public TimekeepingHistoryModel getTimekeepingHistory() {
        return timekeepingHistory;
    }

    public void setTimekeepingHistory(TimekeepingHistoryModel timekeepingHistory) {
        this.timekeepingHistory = timekeepingHistory;
    }
}
