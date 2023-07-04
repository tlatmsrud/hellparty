package com.hellparty.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */
public enum MBTI {
    ISTJ, ISFJ, INFJ, INTJ,  ISTP, ISFP, INFP, INTP, ESTP, ESFP, ENFP, ENTP, ESTJ, ESFJ, ENFJ, ENTJ;

    @JsonCreator
    public static MBTI from(String mbti){
        return MBTI.valueOf(mbti.toUpperCase());
    }
}
