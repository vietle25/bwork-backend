package com.evowork.repository;

import com.evowork.entity.User;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.department.DepartmentFilter;

public interface DepartmentRepositoryExtend {

    /**
     *
     * @param userId
     * @param filter
     * @return
     */
    PaginationResult<User> getStaffDepartment(Long userId, DepartmentFilter filter);
}
