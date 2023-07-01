package com.hellparty.jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;

/**
 * title        : Jwt 토큰 제공클래스
 * author       : sim
 * date         : 2023-07-02
 * description  : JWT 토큰에 대한 생성, 파싱, 검증기능을 수행
 */

@Component
public class JwtProvider {

    private byte[] secretKey = Base64.getEncoder().encode("123456789012345678901234567890".getBytes());

    private String accessTokenSubject = "accessToken";

    private String refreshTokenSubject = "refreshToken";
    private final static Long ONE_HOUR = 1000*60*60L;

    private final static Long TEN_DAY = 1000*60*60*24*7L;

    /**
     * 액세스 토큰 생성
     * @param id
     * @return accessToken
     */
    public String generateAccessToken(Long id){

        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setSubject(accessTokenSubject)
                .claim("id", id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ONE_HOUR))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 리프레시 토큰 생성
     * @param id
     * @return refreshToken
     */
    public String generateRefreshToken(Long id){

        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setSubject(refreshTokenSubject)
                .claim("id", id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+TEN_DAY))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Jwt 토큰 파싱
     * @param token
     * @return Claims
     */
    public Claims parseJwtToken(String token){
        try {

            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token).getBody();

        } catch (ExpiredJwtException e) {
            throw new JwtException("토큰이 만료되었습니다. 다시 로그인하세요.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("잘못된 형식의 토큰입니다. 다시 로그인하세요.");
        } catch (MalformedJwtException e) {
            throw new JwtException("잘못된 형식의 토큰입니다. 다시 로그인하세요.");
        } catch (SignatureException e) {
            throw new JwtException("위조된 요청입니다. 다시 로그인하세요.");
        } catch (IllegalArgumentException e) {
            throw new JwtException("토큰 파싱 중 에러가 발생했습니다. 관리자에게 문의해주세요.");
        }
    }
}
