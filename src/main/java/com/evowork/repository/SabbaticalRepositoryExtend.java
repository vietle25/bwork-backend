package com.evowork.repository;

import com.evowork.entity.Sabbatical;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.company.DashboardFilter;
import com.evowork.viewmodel.sabbatical.SabbaticalFilter;

import java.sql.Date;

public interface SabbaticalRepositoryExtend {

    /**
     * Get sabbaticals
     *
     * @param userId
     * @return
     */
    PaginationResult<Sabbatical> getSabbaticals(Long userId, SabbaticalFilter filter);

    /**
     *
     * @param userId
     * @param filter
     * @return
     */
    PaginationResult<Sabbatical> getSabbaticalsAdmin(Long userId, SabbaticalFilter filter);

    /**
     * @param userId
     * @param status
     * @return
     */
    Boolean countSabbaticalExist(Long userId, Integer status, Integer approvalStatus,
                                 Date offFromDate, Date offToDate, Integer offType);

    /**
     * Get total sabbatical
     *
     * @param filter
     * @return
     */
    Integer getTotalSabbatical(DashboardFilter filter);
}
