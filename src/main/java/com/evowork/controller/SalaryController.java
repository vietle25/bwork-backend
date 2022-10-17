package com.evowork.controller;

import com.evowork.business.SalaryBus;
import com.evowork.common.ErrorCode;
import com.evowork.config.AppConstant;
import com.evowork.response.ApiResponse;
import com.evowork.response.ErrorResponse;
import com.evowork.response.SuccessResponse;
import com.evowork.viewmodel.cache.UserInfo;
import com.evowork.viewmodel.salary.SalaryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Timekeeping controller
 *
 * @author quangtinh
 * @date 08/01/2019
 * @since 1.0
 */
@RestController
@RequestMapping("/salary")
public class SalaryController extends AbstractController {

    @Autowired
    private SalaryBus salaryBus;

    @PostMapping(path = "/history")
    public ApiResponse getSalaryHistory(@RequestBody(required = false) SalaryFilter filter,
                                        ModelMap modelMap,
                                        HttpServletRequest request) {
        try {
            UserInfo userInfo = new UserInfo();
            if (filter.getUserId() != null) {
                userInfo.setUserId(filter.getUserId());
            } else {
                userInfo = getUserInfo(modelMap);
            }
            if (userInfo != null) {
                String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
                return new SuccessResponse(salaryBus.getSalary(userInfo.getUserId(), filter, platform));
            } else {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/config")
    public ApiResponse getSalaryConfig(@RequestBody SalaryFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                return new SuccessResponse(salaryBus.getSalaryConfig(filter));
            } else {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/detail/type")
    public ApiResponse getDetailTypeSalary(@RequestBody(required = false) SalaryFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(salaryBus.getDetailTypeSalary(userInfo.getUserId(), filter));
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/staff/list")
    public ApiResponse getStaffSalary(@RequestBody SalaryFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(salaryBus.getStaffSalary(userInfo.getUserId(), filter));
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }
}
