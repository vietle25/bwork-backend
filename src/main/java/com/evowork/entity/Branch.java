package com.evowork.entity;

import com.evowork.enumeration.StatusType;
import com.evowork.enumeration.converter.StatusTypeConverter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "branch")
public class Branch {

    @Id
    @SequenceGenerator(name = "branch_id_seq", sequenceName = "branch_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "status")
    @Convert(converter = StatusTypeConverter.class)
    private StatusType status;

    @ManyToOne()
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @ManyToOne()
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @ManyToOne()
    @JoinColumn(name = "admin_user_id", referencedColumnName = "id")
    private User adminUser;

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(User adminUser) {
        this.adminUser = adminUser;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}
