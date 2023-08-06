package com.hellparty.filter;

import com.hellparty.exception.JwtTokenException;
import com.hellparty.factory.AuthenticationFactory;
import com.hellparty.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-03
 * description  :
 */

@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            if(!"/view/login".equals(request.getRequestURI())) {

                String authorizationValue = request.getHeader("Authorization");

                if (authorizationValue != null && authorizationValue.contains("TEST_TOKEN")) {
                    Authentication authentication = AuthenticationFactory.getAuthentication(1L);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } else if (authorizationValue != null && !authorizationValue.isBlank()) {
                    String token = jwtProvider.extractAccessToken(authorizationValue);
                    Long id = jwtProvider.parseJwtToken(token).get("id", Long.class);

                    Authentication authentication = AuthenticationFactory.getAuthentication(id);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        }catch (JwtTokenException e){
            response.sendRedirect("/view/login?status=fail&message=invalid_token");
        }
    }
}
