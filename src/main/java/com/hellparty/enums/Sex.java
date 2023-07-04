package com.hellparty.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */
public enum Sex {

    M,W;

    @JsonCreator
    public static Sex from(String sex){
        return Sex.valueOf(sex.toUpperCase());
    }
}
