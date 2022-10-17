package com.evowork.repository;

import com.evowork.entity.Company;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.company.CompanyFilter;

public interface CompanyRepositoryExtend {

    /**
     * Get companies
     * @param userId
     * @param filter
     * @return
     */
    PaginationResult<Company> getCompanies(Long userId, CompanyFilter filter);
}
