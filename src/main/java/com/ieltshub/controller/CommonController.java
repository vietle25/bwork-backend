package com.ieltshub.controller;

import com.ieltshub.business.CommonBus;
import com.ieltshub.common.ErrorCode;
import com.ieltshub.config.AppConstant;
import com.ieltshub.exception.BusinessException;
import com.ieltshub.response.ApiResponse;
import com.ieltshub.response.ErrorResponse;
import com.ieltshub.response.SuccessResponse;
import com.ieltshub.viewmodel.cache.UserInfo;
import com.ieltshub.viewmodel.common.ExceptionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/common")
public class CommonController extends AbstractController {

    @Autowired
    private CommonBus commonBus;

    @GetMapping(path = "version")
    public ApiResponse getVersion(HttpServletRequest request) {
        try {
            String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
            String buildId = request.getHeader(AppConstant.HEADER_X_APP_TYPE);
            return new SuccessResponse(commonBus.getUpdateVersion(platform, buildId));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/save/exception")
    public ApiResponse saveException(@RequestBody ExceptionFilter filter, ModelMap modelMap, HttpServletRequest request) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
            return new SuccessResponse(commonBus.saveException(filter, userInfo, platform));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @GetMapping(path = "/time/today")
    public ApiResponse getTimeToday() {
        try {
            return new SuccessResponse(commonBus.getTimeToday());
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }
}
