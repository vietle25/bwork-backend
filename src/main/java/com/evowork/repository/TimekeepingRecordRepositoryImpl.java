package com.evowork.repository;

import com.evowork.entity.TimekeepingRecord;
import com.evowork.entity.User;
import com.evowork.enumeration.*;
import com.evowork.utility.StringUtility;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.company.DashboardFilter;
import com.evowork.viewmodel.timekeeping.TimekeepingListFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TimekeepingRecord repository
 *
 * @author Tuan
 */
public class TimekeepingRecordRepositoryImpl extends AbstractRepository implements TimekeepingRecordRepositoryExtend {


    @Override
    public List<TimekeepingRecord> getTimekeepingHistory(String day, Long userId) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("select * from timekeeping_record ");
        sql.append("where user_id = :userId ");
        sql.append("and status = :status ");
        sql.append("and to_char(created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :createdAt ");
        parameters.put("createdAt", day);
        sql.append("order by check_in_time asc ");
        parameters.put("userId", userId);
        parameters.put("status", StatusType.ACTIVE.getValue());
        List<TimekeepingRecord> data = repo.getEntities(TimekeepingRecord.class, sql.toString(), parameters);
        return data;
    }

    @Override
    public Integer getTotalCheckin(DashboardFilter filter) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT count(*) ");
        sql.append("FROM users u ");
        if (filter.getBranchId() != null) {
            sql.append("JOIN user_branch ub ON u.id = ub.user_id ");
        }
        sql.append("WHERE 1 = 1 ");
        if (filter.getBranchId() != null) {
            sql.append("  AND ub.branch_id = :branchId ");
            sql.append("  AND ub.status = :status ");
            parameters.put("branchId", filter.getBranchId());
        }
        sql.append("  AND exists  ");
        sql.append("    (SELECT 1 ");
        sql.append("     FROM company c ");
        sql.append("     WHERE c.id = u.company_id ");
        sql.append("       AND c.status = :status ) ");
        sql.append("  AND u.company_id = :companyId ");
        sql.append("  AND u.status = :status ");
        sql.append("  AND u.type = :userType ");
        sql.append("  AND exists ");
        sql.append("    (SELECT 1 ");
        sql.append("     FROM timekeeping_record tr ");
        sql.append("     WHERE tr.user_id = u.id ");
        sql.append("       AND tr.status = :status ");
//        sql.append("       AND (tr.check_in_type = :checkInType ");
//        sql.append("        OR (tr.check_in_type != :checkInType ");
//        sql.append("            AND tr.check_in_approval_status = :approvalStatus)) ");
        sql.append("     AND to_char(tr.created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :day) ");
        parameters.put("status", StatusType.ACTIVE.getValue());
        parameters.put("userType", UserType.NORMAL_USERS.getValue());
        parameters.put("companyId", filter.getCompanyId());
        parameters.put("day", filter.getDay());
