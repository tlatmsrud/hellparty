package com.hellparty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-07
 * description  :
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequestDTO {

    private Long toMemberId;
}
