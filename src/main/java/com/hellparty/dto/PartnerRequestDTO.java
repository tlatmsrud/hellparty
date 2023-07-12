package com.hellparty.dto;

import com.hellparty.enums.PartnerResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * title        : 파트너 요청 DTO
 * author       : sim
 * date         : 2023-07-07
 * description  : 파트너 요청 관련 DTO
 */


@Getter
public class PartnerRequestDTO {

    @Getter
    @AllArgsConstructor
    public static class History{
        private Long requestId;
        private PartnerResponseStatus status;
        private Long memberId;
        private String nickname;
        private String profileUrl;

    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Answer{
        private Long id;
        private PartnerResponseStatus status;

    }
}
