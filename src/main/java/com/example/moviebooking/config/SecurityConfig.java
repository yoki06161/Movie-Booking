package com.example.moviebooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 스프링 시큐리티 설정
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Swagger URL 허용 (SpringDoc 기준)
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
                ).permitAll()

                // 회원가입/로그인은 인증 없이 접근
                .requestMatchers("/api/v1/auth/**").permitAll()

                // 나머지는 인증 필요
                .anyRequest().authenticated()
            )
            
            // (옵션) 폼 로그인 기본 설정 - 로컬 확인용
            .formLogin(Customizer.withDefaults())
            .build();
    }
}
