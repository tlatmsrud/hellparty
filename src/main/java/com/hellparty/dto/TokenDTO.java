package com.hellparty.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-03
 * description  :
 */

@Builder
@Getter
public class TokenDTO {

    private String accessToken;
    private String refreshToken;

}
