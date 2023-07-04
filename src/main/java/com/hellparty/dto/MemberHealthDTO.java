package com.hellparty.dto;

import com.hellparty.domain.embedded.Address;
import com.hellparty.domain.embedded.BigThree;
import com.hellparty.enums.Division;
import lombok.Builder;
import lombok.Getter;

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

    private Long id;
    private Time execStartTime;
    private Time execEndTime;
    private Division div;
    private Long execArea;
    private Address gymAddress;
    private String spclNote;
    private BigThree bigThree;
    private String healthMotto;

}
