package com.evowork.business;

import com.evowork.common.ErrorCode;
import com.evowork.entity.*;
import com.evowork.enumeration.*;
import com.evowork.exception.BusinessException;
import com.evowork.repository.*;
import com.evowork.utility.DateUtility;
import com.evowork.utility.StringUtility;
import com.evowork.viewmodel.cache.UserInfo;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.timekeeping.*;
import com.evowork.viewmodel.timekeeping.history.TimekeepingHistoryDetailModel;
import com.evowork.viewmodel.timekeeping.history.TimekeepingHistoryFilter;
import com.evowork.viewmodel.timekeeping.history.TimekeepingHistoryModel;
import com.evowork.viewmodel.timekeeping.wifi.WiFiConfigFilter;
import com.evowork.viewmodel.timekeeping.wifi.WiFiConfigModel;
import com.evowork.viewmodel.timekeeping.wokingTime.WorkingTimeConfigModel;
import com.evowork.viewmodel.user.UserDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimekeepingBusImpl extends AbstractBusiness implements TimekeepingBus {

    @Autowired
    CommonBusImpl commonBus;

    @Autowired
    CheckInWiFiConfigRepository checkInWiFiConfigRepo;

    @Autowired
    WorkingTimeConfigRepository workingTimeConfigRepo;

    @Autowired
    TimekeepingRecordRepository timekeepingRecordRepo;

    @Autowired
    TimekeepingStatisticRepository timekeepingStatisticRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserLoginLogRepository userLoginLogRepo;

    @Autowired
    AppConfigRepository appConfigRepo;

    @Autowired
    CompanyRepository companyRepo;

    @Autowired
    UserBranchRepository userBranchRepo;

    @Override
    public String getPhoneByUserId(Long userId) {
        Optional<User> user = userRepo.findById(userId);
        return user.get().getPhone();
    }

    @Override
    public ErrorCode getTimekeepingCheckIn(TimekeepingFilter filter, UserInfo userInfo) throws BusinessException {
        initialize();
        String toDay = DateUtility.formatDateWithTimeZone(sysdate, DateUtility.FORMAT_DATE_SQL, "Asia/Saigon");
        TimekeepingRecord timekeepingRecord = timekeepingRecordRepo.getTimekeepingCheckIn(
                userInfo.getUserId(),
                StatusType.ACTIVE.getValue(),
                toDay
        );
        if (timekeepingRecord != null) {
            if (filter.getCheckingIn()) {
                return ErrorCode.NO_ERROR;
            } else {
                return ErrorCode.BUS_USER_ALREADY_CHECK_IN;
            }
        } else {
            if (!filter.getCheckingIn()) {
                return ErrorCode.NO_ERROR;
            } else {
                return ErrorCode.BUS_USER_ALREADY_CHECK_OUT;
            }
        }
    }

    @Override
    public UserLoginLog getUserDeviceLogin(Long userId, String deviceId) {
        Optional<User> user = userRepo.findById(userId);
        UserLoginLog userDeviceLogin = userLoginLogRepo
                .getUserDeviceLogin(deviceId, userId, StatusType.ACTIVE.getValue());
        return userDeviceLogin;
    }

    @Override
    public List<WiFiConfigModel> getWiFiConfig(Long userId) throws BusinessException {
        List<WiFiConfigModel> wiFiConfigs = new ArrayList<>();
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserBranch userBranch = userBranchRepo.getUserBranchByUserId(userId, StatusType.ACTIVE.getValue());
            List<CheckInWiFiConfig> checkInWiFiConfigs;
            if (userBranch != null) {
                checkInWiFiConfigs = checkInWiFiConfigRepo.getWiFiConfig(
                        StatusType.ACTIVE.getValue(), user.getCompany().getId(), userBranch.getBranch().getId());
            } else {
                checkInWiFiConfigs = checkInWiFiConfigRepo.getWiFiConfig(
                        StatusType.ACTIVE.getValue(), user.getCompany().getId());
            }
            wiFiConfigs = getWifi(checkInWiFiConfigs);
        }
        return wiFiConfigs;
    }

    @Override
    public List<WiFiConfigModel> getWiFiConfigAdmin(WiFiConfigFilter filter) throws BusinessException {
        List<CheckInWiFiConfig> checkInWiFiConfigs;
        if (filter.getBranchId() != null) {
            checkInWiFiConfigs = checkInWiFiConfigRepo.getWiFiConfig(
                    StatusType.ACTIVE.getValue(), filter.getCompanyId(), filter.getBranchId());
        } else {
            checkInWiFiConfigs = checkInWiFiConfigRepo.getWiFiConfig(
                    StatusType.ACTIVE.getValue(), filter.getCompanyId());
        }
        return getWifi(checkInWiFiConfigs);
    }

    @Override
    public WorkingTimeConfigModel getWorkingTimeConfig(Long userId, String platform) throws BusinessException {
        initialize();
        String toDay = DateUtility.formatDateWithTimeZone(sysdate, DateUtility.FORMAT_DATE_SQL, "Asia/Saigon");
        return this.getWorkingTime(userId, toDay, platform);
    }

    @Override
    public TimekeepingRecordModel timekeeping(TimekeepingFilter filter, UserInfo userInfo, String platform) throws BusinessException {
        initialize();
        String toDay = DateUtility.formatDateWithTimeZone(sysdate, DateUtility.FORMAT_DATE_SQL, "Asia/Saigon");
        String timeCheck = DateUtility.
                formatDateWithTimeZone(sysdate, DateUtility.FORMAT_TIME, "Asia/Saigon") + ":00";
        Long userId = userInfo.getUserId();
        Optional<User> userOptional = userRepo.findById(userId);
        User user = userOptional.get();
        TimekeepingRecord checkIn = timekeepingRecordRepo
                .getTimekeepingCheckIn(userId, StatusType.ACTIVE.getValue(), toDay);
        CheckInWiFiConfig checkInWiFiConfig = null;
        if (filter.getWiFiConfigId() != null) {
            Optional<CheckInWiFiConfig> inWiFiConfigOptional = checkInWiFiConfigRepo.findById(filter.getWiFiConfigId());
            if (inWiFiConfigOptional.isPresent()) {
                checkInWiFiConfig = inWiFiConfigOptional.get();
            }
        }
        if (checkIn == null) {
            TimekeepingRecord checkInExact = timekeepingRecordRepo
                    .getTimekeepingCheckInExact(timeCheck, userId, StatusType.ACTIVE.getValue(), toDay);
            if (checkInExact != null) {
                checkInExact.setStatus(StatusType.DELETE);
                timekeepingRecordRepo.save(checkInExact);
            }
            TimekeepingRecord startCheckIn = new TimekeepingRecord();
            startCheckIn.setUser(user);
            startCheckIn.setCompanyId(user.getCompany());
            startCheckIn.setStatus(StatusType.ACTIVE);
            startCheckIn.setCheckInWiFiConfig(checkInWiFiConfig);
            startCheckIn.setCheckInTime(timeCheck);
            startCheckIn.setCreatedAt(sysTimestamp);
            startCheckIn.setCreatedBy(user);
            if (StringUtility.isNotEmpty(platform) && platform.equals("android")) {
                startCheckIn.setDeviceOs(PlatformType.ANDROID);
            } else {
                startCheckIn.setDeviceOs(PlatformType.IOS);
            }
            JSONObject deviceJsonObject = new JSONObject();
            try {
                deviceJsonObject.put("appVersion", filter.getDeviceInfo().getAppVersion());
                deviceJsonObject.put("osVersion", filter.getDeviceInfo().getOsVersion());
                deviceJsonObject.put("model", filter.getDeviceInfo().getModel());
                deviceJsonObject.put("macAddress", filter.getDeviceInfo().getMacAddress());
                deviceJsonObject.put("serial", filter.getDeviceInfo().getSerial());
                deviceJsonObject.put("imei1", filter.getDeviceInfo().getImei1());
                deviceJsonObject.put("imei2", filter.getDeviceInfo().getImei2());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startCheckIn.setDeviceInfo(String.valueOf(deviceJsonObject));
            if (filter.getSubmitType() == TimekeepingRecordSubmitType.AUTO_BY_DEVICE.getValue()) {
                startCheckIn.setCheckInSubmitType(TimekeepingRecordSubmitType.AUTO_BY_DEVICE);
                startCheckIn.setCheckInApprovalStatus(ApprovalStatusType.APPROVED);
            } else if (filter.getSubmitType() == TimekeepingRecordSubmitType.WEB_ADMIN.getValue()) {
                startCheckIn.setCheckInSubmitType(TimekeepingRecordSubmitType.WEB_ADMIN);
                startCheckIn.setCheckInApprovalStatus(ApprovalStatusType.WAITING_FOR_APPROVAL);
            }
            if (filter.getType() == TimekeepingType.NORMAL.getValue()) {
                startCheckIn.setCheckInType(TimekeepingType.NORMAL);
            } else if (filter.getType() == TimekeepingType.OUT_OFF_WORKING_TIME.getValue()) {
                startCheckIn.setCheckInType(TimekeepingType.OUT_OFF_WORKING_TIME);
            } else if (filter.getType() == TimekeepingType.CONNECTED_TO_INCORRECT_WI_FI.getValue()) {
                startCheckIn.setCheckInType(TimekeepingType.CONNECTED_TO_INCORRECT_WI_FI);
            }
            startCheckIn.setCheckInNote(filter.getNote());
            startCheckIn.setCheckInGpsLatitude(filter.getGpsLatitude());
            startCheckIn.setCheckInGpsLongitude(filter.getGpsLongitude());
            JSONObject metaJsonObject = new JSONObject();
            try {
                JSONObject checkInJsonObject = new JSONObject();
                checkInJsonObject.put("deviceErrorCode", filter.getTimekeepingMeta().getDeviceErrorCode());
                checkInJsonObject.put("wiFiName", filter.getTimekeepingMeta().getWiFiName());
                checkInJsonObject.put("modemMacAddress", filter.getTimekeepingMeta().getModemMacAddress());
                checkInJsonObject.put("personalIdAccuracy", filter.getTimekeepingMeta().getPersonalIdAccuracy());
                metaJsonObject.put("checkIn", checkInJsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startCheckIn.setMeta(String.valueOf(metaJsonObject));
            startCheckIn.setCheckInImgPath(filter.getImgPath());
            timekeepingRecordRepo.save(startCheckIn);
            return new TimekeepingRecordModel(startCheckIn);
        } else {
            TimekeepingRecord checkOutPrevious = timekeepingRecordRepo
                    .getTimekeepingCheckOutExact(timeCheck, userId, StatusType.ACTIVE.getValue(), toDay);
            if (checkOutPrevious != null) {
                checkIn.setStatus(StatusType.DELETE);
                timekeepingRecordRepo.save(checkIn);
            } else {
                checkIn.setCheckOutWiFiConfig(checkInWiFiConfig);
                checkIn.setCheckOutTime(timeCheck);
                checkIn.setUpdatedAt(sysTimestamp);
                checkIn.setUpdatedBy(user);
                if (filter.getSubmitType() == TimekeepingRecordSubmitType.AUTO_BY_DEVICE.getValue()) {
                    checkIn.setCheckOutSubmitType(TimekeepingRecordSubmitType.AUTO_BY_DEVICE);
                    checkIn.setCheckOutApprovalStatus(ApprovalStatusType.APPROVED);
                } else if (filter.getSubmitType() == TimekeepingRecordSubmitType.WEB_ADMIN.getValue()) {
                    checkIn.setCheckOutSubmitType(TimekeepingRecordSubmitType.WEB_ADMIN);
                    checkIn.setCheckOutApprovalStatus(ApprovalStatusType.WAITING_FOR_APPROVAL);
                }
                if (filter.getType() == TimekeepingType.NORMAL.getValue()) {
                    checkIn.setCheckOutType(TimekeepingType.NORMAL);
                } else if (filter.getType() == TimekeepingType.OUT_OFF_WORKING_TIME.getValue()) {
                    checkIn.setCheckOutType(TimekeepingType.OUT_OFF_WORKING_TIME);
                } else if (filter.getType() == TimekeepingType.CONNECTED_TO_INCORRECT_WI_FI.getValue()) {
                    checkIn.setCheckOutType(TimekeepingType.CONNECTED_TO_INCORRECT_WI_FI);
                }
                checkIn.setCheckOutNote(filter.getNote());
                checkIn.setCheckOutGpsLatitude(filter.getGpsLatitude());
                checkIn.setCheckOutGpsLongitude(filter.getGpsLongitude());
                if (checkIn.getMeta() != null) {
                    JSONObject metaJsonObject = new JSONObject(checkIn.getMeta());
                    try {
                        JSONObject checkOutJsonObject = new JSONObject();
                        checkOutJsonObject.put("deviceErrorCode", filter.getTimekeepingMeta().getDeviceErrorCode());
                        checkOutJsonObject.put("wiFiName", filter.getTimekeepingMeta().getWiFiName());
                        checkOutJsonObject.put("modemMacAddress", filter.getTimekeepingMeta().getModemMacAddress());
                        checkOutJsonObject.put("personalIdAccuracy", filter.getTimekeepingMeta().getPersonalIdAccuracy());
                        metaJsonObject.put("checkOut", checkOutJsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    checkIn.setMeta(String.valueOf(metaJsonObject));
                }
                checkIn.setCheckOutImgPath(filter.getImgPath());
                timekeepingRecordRepo.save(checkIn);
            }
            return new TimekeepingRecordModel(checkIn);
        }
    }

    @Override
    public TimekeepingModel getTimekeeping(Long userId) throws BusinessException {
        initialize();
        String toDay = DateUtility.formatDateWithTimeZone(sysdate, DateUtility.FORMAT_DATE_SQL, "Asia/Saigon");
        TimekeepingModel timekeepingModel = new TimekeepingModel();
        timekeepingModel.setToday(sysTimestamp);
        timekeepingModel.setTimekeepingRecord(this.getTimekeepingRecords(userId, toDay));
        return timekeepingModel;
    }

    @Override
    public PaginationResult<TimekeepingListModel> getTimekeepingList(TimekeepingListFilter filter, String platform)
            throws BusinessException {
        PaginationResult<User> userPaginationResult =
                timekeepingRecordRepo.getUserTimekeeping(filter);
        PaginationResult<TimekeepingListModel> result = new PaginationResult<>(userPaginationResult.getPaging());
        result.setData(userPaginationResult.getData().stream().map(item -> {
            WorkingTimeConfigModel workingTimeConfig = this.getWorkingTime(item.getId(), filter.getDay(), platform);
            TimekeepingListModel model = new TimekeepingListModel();
            model.setUser(new UserDTO(item));
            if (filter.getDashboardType() == DashboardType.NOT_CHECK_IN.getValue()) {
                model.setTimekeepingRecord(this.getTimekeepingRecords(workingTimeConfig));
            } else {
                model.setTimekeepingRecord(this.getTimekeepingRecords(item.getId(), filter.getDay()));
            }
            model.setWorkingTimeConfig(workingTimeConfig);
            return model;
        }).collect(Collectors.toList()));
        return result;
    }

    private List<TimekeepingRecordModel> getTimekeepingRecords(Long userId, String day) {
        List<TimekeepingRecordModel> timekeepingRecordModels = new ArrayList<>();
        List<TimekeepingRecord> timekeepingRecords = timekeepingRecordRepo.getTimekeeping(
                userId, StatusType.ACTIVE.getValue(), day);
        if (timekeepingRecords != null && timekeepingRecords.size() > 0) {
            for (TimekeepingRecord timekeepingRecord : timekeepingRecords) {
                timekeepingRecordModels.add(new TimekeepingRecordModel(timekeepingRecord));
            }
        }
        return timekeepingRecordModels;
    }

    private List<TimekeepingRecordModel> getTimekeepingRecords(WorkingTimeConfigModel workingTimeConfig) {
        List<TimekeepingRecordModel> timekeepingRecordModels = new ArrayList<>();
        if (workingTimeConfig != null) {
            if (workingTimeConfig.getStartWorkingTime1() != null && workingTimeConfig.getEndWorkingTime1() != null) {
                TimekeepingRecordModel timekeepingRecordModel = new TimekeepingRecordModel();
                timekeepingRecordModel.setCheckInTime(workingTimeConfig.getStartWorkingTime1());
                timekeepingRecordModel.setCheckOutTime(workingTimeConfig.getEndWorkingTime1());
                timekeepingRecordModels.add(timekeepingRecordModel);
            }
            if (workingTimeConfig.getStartWorkingTime2() != null && workingTimeConfig.getEndWorkingTime2() != null) {
                TimekeepingRecordModel timekeepingRecordModel = new TimekeepingRecordModel();
                timekeepingRecordModel.setCheckInTime(workingTimeConfig.getStartWorkingTime2());
                timekeepingRecordModel.setCheckOutTime(workingTimeConfig.getEndWorkingTime2());
                timekeepingRecordModels.add(timekeepingRecordModel);
            }
        }
        return timekeepingRecordModels;
    }

    @Override
    public List<TimekeepingHistoryModel> getTimekeepingHistory(TimekeepingHistoryFilter filter, Long userId, String platform)
            throws BusinessException {
        List<TimekeepingHistoryModel> timekeepingHistoryModels = new ArrayList<>();
        Optional<User> userOptional = userRepo.findById(userId);
        if (filter.getDay() != null && userOptional.isPresent()) {
            User user = userOptional.get();
            if ("All".equals(filter.getDay())) {
                List<String> dayOfMonths = timekeepingRecordRepo.getDayOfMonth(userId, filter.getMonth());
                if (dayOfMonths != null && dayOfMonths.size() > 0) {
                    for (String day : dayOfMonths) {
                        TimekeepingHistoryModel timekeepingHistoryModel = this.getTimekeepingByDay(day, user, platform);
                        if (timekeepingHistoryModel != null) {
                            timekeepingHistoryModels.add(timekeepingHistoryModel);
                        }
                    }
                }
            } else {
                String day = filter.getMonth() + "-" + filter.getDay();
                TimekeepingHistoryModel timekeepingHistoryModel = this.getTimekeepingByDay(day, user, platform);
                if (timekeepingHistoryModel != null) {
                    timekeepingHistoryModels.add(timekeepingHistoryModel);
                }
            }
        }
        return timekeepingHistoryModels;
    }

    @Override
    public TimekeepingHistoryDetailModel getTimekeepingHistoryDetail(TimekeepingHistoryFilter filter, Long userId,
                                                                     String platform)
            throws BusinessException {
        Optional<User> userOptional = userRepo.findById(userId);
        TimekeepingHistoryDetailModel historyDetailModel = new TimekeepingHistoryDetailModel();
        List<TimekeepingRecordModel> timekeepingRecordModels = new ArrayList<>();
        List<TimekeepingRecord> timekeepingRecords = timekeepingRecordRepo.getTimekeeping(
                userId, StatusType.ACTIVE.getValue(), filter.getCreatedAt());
        if (userOptional.isPresent() && timekeepingRecords != null && timekeepingRecords.size() > 0) {
            User user = userOptional.get();
            for (TimekeepingRecord timekeepingRecord : timekeepingRecords) {
                timekeepingRecordModels.add(new TimekeepingRecordModel(timekeepingRecord));
            }
            TimekeepingHistoryModel timekeepingHistoryModel =
                    this.getTimekeepingByDay(filter.getCreatedAt(), user, platform);
            historyDetailModel.setTimekeepingRecords(timekeepingRecordModels);
            if (timekeepingHistoryModel != null) {
                historyDetailModel.setTimekeepingHistory(timekeepingHistoryModel);
            }
        }
        return historyDetailModel;
    }

    @Override
    public TimekeepingRecordModel approvalTimekeeping(ApprovalTimekeepingFilter filter, Long userId, Long timekeepingId)
            throws BusinessException {
        initialize();
        Optional<TimekeepingRecord> timekeepingRecordOptional = timekeepingRecordRepo.findById(timekeepingId);
        if (timekeepingRecordOptional.isPresent()) {
            Optional<User> userOptional = userRepo.findById(userId);
            TimekeepingRecord timekeepingRecord = timekeepingRecordOptional.get();
            if (filter.getCheckInType() == CheckInType.CHECK_IN.getValue()) {
                timekeepingRecord.setCheckInApprovalStatus(ApprovalStatusType.parse(filter.getApprovalStatus()));
                timekeepingRecord.setCheckInApprovedAt(sysTimestamp);
                timekeepingRecord.setCheckInApprovedBy(userOptional.get());
            } else {
                timekeepingRecord.setCheckOutApprovalStatus(ApprovalStatusType.parse(filter.getApprovalStatus()));
                timekeepingRecord.setCheckOutApprovedAt(sysTimestamp);
                timekeepingRecord.setCheckOutApprovedBy(userOptional.get());
            }
            timekeepingRecord = timekeepingRecordRepo.save(timekeepingRecord);
            return new TimekeepingRecordModel(timekeepingRecord);
        }
        return null;
    }

    @Override
    public String uploadImagePath(MultipartFile file, Long userId) {
        String filePath = "";
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            filePath = commonBus.uploadFile(file, "user/" + userId + "/timekeeping/imgPath", user.getCompany().getIdAlias());
        }
        return filePath;
    }

    @Override
    public TimekeepingRecordModel timekeepingAdmin(TimekeepingAdminFilter filter, Long adminId) throws BusinessException {
        initialize();
        if (filter.getUserId() != null) {
            Optional<User> userOptional = userRepo.findById(filter.getUserId());
            Timestamp createdAt = DateUtility.convertStringToTimestamp(filter.getDay());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                TimekeepingRecord timekeepingRecord = new TimekeepingRecord();
                timekeepingRecord.setUser(user);
                timekeepingRecord.setCompanyId(user.getCompany());
                timekeepingRecord.setStatus(StatusType.ACTIVE);
                timekeepingRecord.setCreatedAt(createdAt);
                timekeepingRecord.setCreatedBy(userRepo.findById(adminId).get());
                timekeepingRecord.setCheckInType(TimekeepingType.NORMAL);
                if (filter.getCheckoutTime() != null) {
                    timekeepingRecord.setCheckOutType(TimekeepingType.NORMAL);
                }
                timekeepingRecord = setTimekeeping(timekeepingRecord, filter);
                return new TimekeepingRecordModel(timekeepingRecord);
            }
        }
        return null;
    }

    @Override
    public TimekeepingRecordModel timekeepingUpdate(Long timekeepingId, TimekeepingAdminFilter filter, Long adminId) {
        initialize();
        Optional<TimekeepingRecord> timekeepingRecordOptional = timekeepingRecordRepo.findById(timekeepingId);
        if (timekeepingRecordOptional.isPresent()) {
            TimekeepingRecord timekeepingRecord = timekeepingRecordOptional.get();
            timekeepingRecord.setUpdatedAt(sysTimestamp);
            timekeepingRecord.setUpdatedBy(userRepo.findById(adminId).get());
            timekeepingRecord = setTimekeeping(timekeepingRecord, filter);
            return new TimekeepingRecordModel(timekeepingRecord);
        }
        return null;
    }

    @Override
    public TimekeepingRecordModel timekeepingDelete(Long timekeepingId, Long adminId) throws BusinessException {
        Optional<TimekeepingRecord> timekeepingRecordOptional = timekeepingRecordRepo.findById(timekeepingId);
        if (timekeepingRecordOptional.isPresent()) {
            TimekeepingRecord timekeepingRecord = timekeepingRecordOptional.get();
            timekeepingRecord.setDeletedAt(sysTimestamp);
            timekeepingRecord.setDeletedBy(userRepo.findById(adminId).get());
            timekeepingRecord.setStatus(StatusType.DELETE);
            timekeepingRecord = timekeepingRecordRepo.save(timekeepingRecord);
            return new TimekeepingRecordModel(timekeepingRecord);
        }
        return null;
    }

    public TimekeepingRecord setTimekeeping(TimekeepingRecord timekeepingRecord, TimekeepingAdminFilter filter) {
        timekeepingRecord.setCheckInTime(filter.getCheckinTime());
        if (filter.getCheckinWifiId() != null) {
            timekeepingRecord.setCheckInWiFiConfig(checkInWiFiConfigRepo.findById(filter.getCheckinWifiId()).get());
        }
        timekeepingRecord.setCheckInNote(filter.getCheckinNote());
        timekeepingRecord.setCheckInApprovalStatus(ApprovalStatusType.APPROVED);
        timekeepingRecord.setCheckInSubmitType(TimekeepingRecordSubmitType.WEB_ADMIN);
        if (filter.getCheckoutTime() != null) {
            timekeepingRecord.setCheckOutTime(filter.getCheckoutTime());
            if (filter.getCheckoutWifiId() != null) {
                timekeepingRecord.setCheckOutWiFiConfig(checkInWiFiConfigRepo.findById(filter.getCheckoutWifiId()).get());
            }
            timekeepingRecord.setCheckOutNote(filter.getCheckoutNote());
            timekeepingRecord.setCheckOutApprovalStatus(ApprovalStatusType.APPROVED);
            timekeepingRecord.setCheckOutSubmitType(TimekeepingRecordSubmitType.WEB_ADMIN);
        }
        timekeepingRecord = timekeepingRecordRepo.save(timekeepingRecord);
        return timekeepingRecord;
    }

    public TimekeepingHistoryModel getTimekeepingByDay(String day, User user, String platform) {
        initialize();
        String keyBeforeCheckIn1 = "minutes_before_working_time_able_to_check_in_1";
        String keyBeforeCheckIn2 = "minutes_before_working_time_able_to_check_in_2";
        String keyAfterCheckOut1 = "minutes_after_working_time_able_to_check_out_1";
        String keyAfterCheckOut2 = "minutes_after_working_time_able_to_check_out_2";
        String keyAfterCheckIn1 = "minutes_after_working_time_able_to_check_in_1";
        String keyAfterCheckIn2 = "minutes_after_working_time_able_to_check_in_2";
        String keyBeforeCheckOut1 = "minutes_before_working_time_able_to_check_out_1";
        String keyBeforeCheckOut2 = "minutes_before_working_time_able_to_check_out_2";

        long minuteBeforeCheckIn1 = 0;
        long minuteBeforeCheckIn2 = 0;
        long minuteAfterCheckOut1 = 0;
        long minuteAfterCheckOut2 = 0;
        long minuteAfterCheckIn1 = 0;
        long minuteAfterCheckIn2 = 0;
        long minuteBeforeCheckOut1 = 0;
        long minuteBeforeCheckOut2 = 0;
        UserBranch userBranch = userBranchRepo.getUserBranchByUserId(user.getId(), StatusType.ACTIVE.getValue());
        List<AppConfig> minutes;
        if (userBranch != null) {
            minutes = appConfigRepo.getConfigTimeAbleTimekeeping(
                    keyBeforeCheckIn1, keyBeforeCheckIn2, keyAfterCheckOut1, keyAfterCheckOut2,
                    keyAfterCheckIn1, keyAfterCheckIn2, keyBeforeCheckOut1, keyBeforeCheckOut2,
                    user.getCompany().getId(), userBranch.getBranch().getId());
        } else {
            minutes = appConfigRepo.getConfigTimeAbleTimekeeping(
                    keyBeforeCheckIn1, keyBeforeCheckIn2, keyAfterCheckOut1, keyAfterCheckOut2,
                    keyAfterCheckIn1, keyAfterCheckIn2, keyBeforeCheckOut1, keyBeforeCheckOut2,
                    user.getCompany().getId());
        }
        if (minutes != null && minutes.size() > 0) {
            for (AppConfig minute : minutes) {
                if (minute.getName().equals(keyBeforeCheckIn1)) {
                    minuteBeforeCheckIn1 = minute.getNumericValue() != null ? (long) (minute.getNumericValue() * 1000 * 60) : 0;
                } else if (minute.getName().equals(keyBeforeCheckIn2)) {
                    minuteBeforeCheckIn2 = minute.getNumericValue() != null ? (long) (minute.getNumericValue() * 1000 * 60) : 0;
                } else if (minute.getName().equals(keyAfterCheckOut1)) {
                    minuteAfterCheckOut1 = minute.getNumericValue() != null ? (long) (minute.getNumericValue() * 1000 * 60) : 0;
                } else if (minute.getName().equals(keyAfterCheckOut2)) {
                    minuteAfterCheckOut2 = minute.getNumericValue() != null ? (long) (minute.getNumericValue() * 1000 * 60) : 0;
                } else if (minute.getName().equals(keyAfterCheckIn1)) {
                    minuteAfterCheckIn1 = minute.getNumericValue() != null ? (long) (minute.getNumericValue() * 1000 * 60) : 0;
                } else if (minute.getName().equals(keyAfterCheckIn2)) {
                    minuteAfterCheckIn2 = minute.getNumericValue() != null ? (long) (minute.getNumericValue() * 1000 * 60) : 0;
                } else if (minute.getName().equals(keyBeforeCheckOut1)) {
                    minuteBeforeCheckOut1 = minute.getNumericValue() != null ? (long) (minute.getNumericValue() * 1000 * 60) : 0;
                } else if (minute.getName().equals(keyBeforeCheckOut2)) {
                    minuteBeforeCheckOut2 = minute.getNumericValue() != null ? (long) (minute.getNumericValue() * 1000 * 60) : 0;
                }
            }
        }

        String toDay = DateUtility.formatDateWithTimeZone(sysdate, DateUtility.FORMAT_DATE_SQL, "Asia/Saigon");
        List<TimekeepingRecord> timekeepingRecords
                = timekeepingRecordRepo.getTimekeepingHistory(day, user.getId());
        TimekeepingHistoryModel timekeepingHistoryModel = new TimekeepingHistoryModel();
        if (timekeepingRecords != null && timekeepingRecords.size() > 0) {
            WorkingTimeConfigModel workingTimeConfig = this.getWorkingTime(user.getId(), day, platform);
            if (workingTimeConfig != null) {
                Long start1 = this.formatWorkingTime(workingTimeConfig.getStartWorkingTime1(), day);
                Long end1 = this.formatWorkingTime(workingTimeConfig.getEndWorkingTime1(), day);
                Long start2 = this.formatWorkingTime(
                        workingTimeConfig.getStartWorkingTime2() != null
                                ? workingTimeConfig.getStartWorkingTime2()
                                : workingTimeConfig.getStartWorkingTime1(), day);
                Long end2 = this.formatWorkingTime(workingTimeConfig.getEndWorkingTime2() != null
                        ? workingTimeConfig.getEndWorkingTime2()
                        : workingTimeConfig.getEndWorkingTime1(), day);

                long realWorkingHours = 0;
                long deficientWorkingHours = 0;
                long planWorkingHours = 0;
                long lateWorkingHours = 0;

                int sizeTimekeeping1 = 0;
                int sizeTimekeeping2 = 0;

                for (TimekeepingRecord timekeepingRecord : timekeepingRecords) {
                    Long checkInTime = this.formatWorkingTime(timekeepingRecord.getCheckInTime(), day);
                    Long checkOutTime;
                    if (timekeepingRecord.getCheckOutTime() != null) {
                        checkOutTime = this.formatWorkingTime(timekeepingRecord.getCheckOutTime(), day);
                    } else {
                        checkOutTime = sysTimestamp.getTime() + 7 * 1000 * 60 * 60;
                    }
                    if (timekeepingRecord.getCheckInApprovalStatus() == ApprovalStatusType.APPROVED
                            && checkOutTime - checkInTime > 0) {
                        if (checkInTime < end1 && checkOutTime > start1) {
                            if (sizeTimekeeping1 == 0 && checkInTime > start1 + minuteAfterCheckIn1) {
                                lateWorkingHours += checkInTime - start1;
                            }
                            sizeTimekeeping1++;
                        } else if (checkInTime < end2 && checkOutTime > start2) {
                            if (sizeTimekeeping2 == 0 && checkInTime > start2 + minuteAfterCheckIn2) {
                                lateWorkingHours += checkInTime - start2;
                            }
                            sizeTimekeeping2++;
                        }
                    }
                }
                if (!toDay.equals(day)) {
                    TimekeepingStatistic timekeepingStatistic
                            = timekeepingStatisticRepo.getTimekeepingStatistic(user.getId(), day);
                    if (timekeepingStatistic != null) {
                        realWorkingHours = (long) (timekeepingStatistic.getTotalRealWorkingHours() * 1000 * 60 * 60);
                        deficientWorkingHours = (long) (timekeepingStatistic.getTotalDeficientWorkingHours() * 1000 * 60 * 60);
                        planWorkingHours = (long) (timekeepingStatistic.getTotalPlanWorkingHours() * 1000 * 60 * 60);
                        timekeepingHistoryModel.setDeficientWorkingHours(deficientWorkingHours);
                        timekeepingHistoryModel.setRealWorkingHours(realWorkingHours);
                        timekeepingHistoryModel.setPlanWorkingHours(planWorkingHours);
                        timekeepingHistoryModel.setLateWorkingHours(lateWorkingHours);
                    }
                } else {
                    long totalPlanWorkingHours = (long) (workingTimeConfig.getNumWorkingHours() * 1000 * 60 * 60);
                    boolean hasWorking1 = false;
                    boolean hasWorking2 = false;
                    int index1 = 0;
                    int index2 = 0;
                    for (TimekeepingRecord timekeepingRecord : timekeepingRecords) {
                        Long checkInTime = this.formatWorkingTime(timekeepingRecord.getCheckInTime(), day);
                        Long checkOutTime;
                        if (timekeepingRecord.getCheckOutTime() != null) {
                            checkOutTime = this.formatWorkingTime(timekeepingRecord.getCheckOutTime(), day);
                        } else {
                            checkOutTime = sysTimestamp.getTime() + 7 * 1000 * 60 * 60;
                        }
                        long checkInTimestamp = 0;
                        long checkOutTimestamp = 0;
                        if (checkOutTime - checkInTime > 0) {
                            if (timekeepingRecord.getCheckOutApprovalStatus() == ApprovalStatusType.APPROVED
                                    && timekeepingRecord.getCheckInApprovalStatus() == ApprovalStatusType.APPROVED) {
                                checkOutTimestamp = this.getCheckOutTimestampApproved(
                                        start1, end1, start2, end2,
                                        checkInTime, checkOutTime,
                                        minuteBeforeCheckOut1, minuteBeforeCheckOut2, minuteBeforeCheckIn2,
                                        index1, index2, sizeTimekeeping1, sizeTimekeeping2);
                                checkInTimestamp = this.getCheckInTimestampApproved(start1, end1, start2, end2, checkInTime,
                                        checkOutTime, minuteAfterCheckIn1, minuteAfterCheckIn2, index1, index2);
                            } else if (timekeepingRecord.getCheckOutApprovalStatus() == ApprovalStatusType.WAITING_FOR_APPROVAL
                                    || timekeepingRecord.getCheckInApprovalStatus() == ApprovalStatusType.WAITING_FOR_APPROVAL) {
                                checkOutTimestamp = 0;
                                checkInTimestamp = 0;
                            } else if (timekeepingRecord.getCheckOutApprovalStatus() == null
                                    && timekeepingRecord.getCheckInApprovalStatus() == ApprovalStatusType.APPROVED) {
                                checkOutTimestamp = this.getCheckOutTimestampNull(start1, end1, start2, end2,
                                        checkInTime, checkOutTime,
                                        minuteAfterCheckOut1, minuteAfterCheckOut2,
                                        minuteAfterCheckIn1, minuteAfterCheckIn2,
                                        minuteBeforeCheckOut1, minuteBeforeCheckOut2, minuteBeforeCheckIn2,
                                        index1, index2, sizeTimekeeping1, sizeTimekeeping2);
                                checkInTimestamp = this.getCheckInTimestampApproved(start1, end1, start2, end2, checkInTime,
                                        checkOutTime, minuteAfterCheckIn1, minuteAfterCheckIn2, index1, index2);
                            }
                            realWorkingHours = realWorkingHours + (Math.abs(checkOutTimestamp - checkInTimestamp));
                            if (timekeepingRecord.getCheckInApprovalStatus() == ApprovalStatusType.APPROVED
                                    && checkOutTime - checkInTime > 0) {
                                if (checkInTime < end1 && checkOutTime > start1) {
                                    index1++;
                                    hasWorking1 = true;
                                } else if (checkInTime < end2 && checkOutTime > start2) {
                                    index2++;
                                    hasWorking2 = true;
                                }
                            }
                        }
                    }
                    if (hasWorking1 && hasWorking2) {
                        planWorkingHours = totalPlanWorkingHours;
                    } else if (hasWorking1 && !hasWorking2) {
                        planWorkingHours = end1 - start1;
                    } else if (!hasWorking1 && hasWorking2) {
                        planWorkingHours = end2 - start2;
                    }
                    if (realWorkingHours > 0) {
                        deficientWorkingHours = (planWorkingHours) - realWorkingHours;
                    }
                    timekeepingHistoryModel.setDeficientWorkingHours(deficientWorkingHours);
                    timekeepingHistoryModel.setRealWorkingHours(realWorkingHours);
                    timekeepingHistoryModel.setPlanWorkingHours(totalPlanWorkingHours);
                    timekeepingHistoryModel.setLateWorkingHours(lateWorkingHours);
                }
                int size = timekeepingRecords.size();
                for (int i = 0; i < size; i++) {
                    TimekeepingRecord item = timekeepingRecords.get(i);
                    if (i == 0) {
                        timekeepingHistoryModel.setCreatedAt(item.getCreatedAt());
                        timekeepingHistoryModel.setCheckInTime(item.getCheckInTime());
                    }
                    if (i == size - 1) {
                        timekeepingHistoryModel.setCheckOutTime(item.getCheckOutTime());
                    }
                }
                return timekeepingHistoryModel;
            }
        }
        return null;
    }

    private WorkingTimeConfigModel getWorkingTime(Long userId, String day, String platform) {
        Optional<User> userOptional = userRepo.findById(userId);
        WorkingTimeConfigModel workingTimeConfigModel = null;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            WorkingTimeConfig workingTime = workingTimeConfigRepo
                    .getWorkingTimeConfig(user.getId(), day, user.getCompany().getId(), false);
            if (workingTime != null) {
                workingTimeConfigModel = setWorkingConfig(user, day, platform, workingTime);
            }
        }
        return workingTimeConfigModel;
    }

    private WorkingTimeConfigModel setWorkingConfig(User user, String day,
                                                    String platform, WorkingTimeConfig workingTimeConfig) {
        WorkingTimeConfigModel workingTimeConfigModel;
        WorkingTimeConfig workingTimeNext = workingTimeConfigRepo
                .getWorkingTimeConfig(user.getId(), day, user.getCompany().getId(), true);
        workingTimeConfigModel = new WorkingTimeConfigModel(workingTimeConfig);
        workingTimeConfigModel.setDay(day);
        if (workingTimeNext != null && workingTimeNext.getId() != workingTimeConfig.getId()) {
            WorkingTimeConfigModel nextDayModel
                    = this.getNextDayConfig(workingTimeNext, day, platform);
            workingTimeConfigModel.setNextDay(nextDayModel);
        }
        return workingTimeConfigModel;
    }

    private WorkingTimeConfigModel getNextDayConfig(WorkingTimeConfig workingTimeConfig, String nextDay, String platform) {
        WorkingTimeConfigModel model = new WorkingTimeConfigModel(workingTimeConfig);
        if (StringUtility.isNotEmpty(platform) && platform.equals("android")) {
            model.setDay(nextDay);
        } else {
            model.setDay("");
            model.setStartWorkingTime1("");
            model.setStartWorkingTime2("");
            model.setEndWorkingTime1("");
            model.setEndWorkingTime2("");
        }
        return model;
    }

    private Long formatWorkingTime(String workingTime, String day) {
        Timestamp timestamp = DateUtility.convertStringToTimestamp(
                day + " " + DateUtility.convertDateWithFormat(
                        workingTime,
                        DateUtility.FORMAT_TIME_SECONDS,
                        DateUtility.FORMAT_TIME_SECOND));
        return timestamp.getTime();
    }

    /**
     * Get check int timestamp approved
     */
    private Long getCheckInTimestampApproved(Long start1, Long end1, Long start2, Long end2,
                                             Long checkInTimestamp, Long checkOutTimestamp,
                                             Long minuteAfterCheckIn1, Long minuteAfterCheckIn2, int index1, int index2) {
        if (checkInTimestamp < start1) {
            return start1;
        } else if ((checkInTimestamp >= start1 && checkInTimestamp < end1)) {
            if (checkInTimestamp - minuteAfterCheckIn1 <= start1) {
                return index1 == 0 ? start1 : checkInTimestamp;
            } else {
                return checkInTimestamp;
            }
        } else if ((checkInTimestamp >= start2 && checkInTimestamp < end2)) {
            if (checkInTimestamp - minuteAfterCheckIn2 <= start2) {
                return index2 == 0 ? start2 : checkInTimestamp;
            } else {
                return checkInTimestamp;
            }
        } else if (checkInTimestamp >= end1 && checkInTimestamp < start2) {
            return start2;
        } else {
            return checkOutTimestamp;
        }
    }

    /**
     * Get check out timestamp approved
     */
    private Long getCheckOutTimestampApproved(Long start1, Long end1, Long start2, Long end2,
                                              Long checkInTimestamp, Long checkOutTimestamp,
                                              Long minuteBeforeCheckOut1, Long minuteBeforeCheckOut2,
                                              Long minuteBeforeCheckIn2, int index1, int index2,
                                              int sizeTimekeeping1, int sizeTimekeeping2) {
        if (checkOutTimestamp >= end1 && checkInTimestamp < end1) {
            return end1;
        } else if (checkOutTimestamp >= end2 && checkInTimestamp < end2
                && checkInTimestamp >= (start2 - minuteBeforeCheckIn2)) {
            return end2;
        } else if (checkOutTimestamp <= start1) {
            return start1;
        } else if (checkOutTimestamp >= end1 && checkOutTimestamp <= start2) {
            return start2;
        } else {
            if (checkOutTimestamp < end1 && checkOutTimestamp + minuteBeforeCheckOut1 >= end1) {
                return index1 == sizeTimekeeping1 - 1 ? end1 : checkOutTimestamp;
            } else if (checkOutTimestamp < end2 && checkInTimestamp >= (start2 - minuteBeforeCheckIn2)
                    && checkOutTimestamp + minuteBeforeCheckOut2 >= end2) {
                return index2 == sizeTimekeeping2 - 1 ? end2 : checkOutTimestamp;
            } else {
                return checkOutTimestamp;
            }
        }
    }

    /**
     * Get check out timestamp waiting for approval
     */
    private Long getCheckOutTimestampNull(Long start1, Long end1, Long start2, Long end2,
                                          Long checkInTimestamp, Long checkOutTimestamp,
                                          Long minuteAfterCheckOut1, Long minuteAfterCheckOut2,
                                          Long minuteAfterCheckIn1, Long minuteAfterCheckIn2,
                                          Long minuteBeforeCheckOut1, Long minuteBeforeCheckOut2,
                                          Long minuteBeforeCheckIn2, int index1, int index2,
                                          int sizeTimekeeping1, int sizeTimekeeping2) {
        long _59s = 59000;
        if (checkOutTimestamp > (end1 + minuteAfterCheckOut1 + _59s) && checkInTimestamp < end1) {
            return this.getCheckInTimestampApproved(start1, end1, start2, end2, checkInTimestamp,
                    checkOutTimestamp, minuteAfterCheckIn1, minuteAfterCheckIn2, index1, index2);
        } else if (checkOutTimestamp > (end2 + minuteAfterCheckOut2 + _59s) && checkInTimestamp < end2 && checkInTimestamp >= start2) {
            return this.getCheckInTimestampApproved(start1, end1, start2, end2, checkInTimestamp,
                    checkOutTimestamp, minuteAfterCheckIn1, minuteAfterCheckIn2, index1, index2);
        } else {
            return this.getCheckOutTimestampApproved(start1, end1, start2, end2, checkInTimestamp, checkOutTimestamp,
                    minuteBeforeCheckOut1, minuteBeforeCheckOut2, minuteBeforeCheckIn2,
                    index1, index2, sizeTimekeeping1, sizeTimekeeping2);
        }
    }

    /**
     * Get wifi
     *
     * @param checkInWiFiConfigs
     * @return
     */
    private List<WiFiConfigModel> getWifi(List<CheckInWiFiConfig> checkInWiFiConfigs) {
        List<WiFiConfigModel> wiFiConfigs = new ArrayList<>();
        if (checkInWiFiConfigs.size() > 0) {
            for (CheckInWiFiConfig wiFiConfig : checkInWiFiConfigs) {
                WiFiConfigModel wiFiConfigModel = new WiFiConfigModel();
                wiFiConfigModel.setId(wiFiConfig.getId());
                wiFiConfigModel.setWiFiName(wiFiConfig.getWifiName());
                wiFiConfigModel.setModemMacAddress(wiFiConfig.getModemMacAddress());
                wiFiConfigModel.setGpsLatitude(wiFiConfig.getGpsLatitude());
                wiFiConfigModel.setGpsLongitude(wiFiConfig.getGpsLongitude());
                wiFiConfigs.add(wiFiConfigModel);
            }
        }
        return wiFiConfigs;
    }
}
