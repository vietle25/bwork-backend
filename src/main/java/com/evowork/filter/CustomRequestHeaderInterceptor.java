package com.evowork.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.evowork.config.AppConstant;
import com.evowork.config.ApplicationConfig;
import com.evowork.utility.DateUtility;
import com.evowork.utility.StringUtility;

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
