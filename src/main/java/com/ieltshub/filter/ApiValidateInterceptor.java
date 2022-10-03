package com.ieltshub.filter;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ieltshub.config.AppConstant;
import com.ieltshub.config.ApplicationConfig;
import com.ieltshub.exception.IllegalCastException;
import com.ieltshub.service.CacheService;
import com.ieltshub.utility.StringUtility;
import com.ieltshub.utility.TokenUtility;
import com.ieltshub.viewmodel.cache.UserInfo;

/**
 * @author tuannd
 * @date 18/07/2016
 * @since 1.0
 */
@Component
public class ApiValidateInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    CacheService cacheService;

    @Autowired
    ApplicationConfig appConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiToken = request.getHeader(AppConstant.HEADER_X_APITOKEN);
        try {
            if (StringUtility.isNotEmpty(apiToken)) {
                UserInfo userInfo = cacheService.getValue(UserInfo.class, apiToken);
				if (userInfo != null && TokenUtility.checkToken(apiToken, userInfo.getUserIdentifier())) {
					request.setAttribute(AppConstant.REQUETS_ATTRIBUTE_UTMP, userInfo);
					return true;
				}
            }
//            if (isTrustedClient(request)) {
//                return true;
//            }
            String mySecretMessage = request.getHeader(AppConstant.HEADER_X_SECRET_MESSAGE);
            if (hasValidSecretMessage(mySecretMessage)) {
                return true;
            }
        } catch (IllegalCastException e) {
            //logging here
        }
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return true;
    }

    private boolean hasValidSecretMessage(String yourSecretMessage) throws IllegalCastException {
        String mySecretMessage = cacheService.getValue(String.class, AppConstant.HEADER_X_SECRET_MESSAGE);
        return StringUtility.isNotEmpty(mySecretMessage) && mySecretMessage.equals(yourSecretMessage);
    }

    private boolean isTrustedClient(HttpServletRequest request) {
            String clientAddress = request.getHeader("X-FORWARDED-FOR");
            if (StringUtility.isEmpty(clientAddress)) {
                clientAddress = request.getRemoteAddr();
            }
            if (StringUtility.isEmpty(clientAddress)) {
                return false;
            }
            String[] allowIPs = appConfig.getTrustedClients().split(",");
            for (String allowIP : allowIPs) {
                if (allowIP.equals(clientAddress)) {
                    return true;
                }
                String[] ips = allowIP.split("-");
                if (ips.length == 2) {
                    // https://gist.github.com/madan712/6651967
                    // https://stackoverflow.com/a/4256603/2463794
                    try {
                        long ipLo = ipToLong(InetAddress.getByName(ips[0]));
                        long ipHi = ipToLong(InetAddress.getByName(ips[1]));
                        long ipToTest = ipToLong(InetAddress.getByName(clientAddress));
                        return (ipToTest >= ipLo && ipToTest <= ipHi);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
            return appConfig.getTrustedClients().contains(clientAddress);
        }

        private Long ipToLong(InetAddress ip) {
            byte[] octets = ip.getAddress();
            long result = 0;
            for (byte octet : octets) {
                result <<= 8;
                result |= octet & 0xff;
            }
            return result;
        }
}
