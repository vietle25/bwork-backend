package com.evowork.repository;

import com.evowork.entity.Branch;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.company.CompanyFilter;

public interface BranchRepositoryExtend {

    /**
     * Get branches
     * @param userId
     * @param companyId
     * @param filter
     * @return
     */
    PaginationResult<Branch> getBranches(Long userId, Long companyId, CompanyFilter filter);
}
