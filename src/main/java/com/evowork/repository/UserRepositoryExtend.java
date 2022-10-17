package com.evowork.repository;

import com.evowork.entity.User;
import com.evowork.enumeration.LoginType;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.user.UserFilter;

import java.util.List;

/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public interface UserRepositoryExtend {

    /**
     * Check phone exist
     *
     * @param phone
     * @return
     */
    Boolean isUserPhoneExist(String phone);

    /**
     * Check if user email exist
     *
     * @param email
     * @return
     * @since 1.0
     */
    Boolean isUserEmailExist(String email);

    /**
     * Login with social id
     *
     * @param socialId
     * @param socialType
     * @return
     */
    User getUserBySocialId(String socialId, LoginType socialType);

    /**
     * Get total personnel
     * @param companyId
     * @param branchId
     * @return
     */
    List<User> getTotalPersonnel(Long companyId, Long branchId);

    /**
     * get list user invite group chat
     * @param filter
     * @param userId
     * @return
     */
    PaginationResult<User> getListUserChat(UserFilter filter, Long userId);
}
