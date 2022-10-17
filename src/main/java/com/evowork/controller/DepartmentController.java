package com.evowork.controller;

import com.evowork.business.DepartmentBus;
import com.evowork.common.ErrorCode;
import com.evowork.exception.BusinessException;
import com.evowork.response.ApiResponse;
import com.evowork.response.ErrorResponse;
import com.evowork.response.SuccessResponse;
import com.evowork.viewmodel.cache.UserInfo;
import com.evowork.viewmodel.department.DepartmentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Department controller
 *
 * @author quanglengoc
 * @date 02/03/2020
 * @since 1.0
 */

@RestController
@RequestMapping("/department")
public class DepartmentController extends AbstractController {

    @Autowired
    private DepartmentBus departmentBus;

    @PostMapping(path = "/list")
    public ApiResponse listDepartment(@RequestBody DepartmentFilter filter) {
        try {
            return new SuccessResponse(departmentBus.getListDepartment(filter));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/staff/list")
    public ApiResponse getStaffSalary(@RequestBody DepartmentFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(departmentBus.getStaffDepartment(userInfo.getUserId(), filter));
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

}
