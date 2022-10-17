package com.evowork.controller;

import com.evowork.business.SabbaticalBus;
import com.evowork.common.ErrorCode;
import com.evowork.config.AppConstant;
import com.evowork.response.ApiResponse;
import com.evowork.response.ErrorResponse;
import com.evowork.response.SuccessResponse;
import com.evowork.viewmodel.cache.UserInfo;
import com.evowork.viewmodel.sabbatical.RegisterSabbaticalFilter;
import com.evowork.viewmodel.sabbatical.SabbaticalFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Sabbatical controller
 *
 * @author quangtinh
 * @date 08/01/2019
 * @since 1.0
 */
@RestController
@RequestMapping("/sabbatical")
public class SabbaticalController extends AbstractController {

    @Autowired
    private SabbaticalBus sabbaticalBus;

    @PostMapping(path = "/list")
    public ApiResponse getSabbaticals(@RequestBody SabbaticalFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                return new SuccessResponse(sabbaticalBus.getSabbaticals(userInfo.getUserId(), filter));
            }
            return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/list/admin")
    public ApiResponse getSabbaticalsAdmin(@RequestBody SabbaticalFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                return new SuccessResponse(sabbaticalBus.getSabbaticalsAdmin(userInfo.getUserId(), filter));
            }
            return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/register")
    public ApiResponse registerSabbatical(@RequestBody RegisterSabbaticalFilter filter,
                                          HttpServletRequest request,
                                          ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            Boolean hasSabbatical = sabbaticalBus.hasSabbatical(userInfo.getUserId(), filter);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            } else if (hasSabbatical) {
                return new ErrorResponse(ErrorCode.HAS_SABBATICAL_REGISTERED);
            }
            String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
            return new SuccessResponse(sabbaticalBus.registerSabbatical(userInfo.getUserId(), filter, platform));
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/{sabbaticalId}/update")
    public ApiResponse registerSabbatical(@PathVariable Long sabbaticalId,
                                          @RequestBody RegisterSabbaticalFilter filter,
                                          HttpServletRequest request,
                                          ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
                return new SuccessResponse(sabbaticalBus.updateSabbatical(
                        userInfo.getUserId(), sabbaticalId, filter, platform));
            }
            return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @GetMapping(path = "/{sabbaticalId}/delete")
    public ApiResponse registerSabbatical(@PathVariable Long sabbaticalId, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                return new SuccessResponse(sabbaticalBus.deleteSabbatical(sabbaticalId, userInfo.getUserId()));
            }
            return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @GetMapping(path = "/{sabbaticalId}/approved")
    public ApiResponse approvedSabbatical(@PathVariable Long sabbaticalId, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                return new SuccessResponse(sabbaticalBus.approvedSabbatical(sabbaticalId, userInfo.getUserId()));
            }
            return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/{sabbaticalId}/denied")
    public ApiResponse deniedSabbatical(@PathVariable Long sabbaticalId, @RequestBody SabbaticalFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                return new SuccessResponse(sabbaticalBus.deniedSabbatical(sabbaticalId, userInfo.getUserId(), filter.getDeniedNote()));
            }
            return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }
}