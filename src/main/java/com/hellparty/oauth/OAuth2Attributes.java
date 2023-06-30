package com.hellparty.oauth;

import com.hellparty.enums.OAuthType;
import lombok.Getter;

import java.util.Map;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */
@Getter
public class OAuth2Attributes {

    private Map<String, Object> attributes;
    private String name;
    private String email;
    private String profileImage;

    public OAuth2Attributes(Map<String, Object> attributes, String name, String email, String profileImage) {
        this.attributes = attributes;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
    }


    // 해당 로그인인 서비스가 kakao인지 google인지 구분하여, 알맞게 매핑을 해주도록 합니다.
    // 여기서 registrationId는 OAuth2 로그인을 처리한 서비스 명("google","kakao","naver"..)이 되고,
    // userNameAttributeName은 해당 서비스의 map의 키값이 되는 값이됩니다. {google="sub", kakao="id", naver="response"}
    public static OAuth2Attributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals(OAuthType.KAKAO.getValue())) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofNaver(userNameAttributeName,attributes);
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");  // 카카오로 받은 데이터에서 계정 정보가 담긴 kakao_account 값을 꺼낸다.
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");   // 마찬가지로 profile(nickname, image_url.. 등) 정보가 담긴 값을 꺼낸다.

        return new OAuth2Attributes(attributes,
                (String) profile.get("nickname"),
                (String) kakao_account.get("email"),
                (String) profile.get("profile_image_url"));
    }

    private static OAuth2Attributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, String> response = (Map<String, String>) attributes.get(userNameAttributeName);    // 네이버에서 받은 데이터에서 프로필 정보다 담긴 response 값을 꺼낸다.

        return new OAuth2Attributes(attributes,
                response.get("name"),
                response.get("email"),
                response.get("profile_image"));
    }
}
