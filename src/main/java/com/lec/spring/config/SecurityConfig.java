package com.lec.spring.config;

import com.lec.spring.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // security 중단
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return web -> web.ignoring().anyRequest();
//    }

    @Bean
    public PasswordEncoder encoder(){
        System.out.println("PasswordEncoder 생성");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())

                // url 접근권한 세팅
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/leisure/write/**", "/mypage/company/**", "/leisure/delete/**").hasAnyRole("COMPANY", "ADMIN")
                        .requestMatchers("/company/write/**", "/mypage/member").hasAnyRole("USER", "COMPANY", "ADMIN")
                        .requestMatchers("/reserve/write/**", "/reserve/update/**", "/reserve/detail/**", "/reserve/delete/**").hasAnyRole("USER", "COMPANY", "ADMIN")
                        .requestMatchers("/review/write/**", "/review/update/**", "/review/delete/**").hasAnyRole("USER", "COMPANY", "ADMIN")
                        .requestMatchers("/qna/write/**", "/qna/update/**", "/qna/delete/**").hasAnyRole("USER", "COMPANY", "ADMIN")
                        .anyRequest().permitAll()   //나머지 url은 모두 허용
                )

                // 로그인설정
                .formLogin(form -> form
                        .loginPage("/user/login")   // 로그인(인증이) 필요한 상황 발생 시 로그인 폼으로 request 발생
                        .loginProcessingUrl("/user/login")  // POST request 가 들어오면 시큐리티가 낚아챔, 대신 로그인 진행해줌
                        .defaultSuccessUrl("/")

                        // 로그인 성공직후 수행
                        .successHandler(new CustomLoginSuccessHandler("/home"))
                        // 로그인 실패하면 수행할 코드
                        .failureHandler(new CustomLoginFailureHandler())
                )

                //로그아웃 설정
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/user/logout")  // 로그아웃을 수행하는 url
                        .invalidateHttpSession(false)
                        // 로그아웃 성공직후 수행
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                )

                // 예외처리
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler(new CustomAccessDeniedHandler())   // 권한오류
                )
                .build();
    }
}
