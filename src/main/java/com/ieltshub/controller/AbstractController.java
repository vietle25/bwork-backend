package com.ieltshub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ieltshub.config.AppConstant;
import com.ieltshub.config.ApplicationConfig;
import com.ieltshub.exception.BusinessException;
import com.ieltshub.exception.IllegalCastException;
import com.ieltshub.service.CacheService;
import com.ieltshub.utility.BusinessUtility;
import com.ieltshub.utility.StringUtility;
import com.ieltshub.utility.TokenUtility;
import com.ieltshub.viewmodel.cache.UserInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public abstract class AbstractController {
	
    @Autowired
    protected ApplicationConfig appConfig;

    @Autowired
    protected CacheService cacheService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @ModelAttribute(value = AppConstant.REQUETS_ATTRIBUTE_UTMP)
    protected UserInfo userInfo(HttpServletRequest request) {
        Object attribute = request.getAttribute(AppConstant.REQUETS_ATTRIBUTE_UTMP);
        return attribute != null && attribute instanceof UserInfo ? (UserInfo) attribute : null;
    }

    protected UserInfo getUserInfo(ModelMap modelMap) {
        UserInfo userInfo = (UserInfo) modelMap.get(AppConstant.REQUETS_ATTRIBUTE_UTMP);
        Object clientTime = modelMap.get(AppConstant.REQUETS_ATTRIBUTE_CLIENT_TIME_TMP);
        if (clientTime != null && userInfo != null) {
            userInfo.setClientTime((Timestamp) clientTime);
        }
        return userInfo;
    }

    @ModelAttribute(value = AppConstant.REQUETS_ATTRIBUTE_CLIENT_TIME_TMP)
    protected Timestamp clientTime(HttpServletRequest request) {
        Object attribute = request.getAttribute(AppConstant.REQUETS_ATTRIBUTE_CLIENT_TIME_TMP);
        return attribute != null && attribute instanceof Date ? new Timestamp(((Date) attribute).getTime()) : null;
    }

    protected void forward(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
    }
    
    /**
     * Validate token
     * @param request
     * @return
     */
    protected boolean validateToken(HttpServletRequest request) throws BusinessException{
    	String token = request.getHeader(AppConstant.HEADER_X_APITOKEN);
        try {
            UserInfo userInfo = cacheService.getValue(UserInfo.class, token);
            if (token != null && !StringUtility.isEmpty(token)) {
                return TokenUtility.checkToken(token, userInfo.getUserIdentifier());
            }
            throw new BusinessException("Token invalid");
        } catch (IllegalCastException e) {
            return false;
        }
    }

    protected String encodePassword(String password) {
        String passwordWithSalt = BusinessUtility.addSaltToPassword(password);
        return passwordEncoder.encode(passwordWithSalt);
    }

    protected Boolean passwordMatch(String rawPassword, String encodedPassword) {
        /*List<String> allPossiblePasswords = BusinessUtility.getAllPossiblePasswords(rawPassword);
        return allPossiblePasswords.parallelStream().anyMatch(password -> passwordEncoder.matches(password, encodedPassword));*/

        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
