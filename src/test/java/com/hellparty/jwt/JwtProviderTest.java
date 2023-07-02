package com.hellparty.jwt;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class JwtProviderTest {

    private final String secretKey = "123456789012345678901234567890";
    JwtProvider jwtProvider = new JwtProvider(secretKey);

    private final static String EMAIL = "test@naver.com";
    private final static String VALID_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
            ".eyJzdWIiOiJyZWZyZXNoVG9rZW4iLCJpZCI6MSwiaWF0IjoxNjg4MjI4NzgwLCJleHAiOjIyOTMwMjg3ODB9" +
            ".I9GzqNott_LJE7xIhhJ4ZuoARNHwoDyAgiUZ8tgVGc4";

    private final static String INVALID_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
            ".eyJzdWIiOiJyZWZyZXNoVG9rZW4iLCJpZCI6MSwiaWF0IjoxNjg4MjI4NzgwLCJleHAiOjIyOTMwMjg3ODB9" +
            ".I9GzqNott_LJE7xIhhJ4ZuoARNHwoDyAgiUZ8tgVGc4_INVALID";

    @Test
    void generateAccessToken() {

        String token = jwtProvider.generateAccessToken(EMAIL);
        assertThat(token).contains("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9");

    }

    @Test
    void generateRefreshToken() {

        String token = jwtProvider.generateRefreshToken(EMAIL);
        assertThat(token).contains("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9");

    }
    @Test
    void parseJwtTokenWithValidToken() {

        assertThat(jwtProvider.parseJwtToken(VALID_ACCESS_TOKEN))
                .isNotNull();
    }

    @Test
    void parseJwtTokenWithInvalidToken() {

        assertThatThrownBy(()-> jwtProvider.parseJwtToken(INVALID_ACCESS_TOKEN))
                .isInstanceOf(JwtException.class);

    }
}