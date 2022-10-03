package com.ieltshub.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ieltshub.enumeration.GenderType;
import com.ieltshub.enumeration.StatusType;
import com.ieltshub.enumeration.UserType;
import com.ieltshub.enumeration.converter.GenderTypeConverter;
import com.ieltshub.enumeration.converter.StatusTypeConverter;
import com.ieltshub.enumeration.converter.UserTypeConverter;

@Entity
@Table(name = "users")
public class User {

	@Id
	@SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	@Column(name = "id", updatable = false)
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "address")
	private String address;

	@Column(name = "domicile")
	private String domicile;

	@Column(name = "personal_id")
	private String personalId;

	@Column(name = "avatar_path")
	private String avatarPath;

	@Column(name = "gender")
	@Convert(converter = GenderTypeConverter.class)
	private GenderType gender;

	@Column(name = "date_of_birth")
	private Timestamp birthDate;
	
	@Column(name = "status")
    @Convert(converter = StatusTypeConverter.class)
    private StatusType status;

	@Column(name = "type")
	@Convert(converter = UserTypeConverter.class)
	private UserType userType;

	@Column(name = "fb_id")
	private String fbId;

	@Column(name = "gg_id")
	private String ggId;

	@Column(name = "password")
	private String password;

	@Column(name = "remember_token")
	private String rememberToken;

	@Column(name = "failed_login_attemps")
	private Integer failedLoginAttemps;

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

	@Column(name = "start_work_time")
	private Timestamp startWorkTime;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@ManyToOne()
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

	@ManyToOne()
	@JoinColumn(name = "staff_type_id", referencedColumnName = "id")
	private Category staffType;

	@Column(name = "person_id")
	private String personId;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDomicile() {
		return domicile;
	}

	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public Timestamp getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Timestamp birthDate) {
		this.birthDate = birthDate;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	public String getGgId() {
		return ggId;
	}

	public void setGgId(String ggId) {
		this.ggId = ggId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRememberToken() {
		return rememberToken;
	}

	public void setRememberToken(String rememberToken) {
		this.rememberToken = rememberToken;
	}

	public Integer getFailedLoginAttemps() {
		return failedLoginAttemps;
	}

	public void setFailedLoginAttemps(Integer failedLoginAttemps) {
		this.failedLoginAttemps = failedLoginAttemps;
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

	public Timestamp getStartWorkTime() {
		return startWorkTime;
	}

	public void setStartWorkTime(Timestamp startWorkTime) {
		this.startWorkTime = startWorkTime;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Category getStaffType() {
		return staffType;
	}

	public void setStaffType(Category staffType) {
		this.staffType = staffType;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
}
