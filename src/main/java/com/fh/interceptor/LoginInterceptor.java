package com.fh.interceptor;


import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object member = request.getSession().getAttribute("member");
        if(member ==null){
            response.sendRedirect("/login.jsp");
            return  false  ;
        }

        return true;
    }
}
