package com.ieltshub.entity;

import com.ieltshub.enumeration.ApprovalStatusType;
import com.ieltshub.enumeration.SabbaticalOffType;
import com.ieltshub.enumeration.StatusType;
import com.ieltshub.enumeration.WorkingTimeShiftType;
import com.ieltshub.enumeration.converter.ApprovalStatusTypeConverter;
import com.ieltshub.enumeration.converter.SabbaticalOffTypeConverter;
import com.ieltshub.enumeration.converter.StatusTypeConverter;
import com.ieltshub.enumeration.converter.WorkingTimeShiftTypeConverter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "sabbatical")
public class Sabbatical {

	@Id
	@SequenceGenerator(name = "sabbatical_id_seq", sequenceName = "sabbatical_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sabbatical_id_seq")
	@Column(name = "id", updatable = false)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

	@ManyToOne()
	@JoinColumn(name = "branch_id", referencedColumnName = "id")
	private Branch branch;

	@ManyToOne()
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToOne()
	@JoinColumn(name = "department_id", referencedColumnName = "id")
	private Department department;

	@Column(name = "off_type")
	@Convert(converter = SabbaticalOffTypeConverter.class)
	private SabbaticalOffType offType;

	@Column(name = "off_from_date")
	private Date offFromDate;

	@Column(name = "off_to_date")
	private Date offToDate;

	@ManyToOne()
	@JoinColumn(name = "working_time_config_id", referencedColumnName = "id")
	private WorkingTimeConfig workingTimeConfig;

	@Column(name = "off_reason")
	private String offReason;

	@Column(name = "approval_status")
	@Convert(converter = ApprovalStatusTypeConverter.class)
	private ApprovalStatusType approvalStatus;

	@Column(name = "status")
	@Convert(converter = StatusTypeConverter.class)
	private StatusType status;

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

	@Column(name = "approved_at")
	private Timestamp approvedAt;

	@ManyToOne()
	@JoinColumn(name = "approved_by", referencedColumnName = "id")
	private User approvedBy;

	@Column(name = "denied_note")
	private String deniedNote;

	@Column(name = "denied_at")
	private Timestamp deniedAt;

	@ManyToOne()
	@JoinColumn(name = "denied_by", referencedColumnName = "id")
	private User deniedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public SabbaticalOffType getOffType() {
		return offType;
	}

	public void setOffType(SabbaticalOffType offType) {
		this.offType = offType;
	}

	public Date getOffFromDate() {
		return offFromDate;
	}

	public void setOffFromDate(Date offFromDate) {
		this.offFromDate = offFromDate;
	}

	public Date getOffToDate() {
		return offToDate;
	}

	public void setOffToDate(Date offToDate) {
		this.offToDate = offToDate;
	}

	public WorkingTimeConfig getWorkingTimeConfig() {
		return workingTimeConfig;
	}

	public void setWorkingTimeConfig(WorkingTimeConfig workingTimeConfig) {
		this.workingTimeConfig = workingTimeConfig;
	}

	public String getOffReason() {
		return offReason;
	}

	public void setOffReason(String offReason) {
		this.offReason = offReason;
	}

	public ApprovalStatusType getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalStatusType approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
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

	public Timestamp getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(Timestamp approvedAt) {
		this.approvedAt = approvedAt;
	}

	public User getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getDeniedNote() {
		return deniedNote;
	}

	public void setDeniedNote(String deniedNote) {
		this.deniedNote = deniedNote;
	}

	public Timestamp getDeniedAt() {
		return deniedAt;
	}

	public void setDeniedAt(Timestamp deniedAt) {
		this.deniedAt = deniedAt;
	}

	public User getDeniedBy() {
		return deniedBy;
	}

	public void setDeniedBy(User deniedBy) {
		this.deniedBy = deniedBy;
	}
}
