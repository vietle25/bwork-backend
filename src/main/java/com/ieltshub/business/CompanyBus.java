package com.ieltshub.business;

import com.ieltshub.exception.BusinessException;
import com.ieltshub.viewmodel.cache.UserInfo;
import com.ieltshub.viewmodel.company.DashboardFilter;
import com.ieltshub.viewmodel.company.DashboardModel;

/**
 * @author tuannd
 * @date 10/03/2016
 * @since 1.0
 */
public interface CompanyBus {

    /**
     * Get dashboard statistical
     * @return
     * @throws BusinessException
     * @param filter
     */
    DashboardModel getDashboardStatistical(DashboardFilter filter, UserInfo userInfo, String platform)
            throws BusinessException;
}
