package com.hellparty.config;

import com.hellparty.filter.JwtAuthorizationFilter;
import com.hellparty.jwt.JwtProvider;
import com.hellparty.security.CustomSuccessHandler;
import com.hellparty.repository.MemberRepository;
import com.hellparty.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * title        : SpringSecurity 설정
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */

@Configuration
@AllArgsConstructor
public class SecurityConfig{

    private final JwtProvider jwtProvider;

    private final TokenService tokenService;

    private final MemberRepository memberRepository;

    // HttpServletRequest에 대한 필터체인을 정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/view/login").permitAll()
                        .requestMatchers("/api/token/**").permitAll()
                        .anyRequest().authenticated()
                )
                .headers().frameOptions().disable() // frameOptions은 기본 Deny. h2는 iframe을 사용하기에 disable 처리
                .and()
                .csrf().disable()// h2 UI를 통해 DB connect 시 post 통신이 발생하면서 csrf 에러가 발생. 이에따라 disable 처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 인증으로 인한 Session 미생성.
                .and()
                .oauth2Login()
                .loginPage("/view/login")
                .successHandler(new CustomSuccessHandler(jwtProvider, tokenService, memberRepository))
                .and()
                .addFilterBefore(new JwtAuthorizationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
