package com.ieltshub.business;

import com.ieltshub.exception.BusinessException;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.company.DashboardFilter;
import com.ieltshub.viewmodel.sabbatical.RegisterSabbaticalFilter;
import com.ieltshub.viewmodel.sabbatical.SabbaticalFilter;
import com.ieltshub.viewmodel.sabbatical.SabbaticalModel;

public interface SabbaticalBus {

    /**
     * @param userId
     * @param filter
     * @return
     * @throws BusinessException
     */
    PaginationResult<SabbaticalModel> getSabbaticals(Long userId, SabbaticalFilter filter) throws BusinessException;

    /**
     *
     * @param userId
     * @param filter
     * @return
     * @throws BusinessException
     */
    PaginationResult<SabbaticalModel> getSabbaticalsAdmin(Long userId, SabbaticalFilter filter) throws BusinessException;

    /**
     * Has sabbatical
     *
     * @param userId
     * @param filter
     * @return
     * @throws BusinessException
     */
    Boolean hasSabbatical(Long userId, RegisterSabbaticalFilter filter) throws BusinessException;

    /**
     * @param userId
     * @param filter
     * @return
     * @throws BusinessException
     */
    SabbaticalModel registerSabbatical(Long userId, RegisterSabbaticalFilter filter, String platform) throws BusinessException;

    /**
     * @param userId
     * @param filter
     * @param sabbaticalId
     * @return
     * @throws BusinessException
     */
    SabbaticalModel updateSabbatical(Long userId, Long sabbaticalId, RegisterSabbaticalFilter filter, String platform)
            throws BusinessException;

    /**
     * @param sabbaticalId
     * @return
     * @throws BusinessException
     */
    Boolean deleteSabbatical(Long sabbaticalId, Long userId) throws BusinessException;

    /**
     *
     * @param sabbaticalId
     * @param userId
     * @return
     * @throws BusinessException
     */
    Boolean approvedSabbatical(Long sabbaticalId, Long userId) throws BusinessException;

    /**
     *
     * @param sabbaticalId
     * @param userId
     * @param deniedNote
     * @return
     * @throws BusinessException
     */
    Boolean deniedSabbatical(Long sabbaticalId, Long userId, String deniedNote) throws BusinessException;
}
