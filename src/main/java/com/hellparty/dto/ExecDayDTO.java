package com.hellparty.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * title        : 사용자 운동 요일 DTO 클래스
 * author       : sim
 * date         : 2023-07-04
 * description  : 사용자의 운동 요일을 관리하는 DTO 클래스
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExecDayDTO {

    private boolean sun;
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
}
