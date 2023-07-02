package com.hellparty.config;

import com.hellparty.jwt.JwtProvider;
import com.hellparty.oauth.CustomSuccessHandler;
import com.hellparty.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */

@Configuration
@AllArgsConstructor
public class SecurityConfig{

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private final JwtProvider jwtProvider;

    // HttpServletRequest에 대한 필터체인을 정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/api/login/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/login.html")).permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable() // frameOptions은 기본 Deny. h2는 iframe을 사용하기에 disable 처리
                .and().csrf().disable();// h2 UI를 통해 DB connect 시 post 통신이 발생하면서 csrf 에러가 발생. 이에따라 disable 처리
        http
                .oauth2Login()
                .successHandler(new CustomSuccessHandler(jwtProvider));

        return http.build();
    }

}
