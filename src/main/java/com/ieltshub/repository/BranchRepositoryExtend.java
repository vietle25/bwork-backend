package com.ieltshub.repository;

import com.ieltshub.entity.Branch;
import com.ieltshub.entity.Company;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.common.Paging;
import com.ieltshub.viewmodel.company.CompanyFilter;

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
