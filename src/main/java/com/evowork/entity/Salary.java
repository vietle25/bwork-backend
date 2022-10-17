package com.evowork.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "salary")
public class Salary {
    @Id
    @SequenceGenerator(name = "salary_id_seq", sequenceName = "salary_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salary_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "period")
    private String period;

    @Column(name = "net_amount")
    private BigDecimal netAmount;

    @Column(name = "pay_amount")
    private BigDecimal payAmount;

    @Column(name = "total_bonus_amount")
    private BigDecimal totalBonusAmount;

    @Column(name = "total_fine_amount")
    private BigDecimal totalFineAmount;

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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getTotalBonusAmount() {
        return totalBonusAmount;
    }

    public void setTotalBonusAmount(BigDecimal totalBonusAmount) {
        this.totalBonusAmount = totalBonusAmount;
    }

    public BigDecimal getTotalFineAmount() {
        return totalFineAmount;
    }

    public void setTotalFineAmount(BigDecimal totalFineAmount) {
        this.totalFineAmount = totalFineAmount;
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
}
