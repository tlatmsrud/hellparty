package com.hellparty.dto;

import lombok.Getter;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */

@Getter
public class ErrorResponseDTO {

    private String errMessage;

    public ErrorResponseDTO(String errMessage){
        this.errMessage = errMessage;
    }

}
