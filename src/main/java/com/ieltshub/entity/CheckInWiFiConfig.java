package com.ieltshub.entity;

import com.ieltshub.enumeration.StatusType;
import com.ieltshub.enumeration.converter.StatusTypeConverter;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "check_in_wifi_config")
public class CheckInWiFiConfig {

    @Id
    @SequenceGenerator(name = "check_in_wifi_config_id_seq", sequenceName = "check_in_wifi_config_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_in_wifi_config_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

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

    @Column(name = "wifi_name")
    private String wifiName;

    @Column(name = "modem_mac_address")
    private String modemMacAddress;

    @Column(name = "gps_latitude")
    private Double gpsLatitude;

    @Column(name = "gps_longitude")
    private Double gpsLongitude;

    @ManyToOne()
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getModemMacAddress() {
        return modemMacAddress;
    }

    public void setModemMacAddress(String modemMacAddress) {
        this.modemMacAddress = modemMacAddress;
    }

    public Double getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(Double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public Double getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(Double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
