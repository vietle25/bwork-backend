package com.evowork.entity;

import com.evowork.enumeration.StatusType;
import com.evowork.enumeration.converter.StatusTypeConverter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_branch")
public class UserBranch {

	@Id
	@SequenceGenerator(name = "user_branch_id_seq", sequenceName = "user_branch_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_branch_id_seq")
	@Column(name = "id", updatable = false)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToOne()
	@JoinColumn(name = "branch_id", referencedColumnName = "id")
	private Branch branch;
	
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

	@ManyToOne()
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

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

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
