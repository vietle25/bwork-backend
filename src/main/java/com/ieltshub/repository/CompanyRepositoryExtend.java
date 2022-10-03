package com.ieltshub.repository;

import com.ieltshub.entity.Company;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.common.Paging;
import com.ieltshub.viewmodel.company.CompanyFilter;

public interface CompanyRepositoryExtend {

    /**
     * Get companies
     * @param userId
     * @param filter
     * @return
     */
    PaginationResult<Company> getCompanies(Long userId, CompanyFilter filter);
}
