package com.hellparty.dto;

import com.hellparty.enums.ExecStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-06
 * description  :
 */

@Getter
@AllArgsConstructor
@Builder
public class PartnerDTO {

    private Long id;
    private String nickname;
    private ExecStatus execStatus;

}
