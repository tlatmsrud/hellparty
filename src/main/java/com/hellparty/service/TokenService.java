package com.hellparty.service;

import com.hellparty.dto.TokenDTO;
import com.hellparty.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

        String email = jwtProvider.parseJwtToken(refreshToken)
                .get("email",String.class);

        String renewAccessToken = jwtProvider.generateAccessToken(email);
        String renewRefreshToken = jwtProvider.generateRefreshToken(email);

        return TokenDTO.builder()
                .accessToken(renewAccessToken)
                .refreshToken(renewRefreshToken)
                .build();
    }
}
