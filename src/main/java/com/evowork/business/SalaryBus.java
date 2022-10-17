package com.evowork.business;

import com.evowork.exception.BusinessException;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.salary.SalaryDetailModel;
import com.evowork.viewmodel.salary.SalaryFilter;
import com.evowork.viewmodel.salary.SalaryModel;

/**
 * @author tuannd
 * @date 10/03/2016
 * @since 1.0
 */
public interface SalaryBus {

    /**
     * Get salary by userId
     *
     * @param userId
     * @return
     */
    SalaryModel getSalary(Long userId, SalaryFilter filter, String platform) throws BusinessException;

    /**
     * Get salary config by userId
     *
     * @param filter
     * @return
     */
    SalaryModel getSalaryConfig(SalaryFilter filter) throws BusinessException;

    /**
     * Get type amount
     *
     * @param userId
     * @param filter
     * @return
     * @throws BusinessException
     */
    PaginationResult<SalaryDetailModel> getDetailTypeSalary(Long userId, SalaryFilter filter) throws BusinessException;

    /**
     * Get list staff salary
     * @param userId
     * @param filter
     * @return
     * @throws BusinessException
     */
    PaginationResult<SalaryDetailModel> getStaffSalary(Long userId, SalaryFilter filter) throws BusinessException;
}
