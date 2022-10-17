package com.evowork.repository;

import com.evowork.entity.TimekeepingRecord;
import com.evowork.entity.User;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.company.DashboardFilter;
import com.evowork.viewmodel.timekeeping.TimekeepingListFilter;

import java.util.List;

/**
 * TimekeepingRecord repository
 *
 * @author tuanlt
 * @date 15/10/2017
 * @since 1.0
 */
public interface TimekeepingRecordRepositoryExtend {

    /**
     * Get timekeeping history
     *
     * @param day
     * @param userId
     * @return
     */
    List<TimekeepingRecord> getTimekeepingHistory(String day, Long userId);

    /**
     * Get total checkin
     *
     * @param filter
     * @return
     */
    Integer getTotalCheckin(DashboardFilter filter);

    /**
     * Get total not checkin
     *
     * @param filter
     * @return
     */
    Integer getTotalNotCheckin(DashboardFilter filter);

    /**
     * Get user timekeeping
     *
     * @param filter
     * @return
     */
    PaginationResult<User> getUserTimekeeping(TimekeepingListFilter filter);
}
