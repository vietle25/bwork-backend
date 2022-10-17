package com.evowork.controller;

import com.evowork.business.TaskBus;
import com.evowork.common.ErrorCode;
import com.evowork.config.AppConstant;
import com.evowork.exception.BusinessException;
import com.evowork.response.ApiResponse;
import com.evowork.response.ErrorResponse;
import com.evowork.response.SuccessResponse;
import com.evowork.viewmodel.cache.UserInfo;
import com.evowork.viewmodel.task.TaskFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author quangln
 * @date 06/10/2020
 * @since 1.0
 */
@RestController
@RequestMapping("/task")
public class TaskController extends AbstractController {

    @Autowired
    private TaskBus taskBus;

    @PostMapping(path = "list")
    public ApiResponse getListTask(@RequestBody TaskFilter taskFilter, ModelMap modelMap){
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            return new SuccessResponse(taskBus.getListTask(taskFilter, userInfo));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @GetMapping(path = "/{taskId}/detail")
    public ApiResponse getTaskDetail(@PathVariable("taskId") Long taskId, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(taskBus.getTaskDetail(taskId, userInfo));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @PostMapping(path = "/{taskId}/update")
    public ApiResponse updateTask(@PathVariable("taskId") Long taskId, @RequestBody TaskFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(taskBus.updateTask(taskId, filter, userInfo));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @GetMapping(path = "/time/config")
    public ApiResponse getTaskTimeConfig(ModelMap modelMap, HttpServletRequest request) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
                return new SuccessResponse(taskBus.getTaskTimeConfig(userInfo, platform));
            }
            return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }
}
