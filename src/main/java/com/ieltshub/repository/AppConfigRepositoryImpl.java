package com.ieltshub.repository;

import com.ieltshub.entity.AppConfig;
import com.ieltshub.enumeration.StatusType;
import com.ieltshub.viewmodel.user.AppConfigFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppConfigRepositoryImpl extends AbstractRepository implements AppConfigRepositoryExtend {

    @Override
    public List<AppConfig> getMobileConfig(AppConfigFilter filter) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("select * ");
        sql.append("from app_config ");
        sql.append("where 1 = 1 ");
        sql.append("and for_mobile = true ");
        sql.append("and version = 0 ");
        sql.append("and company_id = :companyId ");
        if (filter.getBranchId() != null) {
            sql.append("and branch_id = :branchId ");
            parameters.put("branchId", filter.getBranchId());
        } else {
            sql.append("and branch_id is null ");
        }
        parameters.put("companyId", filter.getCompanyId());
        List<AppConfig> data = repo.getEntities(AppConfig.class, sql.toString(), parameters);
        return data;
    }
}
