package com.hellparty.service;

import com.hellparty.dto.TokenDTO;
import com.hellparty.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    private final RedisTemplate<String,String> redisTemplate;

    /**
     * 리프레시 토큰 갱신
     * @param refreshToken - 리프레시 토큰
     * @return 갱신된 리프레시 토큰
     */
    public TokenDTO renewToken(String refreshToken){

        Claims claims = jwtProvider.parseJwtToken(refreshToken);
        // TODO token claim 정책 수립
        Long memberId = Long.valueOf(claims.get("id"));
        String renewAccessToken = jwtProvider.generateAccessToken(memberId);
        String renewRefreshToken = jwtProvider.generateRefreshToken(memberId);

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
        redisTemplate.opsForValue().set(memberId.toString(), refreshToken,7, TimeUnit.DAYS);
    }
}
