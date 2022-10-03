package com.ieltshub.entity;

import com.ieltshub.enumeration.StatusType;
import com.ieltshub.enumeration.converter.StatusTypeConverter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "department_staff")
public class DepartmentStaff {

	@Id
	@SequenceGenerator(name = "department_staff_id_seq", sequenceName = "department_staff_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_staff_id_seq")
	@Column(name = "id", updatable = false)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

	@ManyToOne()
	@JoinColumn(name = "department_id", referencedColumnName = "id")
	private Department department;

	@ManyToOne()
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "status")
	@Convert(converter = StatusTypeConverter.class)
	private StatusType status;

	@Column(name = "created_at")
    private Timestamp createdAt;
	
	@ManyToOne()
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

	@Column(name = "deleted_at")
	private Timestamp deletedAt;

	@ManyToOne()
	@JoinColumn(name = "deleted_by", referencedColumnName = "id")
	private User deletedBy;

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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
}
