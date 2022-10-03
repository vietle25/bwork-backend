package com.ieltshub.entity;

import com.ieltshub.enumeration.*;
import com.ieltshub.enumeration.converter.*;
import com.ieltshub.mapping.StringJsonUserType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "timekeeping_record")
@TypeDefs( {@TypeDef( name= "StringJsonObject", typeClass = StringJsonUserType.class)})
public class TimekeepingRecord {

    @Id
    @SequenceGenerator(name = "timekeeping_record_id_seq", sequenceName = "timekeeping_record_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timekeeping_record_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "status")
    @Convert(converter = StatusTypeConverter.class)
    private StatusType status;

    @ManyToOne()
    @JoinColumn(name = "check_in_wifi_config_id", referencedColumnName = "id")
    private CheckInWiFiConfig checkInWiFiConfig;

    @ManyToOne()
    @JoinColumn(name = "check_out_wifi_config_id", referencedColumnName = "id")
    private CheckInWiFiConfig checkOutWiFiConfig;

    @Column(name = "check_in_time")
    private String checkInTime;

    @Column(name = "check_out_time")
    private String checkOutTime;

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

    @Column(name = "device_os")
    @Convert(converter = PlatformTypeConverter.class)
    private PlatformType deviceOs;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "check_in_submit_type")
    @Convert(converter = TimekeepingRecordSubmitTypeConverter.class)
    private TimekeepingRecordSubmitType checkInSubmitType;

    @Column(name = "check_out_submit_type")
    @Convert(converter = TimekeepingRecordSubmitTypeConverter.class)
    private TimekeepingRecordSubmitType checkOutSubmitType;

    @Column(name = "check_in_approval_status")
    @Convert(converter = ApprovalStatusTypeConverter.class)
    private ApprovalStatusType checkInApprovalStatus;

    @Column(name = "check_out_approval_status")
    @Convert(converter = ApprovalStatusTypeConverter.class)
    private ApprovalStatusType checkOutApprovalStatus;

    @Column(name = "check_in_type")
    @Convert(converter = TimekeepingTypeConverter.class)
    private TimekeepingType checkInType;

    @Column(name = "check_out_type")
    @Convert(converter = TimekeepingTypeConverter.class)
    private TimekeepingType checkOutType;

    @Column(name = "check_in_note")
    private String checkInNote;

    @Column(name = "check_out_note")
    private String checkOutNote;

    @Column(name = "check_in_approved_at")
    private Timestamp checkInApprovedAt;

    @Column(name = "check_out_approved_at")
    private Timestamp checkOutApprovedAt;

    @ManyToOne()
    @JoinColumn(name = "check_in_approved_by", referencedColumnName = "id")
    private User checkInApprovedBy;

    @ManyToOne()
    @JoinColumn(name = "check_out_approved_by", referencedColumnName = "id")
    private User checkOutApprovedBy;

    @Column(name = "check_in_gps_latitude")
    private Double checkInGpsLatitude;

    @Column(name = "check_out_gps_latitude")
    private Double checkOutGpsLatitude;

    @Column(name = "check_in_gps_longitude")
    private Double checkInGpsLongitude;

    @Column(name = "check_out_gps_longitude")
    private Double checkOutGpsLongitude;

    @Type(type = "StringJsonObject")
    @Column(name = "meta")
    private String meta;

    @ManyToOne()
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company companyId;

    @Column(name = "check_in_img_path")
    private String checkInImgPath;

    @Column(name = "check_out_img_path")
    private String checkOutImgPath;

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

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public CheckInWiFiConfig getCheckInWiFiConfig() {
        return checkInWiFiConfig;
    }

    public void setCheckInWiFiConfig(CheckInWiFiConfig checkInWiFiConfig) {
        this.checkInWiFiConfig = checkInWiFiConfig;
    }

    public CheckInWiFiConfig getCheckOutWiFiConfig() {
        return checkOutWiFiConfig;
    }

