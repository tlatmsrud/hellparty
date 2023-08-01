package com.hellparty.enums;

import lombok.Getter;

/**
 * title        : Extension
 * author       : sim
 * date         : 2023-08-01
 * description  : 확장자에 대한 enum 클래스
 */

@Getter
public enum Extension {

    PNG("png", "image/png")
    , JPEG("jpeg", "image/jpeg")
    , JPG("jpg", "image/jpg");

    private final String extension;
    private final String contentType;
    Extension(String extension, String contentType){
        this.extension = extension;
        this.contentType = contentType;
    }

    public boolean isEqualToExtension(String extension){
        return this.extension.equals(extension.toLowerCase());
    }
}
