package com.ieltshub.business;


import com.ieltshub.entity.Department;
import com.ieltshub.entity.User;
import com.ieltshub.enumeration.StatusType;
import com.ieltshub.exception.BusinessException;
import com.ieltshub.repository.DepartmentRepository;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.department.DepartmentFilter;
import com.ieltshub.viewmodel.department.DepartmentModel;
import com.ieltshub.viewmodel.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentBusImpl extends AbstractBusiness implements DepartmentBus {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentModel> getListDepartment(DepartmentFilter filter) {
        List<DepartmentModel> departmentList = new ArrayList<>();
        List<Department> departmentPaginationResult = departmentRepository.getDepartments(filter.getCompanyId(), StatusType.ACTIVE.getValue());
        if(departmentPaginationResult != null) {
            for (Department item : departmentPaginationResult) {
                DepartmentModel departmentModel = new DepartmentModel(item);
                departmentList.add(departmentModel);
            }
        }
        return departmentList;
    }

    @Override
    public PaginationResult<UserDTO> getStaffDepartment(Long userId, DepartmentFilter filter) throws BusinessException {
        PaginationResult<User> user = departmentRepository.getStaffDepartment(userId, filter);

        PaginationResult<UserDTO> result = new PaginationResult<>(filter.getPaging());
        result.setData(user.getData().stream().map(item -> {
            UserDTO model = new UserDTO(item);
            Department department = departmentRepository.getDepartment(item.getId(), StatusType.ACTIVE.getValue());
            if(department != null) {
                DepartmentModel departmentModel = new DepartmentModel(department);
                model.setDepartment(departmentModel);
            }
            return model;
        }).collect(Collectors.toList()));
        return result;
    }

}
