package com.hellparty.jwt;

import attributes.TestFixture;
import com.hellparty.exception.JwtTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class JwtProviderTest implements TestFixture {

    JwtProvider jwtProvider = new JwtProvider(JWT_SECRET_KEY);

    @Test
    @DisplayName("엑세스 토큰 생성")
    void generateAccessToken() {

        String token = jwtProvider.generateAccessToken(CLAIMS);
        assertThat(token).contains("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9");

    }

    @Test
    @DisplayName("리프레시 토큰 생성")
    void generateRefreshToken() {

        String token = jwtProvider.generateRefreshToken(CLAIMS);
        assertThat(token).contains("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9");

    }
    @Test
    @DisplayName("유효한 엑세스 토큰 파싱")
    void parseJwtTokenWithValidToken() {

        assertThat(jwtProvider.parseJwtToken(VALID_ACCESS_TOKEN))
                .isNotNull();
    }

    @Test
    @DisplayName("유효하지 않은 엑세스 토큰 파싱")
    void parseJwtTokenWithInvalidToken() {

        assertThatThrownBy(()-> jwtProvider.parseJwtToken(INVALID_ACCESS_TOKEN))
                .isInstanceOf(JwtTokenException.class);

    }

    @Test
    @DisplayName("유효한 Authorization 헤더의 엑세스 토큰 추출")
    void extractAccessTokenWithValidAuthorization(){
        assertThat(jwtProvider.extractAccessToken(VALID_AUTHORIZATION))
                .isEqualTo(VALID_ACCESS_TOKEN);
    }

    @ParameterizedTest
    @ValueSource(strings = {INVALID_AUTHORIZATION, "", VALID_ACCESS_TOKEN})
    @DisplayName("유효하지 않은 Authorization 헤더의 엑세스 토큰 추출")
    void extractAccessTokenWithInvalidAuthorization(String authorization){
        assertThatThrownBy(() -> jwtProvider.extractAccessToken(authorization))
                .isInstanceOf(JwtTokenException.class);

    }

    @Test
    @DisplayName("클레임 생성")
    void generateClaims(){
        Map<String, Object> claims = jwtProvider.generateClaims(VALID_MEMBER_ENTITY);
        assertThat(claims.get("id")).isEqualTo(VALID_MEMBER_ENTITY.getId());
        assertThat(claims.get("email")).isEqualTo(VALID_MEMBER_ENTITY.getEmail());
        assertThat(claims.get("nickname")).isEqualTo(VALID_MEMBER_ENTITY.getNickname());
    }
}