//        parameters.put("approvalStatus", ApprovalStatusType.APPROVED.getValue());
//        parameters.put("checkInType", TimekeepingType.NORMAL.getValue());
        String result = repo.getSingleResult(sql, parameters);
        return new Integer(result);
    }

    @Override
    public Integer getTotalNotCheckin(DashboardFilter filter) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT count(*) ");
        sql.append("FROM users u ");
        if (filter.getBranchId() != null) {
            sql.append("JOIN user_branch ub ON u.id = ub.user_id ");
        }
        sql.append("WHERE 1 = 1 ");
        if (filter.getBranchId() != null) {
            sql.append("  AND ub.branch_id = :branchId ");
            sql.append("  AND ub.status = :status ");
            parameters.put("branchId", filter.getBranchId());
        }
        sql.append("  AND exists  ");
        sql.append("    (SELECT 1 ");
        sql.append("     FROM company c ");
        sql.append("     WHERE c.id = u.company_id ");
        sql.append("       AND c.status = :status ) ");
        sql.append("  AND u.company_id = :companyId ");
        sql.append("  AND u.status = :status ");
        sql.append("  AND u.type = :userType ");
        sql.append("  AND NOT exists ");
        sql.append("    (SELECT 1 ");
        sql.append("     FROM sabbatical s ");
        sql.append("     WHERE s.user_id = u.id ");
        sql.append("       AND s.status = :status ");
        sql.append("     AND to_char(s.off_from_date at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :day) ");
        sql.append("  AND NOT exists ");
        sql.append("    (SELECT 1 ");
        sql.append("     FROM timekeeping_record tr ");
        sql.append("     WHERE tr.user_id = u.id ");
        sql.append("       AND tr.status = :status ");
        sql.append("     AND to_char(tr.created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :day) ");
        parameters.put("status", StatusType.ACTIVE.getValue());
        parameters.put("userType", UserType.NORMAL_USERS.getValue());
        parameters.put("companyId", filter.getCompanyId());
        parameters.put("day", filter.getDay());
        String result = repo.getSingleResult(sql, parameters);
        return new Integer(result);
    }

    @Override
    public PaginationResult<User> getUserTimekeeping(TimekeepingListFilter filter) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT u.* ");
        sql.append("FROM users u ");
        if (filter.getBranchId() != null) {
            sql.append("JOIN user_branch ub ON u.id = ub.user_id ");
        }
        sql.append("WHERE 1 = 1 ");
        if (filter.getBranchId() != null) {
            sql.append("  AND ub.branch_id = :branchId ");
            sql.append("  AND ub.status = :status ");
            parameters.put("branchId", filter.getBranchId());
        }
        sql.append("  AND exists  ");
        sql.append("    (SELECT 1 ");
        sql.append("     FROM company c ");
        sql.append("     WHERE c.id = u.company_id ");
        sql.append("       AND c.status = :status ) ");
        sql.append("  AND u.company_id = :companyId ");
        sql.append("  AND u.status = :status ");
        sql.append("  AND u.type = :userType ");
        if (filter.getDashboardType() != DashboardType.LATE_FOR_WORKING.getValue()) {
            if (filter.getDashboardType() == DashboardType.NOT_CHECK_IN.getValue()) {
                sql.append("  AND NOT exists ");
                sql.append("    (SELECT 1 ");
                sql.append("     FROM sabbatical s ");
                sql.append("     WHERE s.user_id = u.id ");
                sql.append("       AND s.status = :status ");
                sql.append("     AND to_char(s.off_from_date at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :day) ");
                sql.append("  AND NOT exists ");
            } else {
                sql.append("  AND exists ");
            }
            sql.append("    (SELECT 1 ");
            sql.append("     FROM timekeeping_record tr ");
            sql.append("     WHERE tr.user_id = u.id ");
            sql.append("       AND tr.status = :status ");
            sql.append("     AND to_char(tr.created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :day) ");
        } else {
            sql.append("  AND (exists(SELECT 1 ");
            sql.append("            FROM (SELECT * ");
            sql.append("                  FROM timekeeping_record ");
            sql.append("                    WHERE user_id = u.id ");
            sql.append("                    AND status = :status ");
            sql.append("                    AND (check_in_approval_status = :approvalStatus OR check_in_approval_status isnull) ");
            sql.append("                    AND (check_out_approval_status = :approvalStatus OR check_out_approval_status isnull) ");
            sql.append("                    AND to_char(created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :day ");
            sql.append("                  ORDER BY check_in_time) as tr ");
            sql.append("                     JOIN (SELECT * ");
            sql.append("                           FROM working_time_config ");
            sql.append("                           WHERE status = :status ");
            sql.append("                             AND ((user_id = u.id AND TYPE = 2) OR (TYPE = 1)) ");
            sql.append("                             AND valid_from <= :day ");
            sql.append("                             AND company_id = :companyId ");
            sql.append("                             ORDER BY TYPE DESC, valid_from DESC, shift_type ASC ");
            sql.append("                           LIMIT 1) wtc ON tr.company_id = wtc.company_id ");
            sql.append("            WHERE tr.check_in_time > to_char(to_timestamp(wtc.start_working_time_1, 'HH24:MI:SS') + ");
            sql.append("                                             CAST((:minuteAfterCheckIn1 || ' minutes') AS interval), 'HH24:MI:SS') ");
            sql.append("              AND tr.check_in_time < wtc.end_working_time_1 ");
            sql.append("         ) ");
            sql.append("  OR (exists(SELECT 1 ");
            sql.append("             FROM (SELECT * ");
            sql.append("                   FROM timekeeping_record ");
            sql.append("                   WHERE user_id = u.id ");
            sql.append("                   AND status = :status ");
            sql.append("                   AND (check_in_approval_status = :approvalStatus OR check_in_approval_status isnull) ");
            sql.append("                   AND (check_out_approval_status = :approvalStatus OR check_out_approval_status isnull) ");
            sql.append("                   AND to_char(created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :day ");
            sql.append("                 ORDER BY check_in_time) as tr ");
            sql.append("                    JOIN (SELECT * ");
            sql.append("                          FROM working_time_config ");
            sql.append("                          WHERE status = :status ");
            sql.append("                            AND ((user_id = u.id AND TYPE = 2) OR (TYPE = 1)) ");
            sql.append("                            AND valid_from <= :day ");
            sql.append("                            AND company_id = :companyId ");
            sql.append("                          ORDER BY TYPE DESC, valid_from DESC, shift_type ASC ");
            sql.append("                          LIMIT 1) wtc ON tr.company_id = wtc.company_id ");
            sql.append("             WHERE tr.check_in_time > to_char( ");
            sql.append("                         to_timestamp(wtc.start_working_time_2, 'HH24:MI:SS') + ");
            sql.append("                         CAST((:minuteAfterCheckIn2 || ' minutes') AS interval), 'HH24:MI:SS') ");
            sql.append("               AND tr.check_in_time < wtc.end_working_time_2 ");
            sql.append("      )) ");
            sql.append("  ) ");
            parameters.put("minuteAfterCheckIn1", filter.getMinuteAfterCheckIn1());
            parameters.put("minuteAfterCheckIn2", filter.getMinuteAfterCheckIn2());
            parameters.put("approvalStatus", ApprovalStatusType.APPROVED.getValue());
        }
        if (filter.getStringSearch() != null) {
            sql.append("and concat_ws(' ', u.first_name_search, u.last_name_search ) like :paramSearch ");
            parameters.put("paramSearch", StringUtility.convertUppercaseToLowercase(
                    StringUtility.toFullSearchLike(StringUtility.removeAccent(filter.getStringSearch()))));
        }
        parameters.put("status", StatusType.ACTIVE.getValue());
        parameters.put("userType", UserType.NORMAL_USERS.getValue());
        parameters.put("companyId", filter.getCompanyId());
        parameters.put("day", filter.getDay());
        PaginationResult<User> result = new PaginationResult<>(filter.getPaging());
        List<User> data = repo.getEntities(User.class, sql.toString(), parameters);
        result.setData(data);
        return result;
    }
}
