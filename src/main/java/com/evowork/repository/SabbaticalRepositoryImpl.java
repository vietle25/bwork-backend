package com.evowork.repository;

import com.evowork.entity.Sabbatical;
import com.evowork.enumeration.*;
import com.evowork.utility.StringUtility;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.company.DashboardFilter;
import com.evowork.viewmodel.sabbatical.SabbaticalFilter;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SabbaticalRepositoryImpl extends AbstractRepository implements SabbaticalRepositoryExtend {

    @Override
    public PaginationResult<Sabbatical> getSabbaticals(Long userId, SabbaticalFilter filter) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("select s.*  ");
        sql.append("from sabbatical s ");
        sql.append("left join users u on s.user_id = u.id ");
        sql.append("where 1 = 1 ");
        sql.append("and s.user_id = :userId ");
        sql.append("and s.company_id = u.company_id ");
        sql.append("and s.status = :status ");
        if(filter.getMonth() != null) {
            sql.append("               AND to_char(s.off_from_date at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM') = :month ");
            parameters.put("month", filter.getMonth());
        }
        if(filter.getDay() != null) {
            sql.append("               AND to_char(s.off_from_date at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :createdAt ");
            parameters.put("createdAt", filter.getDay());
        }
        sql.append("order by s.created_at desc ");
        parameters.put("userId", userId);
        parameters.put("status", StatusType.ACTIVE.getValue());
        sql.append(getPagingSql(filter.getPaging()));
        PaginationResult<Sabbatical> result = new PaginationResult<>(filter.getPaging());
        List<Sabbatical> data = repo.getEntities(Sabbatical.class, sql.toString(), parameters);
        result.setData(data);
        return result;
    }

    @Override
    public PaginationResult<Sabbatical> getSabbaticalsAdmin(Long userId, SabbaticalFilter filter) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT s.* ");
        sql.append("FROM sabbatical s ");
        sql.append("    JOIN users u ");
        sql.append("        ON s.user_id = u.id ");
        sql.append("               AND s.status = :status ");
        sql.append("               AND u.status = :status ");
        sql.append("               AND s.company_id = :companyId ");
        if(filter.getMonth() != null) {
            sql.append("               AND to_char(s.off_from_date at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM') = :month ");
            parameters.put("month", filter.getMonth());
        }
        if(filter.getDay() != null) {
            sql.append("               AND to_char(s.off_from_date at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :createdAt ");
            parameters.put("createdAt", filter.getDay());
        }
        sql.append("    JOIN company c ");
        sql.append("        ON s.company_id = c.id ");
        sql.append("               AND c.status = :status ");
        if(filter.getBranchId() != null) {
            sql.append("    JOIN user_branch ub ");
            sql.append("        ON u.id = ub.user_id ");
            sql.append("               AND ub.status = :status ");
            sql.append("               AND s.branch_id = :branchId ");
            parameters.put("branchId", filter.getBranchId());
        }
        sql.append("   WHERE 1 = 1    ");
        if(filter.getStringSearch() != null) {
            sql.append("       AND concat_ws(' ', u.first_name_search, u.last_name_search ) like :stringSearch  ");
            parameters.put("stringSearch", StringUtility.convertUppercaseToLowercase(StringUtility.toFullSearchLike(StringUtility.removeAccent(filter.getStringSearch()))));
        }
        sql.append("  ORDER BY s.created_at DESC, s.approval_status ASC  ");
        parameters.put("companyId", filter.getCompanyId());
        parameters.put("status", StatusType.ACTIVE.getValue());
        sql.append(getPagingSql(filter.getPaging()));
        PaginationResult<Sabbatical> result = new PaginationResult<>(filter.getPaging());
        List<Sabbatical> data = repo.getEntities(Sabbatical.class, sql.toString(), parameters);
        result.setData(data);
        return result;
    }

    @Override
    public Boolean countSabbaticalExist(Long userId, Integer status, Integer approvalStatus,
                                        Date offFromDate, Date offToDate, Integer offType) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("select count(*) from sabbatical ");
        sql.append("where user_id = :userId ");
        sql.append("and (");
        sql.append("        (off_from_date <= :offFromDate and off_to_date >= :offFromDate) or ");
        sql.append("        off_from_date = :offToDate or ");
        sql.append("        off_to_date = :offToDate ");
        sql.append(") ");
        if (offType == SabbaticalOffType.FULL_WORKING_DAY_1.getValue()) {
            sql.append("and (off_type = :offType1 or off_type = :offType2) ");
            parameters.put("offType1", SabbaticalOffType.FULL_WORKING_DAY_1.getValue());
            parameters.put("offType2", SabbaticalOffType.PARTLY_WORKING_DAY.getValue());
        } else if (offType == SabbaticalOffType.FULL_WORKING_DAY_2.getValue()) {
            sql.append("and (off_type = :offType1 or off_type = :offType2) ");
            parameters.put("offType1", SabbaticalOffType.FULL_WORKING_DAY_2.getValue());
            parameters.put("offType2", SabbaticalOffType.PARTLY_WORKING_DAY.getValue());
        }
        sql.append("and status = :status ");
        sql.append("and approval_status != :approvalStatus");
        parameters.put("userId", userId);
        parameters.put("offFromDate", offFromDate);
        parameters.put("offToDate", offToDate);
        parameters.put("status", status);
        parameters.put("approvalStatus", approvalStatus);
        String result = repo.getSingleResult(sql, parameters);
        return (new Integer(result)) > 0;
    }

    @Override
    public Integer getTotalSabbatical(DashboardFilter filter) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT count(*) ");
        sql.append("FROM sabbatical s ");
        sql.append("WHERE s.company_id = :companyId ");
        if (filter.getBranchId() != null) {
            sql.append("AND s.branch_id = :branchId ");
            parameters.put("branchId", filter.getBranchId());
        }
        sql.append("AND s.status = :status ");
        sql.append("AND to_char(s.created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :day ");
        parameters.put("status", StatusType.ACTIVE.getValue());
        parameters.put("companyId", filter.getCompanyId());
        parameters.put("day", filter.getDay());
        String result = repo.getSingleResult(sql, parameters);
        return new Integer(result);
    }
}
