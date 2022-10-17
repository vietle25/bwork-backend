package com.evowork.entity;

import com.evowork.enumeration.StatusType;
import com.evowork.enumeration.TaskStatus;
import com.evowork.enumeration.converter.StatusTypeConverter;
import com.evowork.enumeration.converter.TaskStatusConverter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @SequenceGenerator(name = "task_id_seq", sequenceName = "task_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Convert(converter = StatusTypeConverter.class)
    private StatusType status;

    @Column(name = "task_status")
    @Convert(converter = TaskStatusConverter.class)
    private TaskStatus taskStatus;

    @Column(name = "scheduled_date")
    private Date scheduledDate;

    @Column(name = "scheduled_time")
    private Time scheduledTime;

    @Column(name = "task_duration_minute")
    private Double taskDurationMinute;

    @Column(name = "repeat_window_type")
    private Integer repeatWindowType;

    @Column(name = "window_period")
    private Integer windowPeriod;

    @Column(name = "next_scheduled_date")
    private Date nextScheduledDate;

    @Column(name = "completed_at")
    private Timestamp completedAt;

    @ManyToOne()
    @JoinColumn(name = "set_completed_by", referencedColumnName = "id")
    private User setCompletedBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne()
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne()
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private User updatedBy;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @ManyToOne()
    @JoinColumn(name = "deleted_by", referencedColumnName = "id")
    private User deletedBy;

    @ManyToOne()
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @ManyToOne()
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private Branch branch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
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

    public Integer getRepeatWindowType() {
        return repeatWindowType;
    }

    public void setRepeatWindowType(Integer repeatWindowType) {
        this.repeatWindowType = repeatWindowType;
    }

    public Integer getWindowPeriod() {
        return windowPeriod;
    }

    public void setWindowPeriod(Integer windowPeriod) {
        this.windowPeriod = windowPeriod;
    }

    public Date getNextScheduledDate() {
        return nextScheduledDate;
    }

    public void setNextScheduledDate(Date nextScheduledDate) {
        this.nextScheduledDate = nextScheduledDate;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    public User getSetCompletedBy() {
        return setCompletedBy;
    }

    public void setSetCompletedBy(User setCompletedBy) {
        this.setCompletedBy = setCompletedBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public User getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(User deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
