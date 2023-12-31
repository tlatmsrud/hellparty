package com.hellparty.dto;

import com.hellparty.enums.Division;
import com.hellparty.enums.MBTI;
import com.hellparty.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

/**
 * title        : 사용자 검색 DTO
 * author       : sim
 * date         : 2023-07-29
 * description  : 사용자 검색 DTO 클래스
 */
public class SearchMemberDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Request{
        private Integer fromAge;
        private Integer toAge;
        private Sex sex;
        private MBTI mbti;
        private Time execStartTime;
        private Time execEndTime;
        private Long execArea;
        private ExecDayDTO execDay;
    }

    @Getter
    @AllArgsConstructor
    public static class Summary{
        private Long memberId;
        private String nickname;
        private int birthYear;
        private Sex sex;
        private String profileUrl;
        private String bodyProfileUrl;
        private ExecDayDTO execDay;
        private Time execStartTime;
        private Time execEndTime;

    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Detail{
        private Long memberId;
        private String nickname;
        private int birthYear;
        private double height;
        private double weight;
        private MBTI mbti;
        private Sex sex;
        private ExecDayDTO execDay;
        private Time execStartTime;
        private Time execEndTime;
        private Division div;
        private Long execArea;
        private String placeName;
        private String address;
        private Long x;
        private Long y;
        private String spclNote;
        private double benchPress;
        private double squat;
        private double deadlift;
        private String healthMotto;
    }
}
