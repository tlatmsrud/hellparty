package com.hellparty.dto;

import com.hellparty.enums.MBTI;
import com.hellparty.enums.Sex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    private int birthYear;
    private double height;
    private double weight;
    private MBTI mbti;
    private Sex sex;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update{

        @NotBlank(message = "닉네임을 입력해주세요.")
        private String nickname;
        private String profileUrl;
        private String bodyProfileUrl;
        @NotNull(message = "출생년도를 입력해주세요.")
        private int birthYear;
        private double height;
        private double weight;
        private MBTI mbti;
        @NotNull(message = "성별을 입력해주세요.")
        private Sex sex;
    }



}
