package com.ieltshub.repository;

import com.ieltshub.entity.AppConfig;
import com.ieltshub.entity.SalaryDetail;
import com.ieltshub.entity.User;
import com.ieltshub.enumeration.*;
import com.ieltshub.utility.StringUtility;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.user.UserDTO;
import com.ieltshub.viewmodel.user.UserFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User repository
 *
 * @author Tuan
 */
@org.springframework.stereotype.Repository
public class UserRepositoryImpl extends AbstractRepository implements UserRepositoryExtend {

    @Override
    public Boolean isUserPhoneExist(String phone) {
        StringBuilder sql = new StringBuilder(" select count(*) count from users where phone = :phone ");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("phone", phone.toLowerCase().trim());
        String result = repo.getSingleResult(sql, parameters);
        return (new Integer(result)) > 0;
    }

    @Override
    public Boolean isUserEmailExist(String email) {
        StringBuilder sql = new StringBuilder(" select count(*) count from users where email = :email ");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", email.toLowerCase().trim());
        String result = repo.getSingleResult(sql, parameters);
        return (new Integer(result)) > 0;
    }

    @Override
    public User getUserBySocialId(String socialId, LoginType socialType) {
        if (StringUtility.isEmpty(socialId) || socialType == null) {
            throw new IllegalArgumentException("parameter must not be null.");
        }
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append(" select * from users where 1=1 ");
        if (LoginType.FACEBOOK == socialType) {
            sql.append(" and fb_id = :socialId ");
        } else if (LoginType.GOOGLE == socialType) {
            sql.append(" and gg_id = :socialId ");
        } else if (LoginType.TWITTER == socialType) {
            sql.append(" and tw_id = :socialId ");
        }
        parameters.put("socialId", socialId);
        sql.append(" and status != :status ");
        parameters.put("status", StatusType.DELETE.getValue());
        return repo.getEntity(User.class, sql, parameters);
    }

    @Override
    public List<User> getTotalPersonnel(Long companyId, Long branchId) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT * ");
        sql.append("FROM users u ");
        if (branchId != null) {
            sql.append("JOIN user_branch ub ON u.id = ub.user_id ");
        }
        sql.append("WHERE 1 = 1 ");
        if (branchId != null) {
            sql.append("  AND ub.branch_id = :branchId ");
            sql.append("  AND ub.status = :status ");
            parameters.put("branchId", branchId);
        }
        sql.append("  AND exists ");
        sql.append("    (SELECT 1 ");
        sql.append("     FROM company c ");
        sql.append("     WHERE c.id = u.company_id ");
        sql.append("       AND c.status = :status ) ");
        sql.append("  AND u.company_id = :companyId ");
        sql.append("  AND u.status = :status ");
        sql.append("  AND u.type = :userType ");
        parameters.put("status", StatusType.ACTIVE.getValue());
        parameters.put("userType", UserType.NORMAL_USERS.getValue());
        parameters.put("companyId", companyId);
        List<User> data = repo.getEntities(User.class, sql.toString(), parameters);
        return data;
    }

    @Override
    public PaginationResult<User> getListUserChat(UserFilter filter, Long userId) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT * ");
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
        if (filter.getStringSearch() != null) {
            sql.append("       AND concat_ws(' ', u.first_name_search, u.last_name_search ) like :stringSearch  ");
            parameters.put("stringSearch", StringUtility.convertUppercaseToLowercase(StringUtility.toFullSearchLike(StringUtility.removeAccent(filter.getStringSearch()))));
        }
        if (filter.getCompanyId() != null) {
            sql.append("  AND exists ");
            sql.append("    (SELECT 1 ");
            sql.append("     FROM company c ");
            sql.append("     WHERE c.id = u.company_id ");
            sql.append("       AND c.status = :status ) ");
            sql.append("  AND u.company_id = :companyId ");
            parameters.put("companyId", filter.getCompanyId());
        }
        if (filter.getMemberType() == null) {
            sql.append("  AND u.id != :userId ");
            parameters.put("userId", userId);
        }
        if (filter.getConversationId() != null && filter.getMemberType() != null && filter.getMemberType() == MemberType.MEMBER_INVITE.getValue()) {
            sql.append(" AND not exists(SELECT  1 FROM conversation_member cm ");
            sql.append(" WHERE cm.conversation_id = :conversationId ");
            sql.append(" AND cm.deleted_conversation != true ");
            sql.append(" AND cm.user_id = u.id) ");
            parameters.put("conversationId", filter.getConversationId());
        } else if (filter.getMemberType() != null && filter.getMemberType() == MemberType.MEMBER_GROUP.getValue()) {
            sql.append(" AND exists(SELECT  1 FROM conversation_member cm ");
            sql.append(" WHERE cm.conversation_id = :conversationId ");
            sql.append(" AND cm.deleted_conversation != true ");
            sql.append(" AND cm.user_id = u.id) ");
            parameters.put("conversationId", filter.getConversationId());
        }
        sql.append("  AND u.status = :status ");
        if (filter.getUserType() != null) {
            sql.append("  AND u.type = :userType ");
            parameters.put("userType", filter.getUserType());
        }
        parameters.put("status", StatusType.ACTIVE.getValue());
        sql.append(getPagingSql(filter.getPaging()));
        PaginationResult<User> result = new PaginationResult<>(filter.getPaging());
        List<User> data = repo.getEntities(User.class, sql.toString(), parameters);
        result.setData(data);
        return result;
    }
}
