package com.hellparty.filter;

import com.hellparty.factory.AuthenticationFactory;
import com.hellparty.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-03
 * description  :
 */

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authorizationValue = ((HttpServletRequest)request).getHeader("Authorization");

        if(authorizationValue != null && !authorizationValue.isBlank()){
            String token = jwtProvider.extractAccessToken(authorizationValue);
            Claims claims = jwtProvider.parseJwtToken(token);
            String email  = claims.get("email", String.class);
            Authentication authentication = AuthenticationFactory.getAuthentication(email);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

}
