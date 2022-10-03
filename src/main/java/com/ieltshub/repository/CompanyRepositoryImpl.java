package com.ieltshub.repository;

import com.ieltshub.entity.Company;
import com.ieltshub.enumeration.StatusType;
import com.ieltshub.utility.StringUtility;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.common.Paging;
import com.ieltshub.viewmodel.company.CompanyFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyRepositoryImpl extends AbstractRepository implements CompanyRepositoryExtend {

    @Override
    public PaginationResult<Company> getCompanies(Long userId, CompanyFilter filter) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT * ");
        sql.append("FROM   company ");
        sql.append("WHERE  1 = 1 ");
        sql.append("AND    status = :status ");
        sql.append("AND    id != 1 ");
        if (filter.getStringSearch() != null) {
            sql.append("AND search_text like :paramSearch ");
            parameters.put("paramSearch", StringUtility.convertUppercaseToLowercase(
                    StringUtility.toFullSearchLike(StringUtility.removeAccent(filter.getStringSearch()))));
        }
        sql.append("ORDER BY name asc ");
        parameters.put("status", StatusType.ACTIVE.getValue());
        sql.append(getPagingSql(filter.getPaging()));
        PaginationResult<Company> result = new PaginationResult<>(filter.getPaging());
        List<Company> data = repo.getEntities(Company.class, sql.toString(), parameters);
        result.setData(data);
        return result;
    }
}
