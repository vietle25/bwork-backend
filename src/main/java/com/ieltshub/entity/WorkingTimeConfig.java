package com.ieltshub.entity;

import com.ieltshub.enumeration.StatusType;
import com.ieltshub.enumeration.WorkingTimeShiftType;
import com.ieltshub.enumeration.WorkingTimeType;
import com.ieltshub.enumeration.converter.StatusTypeConverter;
import com.ieltshub.enumeration.converter.WorkingTimeShiftTypeConverter;
import com.ieltshub.enumeration.converter.WorkingTimeTypeConverter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "working_time_config")
public class WorkingTimeConfig {

    @Id
    @SequenceGenerator(name = "working_time_config_id_seq", sequenceName = "working_time_config_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "working_time_config_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "type")
    @Convert(converter = WorkingTimeTypeConverter.class)
    private WorkingTimeType workingTimeType;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Convert(converter = StatusTypeConverter.class)
    private StatusType status;

    @Column(name = "num_working_hours")
    private Double numWorkingHours;

    @Column(name = "num_day_off_in_month")
    private Integer numDayOffInMonth;

    @Column(name = "start_working_time_1")
    private String startWorkingTime1;

    @Column(name = "end_working_time_1")
    private String endWorkingTime1;

    @Column(name = "start_working_time_2")
    private String startWorkingTime2;

    @Column(name = "end_working_time_2")
    private String endWorkingTime2;

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

    @Column(name = "valid_from")
    private String validFrom;

    @Column(name = "shift_type")
    @Convert(converter = WorkingTimeShiftTypeConverter.class)
    private WorkingTimeShiftType shiftType;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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

    public WorkingTimeType getWorkingTimeType() {
        return workingTimeType;
    }

    public void setWorkingTimeType(WorkingTimeType workingTimeType) {
        this.workingTimeType = workingTimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Double getNumWorkingHours() {
        return numWorkingHours;
    }

    public void setNumWorkingHours(Double numWorkingHours) {
        this.numWorkingHours = numWorkingHours;
    }

    public Integer getNumDayOffInMonth() {
        return numDayOffInMonth;
    }

    public void setNumDayOffInMonth(Integer numDayOffInMonth) {
        this.numDayOffInMonth = numDayOffInMonth;
    }

    public String getStartWorkingTime1() {
        return startWorkingTime1;
    }

    public void setStartWorkingTime1(String startWorkingTime1) {
        this.startWorkingTime1 = startWorkingTime1;
    }

    public String getEndWorkingTime1() {
        return endWorkingTime1;
    }

    public void setEndWorkingTime1(String endWorkingTime1) {
        this.endWorkingTime1 = endWorkingTime1;
    }

    public String getStartWorkingTime2() {
        return startWorkingTime2;
    }

    public void setStartWorkingTime2(String startWorkingTime2) {
        this.startWorkingTime2 = startWorkingTime2;
    }

    public String getEndWorkingTime2() {
        return endWorkingTime2;
    }

    public void setEndWorkingTime2(String endWorkingTime2) {
        this.endWorkingTime2 = endWorkingTime2;
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

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public WorkingTimeShiftType getShiftType() {
        return shiftType;
    }

    public void setShiftType(WorkingTimeShiftType shiftType) {
        this.shiftType = shiftType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
