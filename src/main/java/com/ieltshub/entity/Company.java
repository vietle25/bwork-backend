package com.ieltshub.entity;

import com.ieltshub.enumeration.CompanyType;
import com.ieltshub.enumeration.StatusType;
import com.ieltshub.enumeration.converter.CompanyTypeConverter;
import com.ieltshub.enumeration.converter.StatusTypeConverter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@SequenceGenerator(name = "company_id_seq", sequenceName = "company_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_id_seq")
	@Column(name = "id", updatable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "phone1")
	private String phone1;

	@Column(name = "website")
	private String website;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	@Convert(converter = StatusTypeConverter.class)
	private StatusType status;

	@Column(name = "employee_count")
	private Integer employeeCount;

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

	@Column(name = "avatar_path")
	private String avatarPath;

	@Column(name = "code")
	private String code;

	@Column(name = "id_alias")
	private String idAlias;

	@Column(name = "type")
	@Convert(converter = CompanyTypeConverter.class)
	private CompanyType type;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public Integer getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(Integer employeeCount) {
		this.employeeCount = employeeCount;
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

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIdAlias() {
		return idAlias;
	}

	public void setIdAlias(String idAlias) {
		this.idAlias = idAlias;
	}

	public CompanyType getType() {
		return type;
	}

	public void setType(CompanyType type) {
		this.type = type;
	}
}
