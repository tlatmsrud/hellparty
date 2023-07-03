package com.hellparty.oauth;

import com.hellparty.domain.Member;
import com.hellparty.enums.ExecStatus;
import com.hellparty.enums.OAuthType;
import lombok.Getter;

import java.util.Map;

/**
 * title        : OAuth2 속성 클래스
 * author       : sim
 * date         : 2023-06-30
 * description  : OAuth2를 통해 조회한 사용자 정보를 관리하는 클래스.
 *                OAuth2 타입별로 상이한 조회 값들을 하나의 클래스로 통합관리하기 위함.
 */
@Getter
public class OAuth2Attributes {

    private final Map<String, Object> attributes;
    private final String attributesKey;
    private final String nickname;
    private final String email;
    private final String profileUrl;

    public OAuth2Attributes(Map<String, Object> attributes, String attributesKey, String nickname, String email, String profileUrl) {
        this.attributes = attributes;
        this.attributesKey = attributesKey;
        this.nickname = nickname;
        this.email = email;
        this.profileUrl = profileUrl;
    }

    public static OAuth2Attributes of(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equals(OAuthType.KAKAO.getValue())) {
            return ofKakao("email", attributes);
        }
        return ofNaver("email",attributes);
    }

    private static OAuth2Attributes ofKakao(String attributesKey, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, String> profile = (Map<String, String>) kakaoAccount.get("profile");

        return new OAuth2Attributes(kakaoAccount,
                attributesKey,
                profile.get("nickname"),
                (String) kakaoAccount.get("email"),
                profile.get("profile_image_url")
        );
    }

    private static OAuth2Attributes ofNaver(String attributesKey, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");    // 네이버에서 받은 데이터에서 프로필 정보다 담긴 response 값을 꺼낸다.

        return new OAuth2Attributes(response,
                attributesKey,
                (String)response.get("nickname"),
                (String)response.get("email"),
                (String)response.get("profile_image"));
    }

    public Member toMemberEntity(){
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .profileUrl(profileUrl)
                .status(ExecStatus.W)
                .build();
    }
}
