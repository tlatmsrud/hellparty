package com.hellparty.dto;

import com.hellparty.enums.MBTI;
import com.hellparty.enums.Sex;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */
public class MemberDTO {

    @Getter
    @Builder
    public static class Update{
        private Long id;
        private String nickname;
        private String email;
        private double height;
        private double weight;
        private int age;
        private Sex sex;
        private MBTI mbti;
        private String profileUrl;
    }



}
