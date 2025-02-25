package com.example.test.config;

import java.sql.Timestamp;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestLoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // 요청이 들어올 때 로그 출력
        System.out.println("************");
        System.out.println("요청이 들어왔습니다: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("요청 시간 >> " + timestamp);
        System.out.println("************" + '\n');

        return true; // 요청을 계속 처리하도록 허용
    }

}
