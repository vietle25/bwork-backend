package com.ieltshub.viewmodel.task.taskTime;

import com.ieltshub.entity.Task;

import java.sql.Date;
import java.sql.Time;

public class TaskTimeConfigModel {

    private Long id;
    private String name;
    private Time timeStart;
    private Date day;
    private Date nextDay;

    public TaskTimeConfigModel(Task task) {
        id = task.getId();
        name = task.getName();
        timeStart = task.getScheduledTime();
        day = task.getScheduledDate();
        nextDay = task.getNextScheduledDate();
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

    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getNextDay() {
        return nextDay;
    }

    public void setNextDay(Date nextDay) {
        this.nextDay = nextDay;
    }
}
