package com.hellparty.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * title        : Extension
 * author       : sim
 * date         : 2023-08-01
 * description  : 확장자에 대한 enum 클래스
 */

@Getter
public enum Extension {

    PNG("PNG", "image/png")
    , JPEG("JPEG", "image/jpeg")
    , JPG("JPG", "image/jpg");

    private final String ext;
    private final String contentType;
    Extension(String ext, String contentType){
        this.ext = ext;
        this.contentType = contentType;
    }

    public static boolean isExtension(String ext) {
        return Arrays.stream(values())
                .anyMatch(value -> value.ext.equals(ext.toUpperCase()));
    }
}
