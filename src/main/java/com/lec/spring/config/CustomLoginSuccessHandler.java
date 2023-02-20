package com.lec.spring.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public CustomLoginSuccessHandler(String defaultTargetUrl){
        setDefaultTargetUrl(defaultTargetUrl);
    }

    // 로그인 성공 직후 수행할 동작
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        System.out.println("로그인 성공: onAuthenticationSuccess() 호출");

        PrincipalDetails userDetails = (PrincipalDetails)authentication.getPrincipal();
        System.out.println("ID: " + userDetails.getUsername());
        System.out.println("password: " + userDetails.getPassword());
        List<String> roleNames = new ArrayList<>();   // 권한이름들
        authentication.getAuthorities().forEach(authority -> {
            roleNames.add(authority.getAuthority());
        });
        System.out.println("authorities: " + roleNames);

        // 로그인 시간을 세션에 저장하기
        LocalDateTime loginTime = LocalDateTime.now();
        System.out.println("로그인 시간: " + loginTime);
        request.getSession().setAttribute("loginTime", loginTime);
        System.out.println();

        // 로그인 직전 url 로 redirect 하기
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
