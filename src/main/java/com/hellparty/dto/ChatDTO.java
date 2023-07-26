package com.hellparty.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-19
 * description  :
 */

@Getter
@AllArgsConstructor
public class ChatDTO implements Serializable {
    private Long roomId;

    private Long writerId;

    private String message;
    private LocalDateTime time = LocalDateTime.now();
}
