package com.evowork.repository;

import com.evowork.entity.SalaryDetail;
import com.evowork.enumeration.SalaryType;
import com.evowork.enumeration.StatusType;
import com.evowork.utility.StringUtility;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.common.Paging;
import com.evowork.viewmodel.salary.SalaryFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NotificationSchedule repository
 *
 * @author tuannd
 * @date 15/10/2016
 * @since 1.0
 */
public class SalaryDetailRepositoryImpl extends AbstractRepository implements SalaryDetailRepositoryExtend {

    @Override
    public PaginationResult<SalaryDetail> getTypeAmount(Long userId, Long salaryId, Integer type, Paging paging) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT * ");
        sql.append("FROM   salary_detail ");
        sql.append("WHERE  user_id = :userId ");
        sql.append("AND  salary_id = :salaryId ");
        sql.append("AND  type = :type ");
        sql.append("AND  status = :status ");
        sql.append("ORDER BY created_at desc ");
        parameters.put("userId", userId);
        parameters.put("salaryId", salaryId);
        parameters.put("type", type);
        parameters.put("status", StatusType.ACTIVE.getValue());
        sql.append(getPagingSql(paging));
        PaginationResult<SalaryDetail> result = new PaginationResult<>(paging);
        List<SalaryDetail> data = repo.getEntities(SalaryDetail.class, sql.toString(), parameters);
        result.setData(data);
        return result;
    }

    @Override
    public PaginationResult<SalaryDetail> getStaffSalary(Long userId, SalaryFilter filter) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT sd.* ");
        sql.append("FROM users u ");
        if(filter.getBranchId() != null) {
            sql.append("    JOIN user_branch ub ");
            sql.append("        ON u.id = ub.user_id ");
            sql.append("               AND ub.status = :status ");
            sql.append("               AND ub.branch_id = :branchId ");
            parameters.put("branchId", filter.getBranchId());
        }
        sql.append("    JOIN salary s ");
        sql.append("        ON u.id = s.user_id ");
        sql.append("            AND u.status = :status ");
        if(filter.getCompanyId() != null) {
            sql.append("            AND u.company_id = :companyId ");
            parameters.put("companyId", filter.getCompanyId());
        }
        if(filter.getMonth() != null) {
            sql.append("            AND s.period LIKE :period ");
            parameters.put("period", filter.getMonth());
        }
        sql.append("    JOIN salary_detail sd ");
        sql.append("         ON s.id = sd.salary_id ");
        sql.append("             AND sd.status = :status ");
        sql.append("             AND sd.type = :type ");
        parameters.put("type", SalaryType.TEMPORARY_SALARY.getValue());
        sql.append(" WHERE EXISTS(SELECT 1 FROM company c WHERE c.id = u.company_id AND c.status = :status) ");
        if(filter.getStringSearch() != null) {
            sql.append("         AND concat_ws(' ', u.first_name_search, u.last_name_search ) like :stringSearch ");
            parameters.put("stringSearch", StringUtility.convertUppercaseToLowercase(StringUtility.toFullSearchLike(StringUtility.removeAccent(filter.getStringSearch()))));
        }
        sql.append(" ORDER BY u.last_name ASC ");
        parameters.put("status", StatusType.ACTIVE.getValue());
        sql.append(getPagingSql(filter.getPaging()));
        PaginationResult<SalaryDetail> result = new PaginationResult<>(filter.getPaging());
        List<SalaryDetail> data = repo.getEntities(SalaryDetail.class, sql.toString(), parameters);
        result.setData(data);
        return result;
    }

}
