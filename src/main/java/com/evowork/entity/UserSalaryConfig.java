package com.evowork.entity;

import com.evowork.enumeration.UserSalaryInputType;
import com.evowork.enumeration.converter.UserSalaryInputTypeConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "user_salary_config")
public class UserSalaryConfig {

	@Id
	@SequenceGenerator(name = "user_salary_config_id_seq", sequenceName = "user_salary_config_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_salary_config_id_seq")
	@Column(name = "id", updatable = false)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "input_type")
	@Convert(converter = UserSalaryInputTypeConverter.class)
	private UserSalaryInputType inputType;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "valid_from")
	private String valid;

	@Column(name = "created_at")
    private Timestamp createdAt;
	
	@ManyToOne()
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

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

	public UserSalaryInputType getInputType() {
		return inputType;
	}

	public void setInputType(UserSalaryInputType inputType) {
		this.inputType = inputType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
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
}
