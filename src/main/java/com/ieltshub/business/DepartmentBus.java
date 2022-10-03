package com.ieltshub.business;

import com.ieltshub.exception.BusinessException;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.department.DepartmentFilter;
import com.ieltshub.viewmodel.department.DepartmentModel;
import com.ieltshub.viewmodel.user.UserDTO;

import java.util.List;

public interface DepartmentBus {

    /**
     *
     * @param filter
     * @return
     * @throws BusinessException
     */
    List<DepartmentModel> getListDepartment(DepartmentFilter filter) throws BusinessException;

    /**
     *
     * @param userId
     * @param filter
     * @return
     * @throws BusinessException
     */
    PaginationResult<UserDTO> getStaffDepartment(Long userId, DepartmentFilter filter) throws BusinessException;
}
