package com.ieltshub.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "timekeeping_statistic")
public class TimekeepingStatistic {
	
	@Id
	@SequenceGenerator(name = "timekeeping_statistic_id_seq", sequenceName = "timekeeping_statistic_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timekeeping_statistic_id_seq")
	@Column(name = "id", updatable = false)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "day")
	private String day;

	@Column(name = "plan_working_hours_1")
	private Double planWorkingHours1;

	@Column(name = "plan_working_hours_2")
	private Double planWorkingHours2;

	@Column(name = "total_plan_working_hours")
	private Double totalPlanWorkingHours;

	@Column(name = "real_working_hours_1")
	private Double realWorkingHours1;

	@Column(name = "real_working_hours_2")
	private Double realWorkingHours2;

	@Column(name = "total_real_working_hours")
	private Double totalRealWorkingHours;

	@Column(name = "deficient_working_hours_1")
	private Double deficientWorkingHours1;

	@Column(name = "deficient_working_hours_2")
	private Double deficientWorkingHours2;

	@Column(name = "total_deficient_working_hours")
	private Double totalDeficientWorkingHours;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Double getPlanWorkingHours1() {
		return planWorkingHours1;
	}

	public void setPlanWorkingHours1(Double planWorkingHours1) {
		this.planWorkingHours1 = planWorkingHours1;
	}

	public Double getPlanWorkingHours2() {
		return planWorkingHours2;
	}

	public void setPlanWorkingHours2(Double planWorkingHours2) {
		this.planWorkingHours2 = planWorkingHours2;
	}

	public Double getTotalPlanWorkingHours() {
		return totalPlanWorkingHours;
	}

	public void setTotalPlanWorkingHours(Double totalPlanWorkingHours) {
		this.totalPlanWorkingHours = totalPlanWorkingHours;
	}

	public Double getRealWorkingHours1() {
		return realWorkingHours1;
	}

	public void setRealWorkingHours1(Double realWorkingHours1) {
		this.realWorkingHours1 = realWorkingHours1;
	}

	public Double getRealWorkingHours2() {
		return realWorkingHours2;
	}

	public void setRealWorkingHours2(Double realWorkingHours2) {
		this.realWorkingHours2 = realWorkingHours2;
	}

	public Double getTotalRealWorkingHours() {
		return totalRealWorkingHours;
	}

	public void setTotalRealWorkingHours(Double totalRealWorkingHours) {
		this.totalRealWorkingHours = totalRealWorkingHours;
	}

	public Double getDeficientWorkingHours1() {
		return deficientWorkingHours1;
	}

	public void setDeficientWorkingHours1(Double deficientWorkingHours1) {
		this.deficientWorkingHours1 = deficientWorkingHours1;
	}

	public Double getDeficientWorkingHours2() {
		return deficientWorkingHours2;
	}

	public void setDeficientWorkingHours2(Double deficientWorkingHours2) {
		this.deficientWorkingHours2 = deficientWorkingHours2;
	}

	public Double getTotalDeficientWorkingHours() {
		return totalDeficientWorkingHours;
	}

	public void setTotalDeficientWorkingHours(Double totalDeficientWorkingHours) {
		this.totalDeficientWorkingHours = totalDeficientWorkingHours;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