    public void setCheckOutWiFiConfig(CheckInWiFiConfig checkOutWiFiConfig) {
        this.checkOutWiFiConfig = checkOutWiFiConfig;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
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

    public PlatformType getDeviceOs() {
        return deviceOs;
    }

    public void setDeviceOs(PlatformType deviceOs) {
        this.deviceOs = deviceOs;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public TimekeepingRecordSubmitType getCheckInSubmitType() {
        return checkInSubmitType;
    }

    public void setCheckInSubmitType(TimekeepingRecordSubmitType checkInSubmitType) {
        this.checkInSubmitType = checkInSubmitType;
    }

    public TimekeepingRecordSubmitType getCheckOutSubmitType() {
        return checkOutSubmitType;
    }

    public void setCheckOutSubmitType(TimekeepingRecordSubmitType checkOutSubmitType) {
        this.checkOutSubmitType = checkOutSubmitType;
    }

    public ApprovalStatusType getCheckInApprovalStatus() {
        return checkInApprovalStatus;
    }

    public void setCheckInApprovalStatus(ApprovalStatusType checkInApprovalStatus) {
        this.checkInApprovalStatus = checkInApprovalStatus;
    }

    public ApprovalStatusType getCheckOutApprovalStatus() {
        return checkOutApprovalStatus;
    }

    public void setCheckOutApprovalStatus(ApprovalStatusType checkOutApprovalStatus) {
        this.checkOutApprovalStatus = checkOutApprovalStatus;
    }

    public TimekeepingType getCheckInType() {
        return checkInType;
    }

    public void setCheckInType(TimekeepingType checkInType) {
        this.checkInType = checkInType;
    }

    public TimekeepingType getCheckOutType() {
        return checkOutType;
    }

    public void setCheckOutType(TimekeepingType checkOutType) {
        this.checkOutType = checkOutType;
    }

    public String getCheckInNote() {
        return checkInNote;
    }

    public void setCheckInNote(String checkInNote) {
        this.checkInNote = checkInNote;
    }

    public String getCheckOutNote() {
        return checkOutNote;
    }

    public void setCheckOutNote(String checkOutNote) {
        this.checkOutNote = checkOutNote;
    }

    public Timestamp getCheckInApprovedAt() {
        return checkInApprovedAt;
    }

    public void setCheckInApprovedAt(Timestamp checkInApprovedAt) {
        this.checkInApprovedAt = checkInApprovedAt;
    }

    public Timestamp getCheckOutApprovedAt() {
        return checkOutApprovedAt;
    }

    public void setCheckOutApprovedAt(Timestamp checkOutApprovedAt) {
        this.checkOutApprovedAt = checkOutApprovedAt;
    }

    public User getCheckInApprovedBy() {
        return checkInApprovedBy;
    }

    public void setCheckInApprovedBy(User checkInApprovedBy) {
        this.checkInApprovedBy = checkInApprovedBy;
    }

    public User getCheckOutApprovedBy() {
        return checkOutApprovedBy;
    }

    public void setCheckOutApprovedBy(User checkOutApprovedBy) {
        this.checkOutApprovedBy = checkOutApprovedBy;
    }

    public Double getCheckInGpsLatitude() {
        return checkInGpsLatitude;
    }

    public void setCheckInGpsLatitude(Double checkInGpsLatitude) {
        this.checkInGpsLatitude = checkInGpsLatitude;
    }

    public Double getCheckOutGpsLatitude() {
        return checkOutGpsLatitude;
    }

    public void setCheckOutGpsLatitude(Double checkOutGpsLatitude) {
        this.checkOutGpsLatitude = checkOutGpsLatitude;
    }

    public Double getCheckInGpsLongitude() {
        return checkInGpsLongitude;
    }

    public void setCheckInGpsLongitude(Double checkInGpsLongitude) {
        this.checkInGpsLongitude = checkInGpsLongitude;
    }

    public Double getCheckOutGpsLongitude() {
        return checkOutGpsLongitude;
    }

    public void setCheckOutGpsLongitude(Double checkOutGpsLongitude) {
        this.checkOutGpsLongitude = checkOutGpsLongitude;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public String getCheckInImgPath() {
        return checkInImgPath;
    }

    public void setCheckInImgPath(String checkInImgPath) {
        this.checkInImgPath = checkInImgPath;
    }

    public String getCheckOutImgPath() {
        return checkOutImgPath;
    }

    public void setCheckOutImgPath(String checkOutImgPath) {
        this.checkOutImgPath = checkOutImgPath;
    }
}
