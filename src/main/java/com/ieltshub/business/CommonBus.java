package com.ieltshub.business;

import com.ieltshub.exception.BusinessException;
import com.ieltshub.viewmodel.cache.UserInfo;
import com.ieltshub.viewmodel.common.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author tuannd
 * @date 10/03/2016
 * @since 1.0
 */
public interface CommonBus {

    /**
     * Get update version
     *
     * @param platform
     * @return
     * @throws BusinessException
     */
    UpdateVersionModel getUpdateVersion(String platform, String buildId) throws BusinessException;

    /**
     * Save exception
     *
     * @param filter
     * @return
     */
    Boolean saveException(ExceptionFilter filter, UserInfo userInfo, String platform) throws BusinessException;

    /**
     * Get time today
     *
     * @throws BusinessException
     */
    Timestamp getTimeToday() throws BusinessException;
}
