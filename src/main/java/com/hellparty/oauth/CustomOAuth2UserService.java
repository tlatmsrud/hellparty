package com.hellparty.oauth;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * title        : OAuth2UserService
 * author       : sim
 * date         : 2023-06-30
 * description  : OAuth2 AuthorizationCode 발급, AccessToken 발급, user 정보 조회 후 최종적으로 로드되는 서비스
 */

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

        // registrationId는 서비스 Owner를 가리킴 (naver. kakao)
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2 로그인을 통해 가져온 OAuth2User의 attribute를 담아주는 of 메소드.
        OAuth2Attributes attributes = OAuth2Attributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
                , attributes.getAttributes()
                , userNameAttributeName);
    }
}
