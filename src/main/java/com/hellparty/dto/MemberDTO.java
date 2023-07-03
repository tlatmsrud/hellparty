package com.hellparty.dto;

import com.hellparty.enums.MBTI;
import com.hellparty.enums.Sex;
import lombok.Builder;
import lombok.Getter;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */
@Getter
@Builder
public class MemberDTO {

    private Long id;
    private String nickname;
    private String email;
    private String profileUrl;
    private String bodyProfileUrl;
    private int age;
    private double height;
    private double weight;
    private MBTI mbti;
    private Sex sex;

    @Getter
    @Builder
    public static class Update{
        private Long id;
        private String nickname;
        private String email;
        private String profileUrl;
        private String bodyProfileUrl;
        private int age;
        private double height;
        private double weight;
        private MBTI mbti;
        private Sex sex;
    }



}
