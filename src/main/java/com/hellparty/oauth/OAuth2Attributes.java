package com.hellparty.oauth;

import com.hellparty.domain.Member;
import com.hellparty.enums.ExecStatus;
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
    private String nickname;
    private String email;
    private String profileImage;

    public OAuth2Attributes(Map<String, Object> attributes, String nickname, String email, String profileImage) {
        this.attributes = attributes;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
    }

    public static OAuth2Attributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals(OAuthType.KAKAO.getValue())) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofNaver(userNameAttributeName,attributes);
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get(userNameAttributeName);
        Map<String, String> profile = (Map<String, String>) kakaoAccount.get("profile");

        return new OAuth2Attributes(attributes,
                profile.get("nickname"),
                (String) kakaoAccount.get("email"),
                profile.get("profile_image_url"));
    }

    private static OAuth2Attributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, String> response = (Map<String, String>) attributes.get(userNameAttributeName);    // 네이버에서 받은 데이터에서 프로필 정보다 담긴 response 값을 꺼낸다.

        return new OAuth2Attributes(attributes,
                response.get("nickname"),
                response.get("email"),
                response.get("profile_image"));
    }

    public Member toMemberEntity(){
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .profileUrl(profileImage)
                .status(ExecStatus.W)
                .build();
    }
}
