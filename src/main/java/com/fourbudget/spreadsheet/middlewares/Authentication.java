package com.fourbudget.spreadsheet.middlewares;

import com.fourbudget.spreadsheet.annotations.NoAuth;
import com.fourbudget.spreadsheet.gateway.GoogleGateway;
import com.fourbudget.spreadsheet.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Authentication implements HandlerInterceptor {

    @Autowired
    private GoogleGateway googleGateway;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        final NoAuth noAuth = ((HandlerMethod)handler).getMethod().getAnnotation((NoAuth.class));

        if(noAuth != null){
            return true;
        }

        UserInfo userInfo = googleGateway.autenticar(token);

        System.out.println(userInfo);
        return true;
    }
}