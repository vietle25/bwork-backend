package com.ieltshub.repository;

import com.ieltshub.entity.WorkingTimeConfig;
import com.ieltshub.enumeration.StatusType;
import com.ieltshub.enumeration.WorkingTimeType;

import java.util.HashMap;
import java.util.Map;

public class WorkingTimeConfigRepositoryImpl extends AbstractRepository implements WorkingTimeConfigRepositoryExtend {
    @Override
    public WorkingTimeConfig getWorkingTimeConfig(Long userId, String toDay, Long companyId, Boolean isFuture) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT * FROM working_time_config ");
        sql.append("where status = :status ");
        sql.append("and ((type = :typeUser ");
        sql.append("     and user_id = :userId) ");
        sql.append("    or (type = :typeGeneral)) ");
        if (isFuture) {
            sql.append("and valid_from >= :toDay ");
        } else {
            sql.append("and valid_from <= :toDay ");
        }
        sql.append("and company_id = :companyId ");
        sql.append("order by type desc, valid_from desc, shift_type asc limit 1");
        parameters.put("userId", userId);
        parameters.put("typeUser", WorkingTimeType.SPECIFIC_WORKING_TIME_FOR_USER.getValue());
        parameters.put("typeGeneral", WorkingTimeType.GENERAL_WORKING_TIME.getValue());
        parameters.put("toDay", toDay);
        parameters.put("status", StatusType.ACTIVE.getValue());
        parameters.put("companyId", companyId);
        WorkingTimeConfig data = repo.getEntity(WorkingTimeConfig.class, sql.toString(), parameters);
        return data;
    }
}
