package com.hellparty.dto;

import lombok.Getter;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-19
 * description  :
 */

@Getter
public class ChatDTO {
    private Long roomId;
    private Long writerID;
    private String chat;
}
