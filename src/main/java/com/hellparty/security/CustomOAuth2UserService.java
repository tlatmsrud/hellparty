package com.hellparty.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.enums.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * title        : OAuth2UserService
 * author       : sim
 * date         : 2023-06-30
 * description  : OAuth2 AuthorizationCode 발급, AccessToken 발급 후 유저 정보를 조회하기 위해 호출되는 메서드
 */

@Service
@AllArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final ObjectMapper objectMapper;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        log.info("success to get oauth2 authentication");
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest); // 유저 정보 조회

        log.info("success to get oauth2 userInfo : " + oAuth2User.getName());
        // registrationId는 서비스 Owner를 가리킴 (naver. kakao)
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인을 통해 가져온 OAuth2User의 attribute를 담아주는 of 메소드.
        OAuth2Attributes attributes = OAuth2Attributes.of(registrationId, oAuth2User.getAttributes());

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.name()))
                , objectMapper.convertValue(attributes, Map.class)
                , "email");
    }
}
