package com.ieltshub.business;

import com.ieltshub.common.ErrorCode;
import com.ieltshub.entity.UserLoginLog;
import com.ieltshub.exception.BusinessException;
import com.ieltshub.viewmodel.cache.UserInfo;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.timekeeping.*;
import com.ieltshub.viewmodel.timekeeping.history.TimekeepingHistoryDetailModel;
import com.ieltshub.viewmodel.timekeeping.history.TimekeepingHistoryFilter;
import com.ieltshub.viewmodel.timekeeping.history.TimekeepingHistoryModel;
import com.ieltshub.viewmodel.timekeeping.wifi.WiFiConfigFilter;
import com.ieltshub.viewmodel.timekeeping.wifi.WiFiConfigModel;
import com.ieltshub.viewmodel.timekeeping.wokingTime.WorkingTimeConfigModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TimekeepingBus {

    /**
     * @param userId
     * @return
     * @throws BusinessException
     */
    String getPhoneByUserId(Long userId) throws BusinessException;

    /**
     * Get timekeeping checkIn
     *
     * @param filter
     * @param userInfo
     * @return
     * @throws BusinessException
     */
    ErrorCode getTimekeepingCheckIn(TimekeepingFilter filter, UserInfo userInfo) throws BusinessException;

    /**
     * Get user device login
     *
     * @throws BusinessException
     */
    UserLoginLog getUserDeviceLogin(Long userId, String deviceId) throws BusinessException;

    /**
     * Get wi-fi config
     *
     * @throws BusinessException
     */
    List<WiFiConfigModel> getWiFiConfig(Long userId) throws BusinessException;

    /**
     * Get wi-fi config
     *
     * @throws BusinessException
     */
    List<WiFiConfigModel> getWiFiConfigAdmin(WiFiConfigFilter filter) throws BusinessException;

    /**
     * Get working time config
     *
     * @throws BusinessException
     */
    WorkingTimeConfigModel getWorkingTimeConfig(Long userId, String platform) throws BusinessException;

    /**
     * Timekeeping check
     *
     * @param filter
     * @throws BusinessException
     */
    TimekeepingRecordModel timekeeping(TimekeepingFilter filter, UserInfo userInfo, String platform) throws BusinessException;

    /**
     * Get timekeeping
     *
     * @throws BusinessException
     */
    TimekeepingModel getTimekeeping(Long userId) throws BusinessException;

    /**
     * Get timekeeping list
     *
     * @throws BusinessException
     */
    PaginationResult<TimekeepingListModel> getTimekeepingList(TimekeepingListFilter filter, String platform) throws BusinessException;

    /**
     * Get timekeeping history
     *
     * @param filter
     * @throws BusinessException
     */
    List<TimekeepingHistoryModel> getTimekeepingHistory(TimekeepingHistoryFilter filter, Long userId, String platform)
            throws BusinessException;

    /**
     * Get timekeeping history detail
     *
     * @param filter
     * @throws BusinessException
     */
    TimekeepingHistoryDetailModel getTimekeepingHistoryDetail(TimekeepingHistoryFilter filter, Long userId, String platform)
            throws BusinessException;

    /**
     * Approval timekeeping
     *
     * @param filter
     * @throws BusinessException
     */
    TimekeepingRecordModel approvalTimekeeping(ApprovalTimekeepingFilter filter, Long userId, Long timekeepingId)
            throws BusinessException;

    /**
     * Upload image path
     *
     * @param file
     * @param userId
     * @return
     */
    String uploadImagePath(MultipartFile file, Long userId);

    /**
     * Timekeeping admin check
     *
     * @param filter
     * @throws BusinessException
     */
    TimekeepingRecordModel timekeepingAdmin(TimekeepingAdminFilter filter, Long adminId) throws BusinessException;

    /**
     * Timekeeping admin check
     *
     * @param filter
     * @throws BusinessException
     */
    TimekeepingRecordModel timekeepingUpdate(Long timekeepingId, TimekeepingAdminFilter filter, Long adminId)
            throws BusinessException;

    /**
     * Timekeeping delete
     *
     * @param timekeepingId
     * @throws BusinessException
     */
    TimekeepingRecordModel timekeepingDelete(Long timekeepingId, Long adminId) throws BusinessException;
}
