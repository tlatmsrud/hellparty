package com.hellparty.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesRegistrationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */

@Configuration
public class SecurityConfig{

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // HttpServletRequest에 대한 필터체인을 정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/api/login/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable() // frameOptions은 기본 Deny. h2는 iframe을 사용하기에 disable 처리
                .and().csrf().disable();// h2 UI를 통해 DB connect 시 post 통신이 발생하면서 csrf 에러가 발생. 이에따라 disable 처리
        http
                .oauth2Login();

        return http.build();
    }

}
