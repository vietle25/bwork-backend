package com.ieltshub.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ieltshub.config.AppConstant;
import com.ieltshub.config.ApplicationConfig;
import com.ieltshub.utility.DateUtility;
import com.ieltshub.utility.StringUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author tuannd
 * @date 21/12/2016
 * @since 1.0
 */
@Component
public class CustomRequestHeaderInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    protected ApplicationConfig appConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientTime = request.getHeader(AppConstant.HEADER_X_CLIENT_TIME);
        if (StringUtility.isNotEmpty(clientTime)) {
            Date date = DateUtility.valueOf(clientTime, appConfig.getDefaultDateTimeFormat());
            request.setAttribute(AppConstant.REQUETS_ATTRIBUTE_CLIENT_TIME_TMP, date);
        }
        return true;
    }
}
