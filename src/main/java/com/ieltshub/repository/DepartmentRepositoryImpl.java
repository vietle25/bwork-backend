package com.ieltshub.repository;

import com.ieltshub.entity.User;
import com.ieltshub.enumeration.StatusType;
import com.ieltshub.enumeration.UserType;
import com.ieltshub.utility.StringUtility;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.department.DepartmentFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentRepositoryImpl extends AbstractRepository implements DepartmentRepositoryExtend {

    @Override
    public PaginationResult<User> getStaffDepartment(Long userId, DepartmentFilter filter) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT u.* ");
        sql.append("FROM users u ");
        if (filter.getBranchId() != null) {
            sql.append("    JOIN user_branch ub ");
            sql.append("        ON u.id = ub.user_id ");
            sql.append("               AND ub.status = :status ");
            sql.append("               AND ub.branch_id = :branchId ");
            parameters.put("branchId", filter.getBranchId());
        }
        sql.append("    LEFT JOIN department_staff ds ");
        sql.append("        ON u.id = ds.user_id ");
        sql.append("               AND ds.status = :status ");
        sql.append("    LEFT JOIN department d ");
        sql.append("        ON ds.department_id = d.id ");
        sql.append("               AND d.status = :status ");
        sql.append("    LEFT JOIN company c ");
        sql.append("        ON d.company_id = c.id ");
        sql.append("               AND c.status = :status ");
        sql.append("WHERE 1 = 1 ");
        if (filter.getStatus() != null) {
            sql.append("           AND u.status = :statusUser ");
            parameters.put("statusUser", filter.getStatus());
        } else {
            sql.append("           AND u.status in(0,1) ");
        }
        sql.append("               AND u.type != :typeUser ");
        parameters.put("typeUser", UserType.WEB_ADMIN.getValue());
        if (filter.getCompanyId() != null) {
            sql.append("               AND u.company_id = :companyId ");
            parameters.put("companyId", filter.getCompanyId());
        }
        if (filter.getDepartmentId() != null) {
            sql.append("               AND ds.department_id = :departmentId ");
            parameters.put("departmentId", filter.getDepartmentId());
        }
        if (filter.getStringSearch() != null) {
            sql.append("       AND concat_ws(' ', u.first_name_search, u.last_name_search ) like :stringSearch ");
            parameters.put("stringSearch", StringUtility.convertUppercaseToLowercase(StringUtility.toFullSearchLike(StringUtility.removeAccent(filter.getStringSearch()))));
        }
        sql.append(" ORDER BY u.status, u.last_name ASC ");
        parameters.put("status", StatusType.ACTIVE.getValue());
        sql.append(getPagingSql(filter.getPaging()));
        PaginationResult<User> result = new PaginationResult<>(filter.getPaging());
        List<User> data = repo.getEntities(User.class, sql.toString(), parameters);
        result.setData(data);
        return result;
    }

}
