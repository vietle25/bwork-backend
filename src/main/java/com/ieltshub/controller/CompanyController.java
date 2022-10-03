package com.ieltshub.controller;

import com.ieltshub.business.CompanyBus;
import com.ieltshub.common.ErrorCode;
import com.ieltshub.config.AppConstant;
import com.ieltshub.response.ApiResponse;
import com.ieltshub.response.ErrorResponse;
import com.ieltshub.response.SuccessResponse;
import com.ieltshub.viewmodel.cache.UserInfo;
import com.ieltshub.viewmodel.company.DashboardFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Company controller
 *
 * @author quangtinh
 * @date 08/01/2019
 * @since 1.0
 */
@RestController
@RequestMapping("/company")
public class CompanyController extends AbstractController {

    @Autowired
    private CompanyBus companyBus;

    @PostMapping(path = "/statistical/get")
    public ApiResponse getDashboardStatistical(@RequestBody DashboardFilter filter,
                                               ModelMap modelMap,
                                               HttpServletRequest request) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
                return new SuccessResponse(companyBus.getDashboardStatistical(filter, userInfo, platform));
            } else {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }
}
