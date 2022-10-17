package com.evowork.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author tuannd
 * @date 12/07/2016
 * @since 1.0
 */
public class LoggingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Enumeration<String> headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            System.out.println(String.format("%s: %s", header, req.getHeader(header)));
        }
        System.out.println("X-FORWARDED-FOR: " + ((HttpServletRequest) request).getHeader("X-FORWARDED-FOR"));
        System.out.println("Remote host: " + request.getRemoteAddr());
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
