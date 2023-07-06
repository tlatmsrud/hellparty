package com.hellparty.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-06
 * description  :
 */
public class PartnerDTO {

    @Getter
    @AllArgsConstructor
    public static class Request{
        private Long partnerId;
    }
}
