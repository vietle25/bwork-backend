package com.ieltshub.repository;

import com.ieltshub.entity.User;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.department.DepartmentFilter;

public interface DepartmentRepositoryExtend {

    /**
     *
     * @param userId
     * @param filter
     * @return
     */
    PaginationResult<User> getStaffDepartment(Long userId, DepartmentFilter filter);
}
