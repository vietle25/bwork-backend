package com.evowork.repository;

import com.evowork.entity.SalaryDetail;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.common.Paging;
import com.evowork.viewmodel.salary.SalaryFilter;

/**
 * NotificationSchedule repository
 *
 * @author tuanlt
 * @date 15/10/2017
 * @since 1.0
 */
public interface SalaryDetailRepositoryExtend {

    /**
     * Get type amount
     *
     * @param userId
     * @param salaryId
     * @param type
     * @param paging
     * @return
     */
    PaginationResult<SalaryDetail> getTypeAmount(Long userId, Long salaryId, Integer type, Paging paging);

    /**
     *
     * @param userId
     * @param filter
     * @return
     */
    PaginationResult<SalaryDetail> getStaffSalary(Long userId, SalaryFilter filter);
}
