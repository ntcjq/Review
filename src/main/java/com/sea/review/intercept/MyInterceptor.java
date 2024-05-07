package com.sea.review.intercept;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        if ("POST".equals(requestWrapper.getMethod())) {
            log.info("preHandle requestURI = {},requestBody={}", requestWrapper.getRequestURI(), requestWrapper.getBody());
        }
        return true;
    }

}
