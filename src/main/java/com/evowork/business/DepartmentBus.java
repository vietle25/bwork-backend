package com.evowork.business;

import com.evowork.exception.BusinessException;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.department.DepartmentFilter;
import com.evowork.viewmodel.department.DepartmentModel;
import com.evowork.viewmodel.user.UserDTO;

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
