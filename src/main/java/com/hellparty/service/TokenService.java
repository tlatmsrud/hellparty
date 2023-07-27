package com.hellparty.service;

import com.hellparty.dto.TokenDTO;
import com.hellparty.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
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

    private final RedisService redisService;


    /**
     * 리프레시 토큰 갱신
     * @param refreshToken - 리프레시 토큰
     * @return 갱신된 리프레시 토큰
     */
    public TokenDTO renewToken(String refreshToken){

        Claims claims = jwtProvider.parseJwtToken(refreshToken);

        String renewAccessToken = jwtProvider.generateAccessToken(claims);
        String renewRefreshToken = jwtProvider.generateRefreshToken(claims);

        Long memberId = claims.get("id", Long.class);
        saveRefreshToken(memberId, renewRefreshToken);

        return TokenDTO.builder()
                .accessToken(renewAccessToken)
                .refreshToken(renewRefreshToken)
                .build();
    }

    /**
     * 리프레시 토큰 레디스 저장
     * @param memberId - 사용자 ID (key)
     * @param refreshToken - 리프레시 토큰 (value)
     */
    public void saveRefreshToken(Long memberId, String refreshToken){
        redisService.saveRefreshToken(memberId.toString(), refreshToken);
    }
}
