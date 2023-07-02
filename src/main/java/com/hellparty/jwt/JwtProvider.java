package com.hellparty.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
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

    public JwtProvider(@Value("${jwt.secret-key}") String secretKey){
        byte[] keyBytes = Base64.getEncoder().encode(secretKey.getBytes());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private final Key key;

    @Value("${jwt.access-token-subject}")
    private String accessTokenSubject;

    @Value("${jwt.refresh-token-subject}")
    private String refreshTokenSubject;
    private final static Long ONE_HOUR = 1000*60*60L;

    private final static Long TEN_DAY = 1000*60*60*24*7L;

    /**
     * 액세스 토큰 생성
     * @param id 사용자 ID
     * @return accessToken
     */
    public String generateAccessToken(Long id){

        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setSubject(accessTokenSubject)
                .claim("id", id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ONE_HOUR))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 리프레시 토큰 생성
     * @param id - 사용자 ID
     * @return refreshToken
     */
    public String generateRefreshToken(Long id){

        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setSubject(refreshTokenSubject)
                .claim("id", id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+TEN_DAY))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Jwt 토큰 파싱
     * @param token - JWT 토큰
     * @return Claims
     */
    public Claims parseJwtToken(String token){
        try {

            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody();

        } catch (ExpiredJwtException e) {
            throw new JwtException("토큰이 만료되었습니다. 다시 로그인하세요.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("지원하지 않는 토큰입니다. 다시 로그인하세요.");
        } catch (MalformedJwtException e) {
            throw new JwtException("잘못된 형식의 토큰입니다. 다시 로그인하세요.");
        } catch (SignatureException e) {
            throw new JwtException("위조된 요청입니다. 다시 로그인하세요.");
        } catch (IllegalArgumentException e) {
            throw new JwtException("토큰 파싱 중 에러가 발생했습니다. 관리자에게 문의해주세요.");
        }
    }
}
