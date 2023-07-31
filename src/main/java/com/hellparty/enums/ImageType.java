package com.hellparty.enums;

import lombok.Getter;

/**
 * title        : ImageType
 * author       : sim
 * date         : 2023-08-01
 * description  : 이미지 타입에 대한 Enum
 */

@Getter
public enum ImageType {

    PROFILE("/profile"), BODY_PROFILE("/body_profile");

    private String path;

    ImageType(String path){
        this.path = path;
    }
}
