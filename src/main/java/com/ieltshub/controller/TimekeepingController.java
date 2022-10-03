package com.ieltshub.controller;

import com.ieltshub.business.TimekeepingBus;
import com.ieltshub.business.UserBus;
import com.ieltshub.common.ErrorCode;
import com.ieltshub.config.AppConstant;
import com.ieltshub.exception.BusinessException;
import com.ieltshub.response.ApiResponse;
import com.ieltshub.response.ErrorResponse;
import com.ieltshub.response.SuccessResponse;
import com.ieltshub.viewmodel.cache.UserInfo;
import com.ieltshub.viewmodel.timekeeping.ApprovalTimekeepingFilter;
import com.ieltshub.viewmodel.timekeeping.TimekeepingAdminFilter;
import com.ieltshub.viewmodel.timekeeping.TimekeepingFilter;
import com.ieltshub.viewmodel.timekeeping.TimekeepingListFilter;
import com.ieltshub.viewmodel.timekeeping.history.TimekeepingHistoryFilter;
import com.ieltshub.viewmodel.timekeeping.wifi.WiFiConfigFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Timekeeping controller
 *
 * @author quangtinh
 * @date 08/01/2019
 * @since 1.0
 */
@RestController
@RequestMapping("/timekeeping")
public class TimekeepingController extends AbstractController {

    @Autowired
    private TimekeepingBus timekeepingBus;

    @Autowired
    private UserBus userBus;

    @GetMapping(path = "/wi-fi/config")
    public ApiResponse getWiFiConfig(ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                return new SuccessResponse(timekeepingBus.getWiFiConfig(userInfo.getUserId()));
            }
            return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/wi-fi/config/admin")
    public ApiResponse getWiFiConfigAdmin(@RequestBody WiFiConfigFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                return new SuccessResponse(timekeepingBus.getWiFiConfigAdmin(filter));
            }
            return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @GetMapping(path = "/working-time/config/{userId}")
    public ApiResponse getWorkingTimeConfig(@PathVariable Long userId, ModelMap modelMap, HttpServletRequest request) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
                return new SuccessResponse(timekeepingBus.getWorkingTimeConfig(userId, platform));
            }
            return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @GetMapping(path = "/working-time/config")
    public ApiResponse getWorkingTimeConfig(ModelMap modelMap, HttpServletRequest request) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
                return new SuccessResponse(timekeepingBus.getWorkingTimeConfig(userInfo.getUserId(), platform));
            }
            return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/check")
    public ApiResponse timekeeping(@RequestBody TimekeepingFilter filter,
                                   ModelMap modelMap, HttpServletRequest request) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
                String deviceId = request.getHeader(AppConstant.HEADER_X_DEVICE_ID);
                String phone = timekeepingBus.getPhoneByUserId(userInfo.getUserId());
                String companyCode = userBus.getCompanyCodeByUserId(userInfo.getUserId());
                if (!phone.equals(AppConstant.PHONE_GOVERNOR)
                        && !companyCode.equals(AppConstant.COMPANY_CODE_DEFAULT)
                        && timekeepingBus.getUserDeviceLogin(userInfo.getUserId(), deviceId) == null) {
                    return new ErrorResponse(ErrorCode.BUS_USER_HAS_ALREADY_BEEN_REMOVED_FROM_THE_DEVICE);
                } else if (timekeepingBus.getTimekeepingCheckIn(filter, userInfo) == ErrorCode.NO_ERROR) {
                    return new SuccessResponse(timekeepingBus.timekeeping(filter, userInfo, platform));
                } else {
                    return new ErrorResponse(timekeepingBus.getTimekeepingCheckIn(filter, userInfo));
                }
            } else {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @GetMapping(path = "/today")
    public ApiResponse getTimekeeping(ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                return new SuccessResponse(timekeepingBus.getTimekeeping(userInfo.getUserId()));
            } else {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/list")
    public ApiResponse getTimekeepingList(@RequestBody TimekeepingListFilter filter, ModelMap modelMap,
                                          HttpServletRequest request) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
                return new SuccessResponse(timekeepingBus.getTimekeepingList(filter, platform));
            } else {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/history")
    public ApiResponse getTimekeepingHistory(@RequestBody TimekeepingHistoryFilter filter, ModelMap modelMap,
                                             HttpServletRequest request) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
                return new SuccessResponse(timekeepingBus.getTimekeepingHistory(
                        filter,
                        filter.getUserId() != null ? filter.getUserId() : userInfo.getUserId(),
                        platform
                ));
            } else {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/history/detail")
    public ApiResponse getTimekeepingHistoryDetail(@RequestBody TimekeepingHistoryFilter filter, ModelMap modelMap,
                                                   HttpServletRequest request) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
                return new SuccessResponse(timekeepingBus.getTimekeepingHistoryDetail(
                        filter,
                        filter.getUserId() != null ? filter.getUserId() : userInfo.getUserId(),
                        platform
                ));
            } else {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/{timekeepingId}/approval")
    public ApiResponse approvalTimekeeping(@PathVariable Long timekeepingId,
                                           @RequestBody ApprovalTimekeepingFilter filter,
                                           ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                return new SuccessResponse(timekeepingBus.approvalTimekeeping(filter, userInfo.getUserId(),
                        timekeepingId));
            } else {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "upload/image-path")
    public ApiResponse uploadImagePath(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            if (file.isEmpty()) {
                return new ErrorResponse("File empty", ErrorCode.SYSTEM_ERROR);
            }
            return new SuccessResponse(timekeepingBus.uploadImagePath(file, userInfo.getUserId()));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @PostMapping(path = "/admin/check")
    public ApiResponse timekeepingAdmin(@RequestBody TimekeepingAdminFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(timekeepingBus.timekeepingAdmin(filter, userInfo.getUserId()));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/{timekeepingId}/update")
    public ApiResponse timekeepingUpdate(@PathVariable Long timekeepingId,
                                         @RequestBody TimekeepingAdminFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(timekeepingBus.timekeepingUpdate(timekeepingId, filter, userInfo.getUserId()));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @GetMapping(path = "/{timekeepingId}/delete")
    public ApiResponse timekeepingDelete(@PathVariable Long timekeepingId, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(timekeepingBus.timekeepingDelete(timekeepingId, userInfo.getUserId()));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }
}
