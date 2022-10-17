package com.evowork.repository;

import com.evowork.entity.Branch;
import com.evowork.enumeration.StatusType;
import com.evowork.utility.StringUtility;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.company.CompanyFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchRepositoryImpl extends AbstractRepository implements BranchRepositoryExtend {

    @Override
    public PaginationResult<Branch> getBranches(Long userId, Long companyId, CompanyFilter filter) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT * ");
        sql.append("FROM   branch ");
        sql.append("WHERE  1 = 1 ");
        sql.append("AND    status = :status ");
        sql.append("AND    company_id = :companyId ");
        if (filter.getStringSearch() != null) {
            sql.append("AND search_text like :paramSearch ");
            parameters.put("paramSearch", StringUtility.convertUppercaseToLowercase(
                    StringUtility.toFullSearchLike(StringUtility.removeAccent(filter.getStringSearch()))));
        }
        sql.append("ORDER BY name asc ");
        parameters.put("status", StatusType.ACTIVE.getValue());
        parameters.put("companyId", companyId);
        sql.append(getPagingSql(filter.getPaging()));
        PaginationResult<Branch> result = new PaginationResult<>(filter.getPaging());
        List<Branch> data = repo.getEntities(Branch.class, sql.toString(), parameters);
        result.setData(data);
        return result;
    }
}
