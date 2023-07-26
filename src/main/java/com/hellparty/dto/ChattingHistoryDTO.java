package com.hellparty.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * title        : 채팅 내역 Dto
 * author       : sim
 * date         : 2023-07-26
 * description  : 채팅 내역에 대한 Dto 클래스
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChattingHistoryDTO {

    private Long memberId;

    private String message;

    private LocalDateTime time;
}
