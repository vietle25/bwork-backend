package com.ieltshub.viewmodel.task;

import com.ieltshub.entity.Task;
import com.ieltshub.viewmodel.user.UserDTO;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class TaskModel {

    private Long id;
    private String name;
    private UserDTO user;
    private Integer status;
    private Integer taskStatus;
    private UserDTO createdBy;
    private Timestamp createdAt;
    private Timestamp completedAt;
    private String description;
    private Date scheduledDate;
    private Time scheduledTime;
    private Double taskDurationMinute;
    private Integer repeatType;
    private Integer windowType;
    private Date nextScheduledDate;

    public TaskModel(Task task) {

        id = task.getId();
        name = task.getName();
        if(task.getUser() != null) {
            user = new UserDTO(task.getUser());
        }
        status = task.getStatus().getValue();
        taskStatus = task.getTaskStatus().getValue();
        description = task.getDescription();
        if(task.getCreatedBy() != null) {
            createdBy = new UserDTO(task.getCreatedBy());
        }
        createdAt = task.getCreatedAt();
        completedAt = task.getCompletedAt();
        scheduledDate = task.getScheduledDate();
        scheduledTime = task.getScheduledTime();
        taskDurationMinute = task.getTaskDurationMinute();
        repeatType = task.getRepeatWindowType();
        windowType = task.getWindowPeriod();
        nextScheduledDate = task.getNextScheduledDate();

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Time getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Time scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Double getTaskDurationMinute() {
        return taskDurationMinute;
    }

    public void setTaskDurationMinute(Double taskDurationMinute) {
        this.taskDurationMinute = taskDurationMinute;
    }

    public Integer getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(Integer repeatType) {
        this.repeatType = repeatType;
    }

    public Date getNextScheduledDate() {
        return nextScheduledDate;
    }

    public void setNextScheduledDate(Date nextScheduledDate) {
        this.nextScheduledDate = nextScheduledDate;
    }

    public Integer getWindowType() {
        return windowType;
    }

    public void setWindowType(Integer windowType) {
        this.windowType = windowType;
    }
}
