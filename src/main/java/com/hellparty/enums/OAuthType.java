package com.hellparty.enums;

import lombok.Getter;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */

@Getter
public enum OAuthType {
    KAKAO("kakao")
    , NAVER("naver");

    private String value;

    OAuthType(String value){
        this.value = value;
    }
}
