package com.sea.review.intercept;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        String requestURI = request.getRequestURI();
        if ("POST".equals(requestWrapper.getMethod())) {
            System.out.println("=========== " + requestURI + " body= " + requestWrapper.getBody());
        }
        return true;
    }

}
