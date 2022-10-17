package com.evowork.business;

import com.evowork.exception.BusinessException;
import com.evowork.viewmodel.cache.UserInfo;
import com.evowork.viewmodel.company.DashboardFilter;
import com.evowork.viewmodel.company.DashboardModel;

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
