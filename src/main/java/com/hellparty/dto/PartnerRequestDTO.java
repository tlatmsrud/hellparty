package com.hellparty.dto;

import com.hellparty.enums.PartnerResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * title        : 파트너 요청 DTO
 * author       : sim
 * date         : 2023-07-07
 * description  : 파트너 요청 관련 DTO
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PartnerRequestDTO {
    private Long id;
    private MemberDTO member;
    private PartnerResponseStatus status;

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Answer{
        private Long id;
        private PartnerResponseStatus status;

    }
}
