package com.hellparty.dto;

import com.hellparty.domain.embedded.Address;
import com.hellparty.domain.embedded.BigThree;
import com.hellparty.enums.Division;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-04
 * description  :
 */

@Builder
@Getter
public class MemberHealthDTO {

    private Time execStartTime;
    private Time execEndTime;
    private Division div;
    private Long execArea;
    private Address gymAddress;
    private String spclNote;
    private BigThree bigThree;
    private String healthMotto;


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update{
        private Time execStartTime;
        private Time execEndTime;
        private Division div;
        private Long execArea;
        private Address gymAddress;
        private String spclNote;
        private BigThree bigThree;
        private String healthMotto;
        private ExecDayDTO execDay;
    }

}
