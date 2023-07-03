package com.hellparty.service;

import com.hellparty.dto.TokenDTO;
import com.hellparty.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * title        : 토큰 서비스
 * author       : sim
 * date         : 2023-07-02
 * description  : 토큰 서비스
 */

@Service
@AllArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;

    public TokenDTO renewToken(String refreshToken){

        Map<String, Object> claims = jwtProvider.parseJwtToken(refreshToken);

        String renewAccessToken = jwtProvider.generateAccessToken(claims);
        String renewRefreshToken = jwtProvider.generateRefreshToken(claims);

        return TokenDTO.builder()
                .accessToken(renewAccessToken)
                .refreshToken(renewRefreshToken)
                .build();
    }
}
