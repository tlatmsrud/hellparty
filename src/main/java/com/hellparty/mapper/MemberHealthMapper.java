package com.hellparty.mapper;

import com.hellparty.domain.MemberHealth;
import com.hellparty.dto.MemberHealthDTO;
/**
 * title        :
 * author       : sim
 * date         : 2023-07-04
 * description  :
 */
public class MemberHealthMapper {

    public static MemberHealthDTO entityToDTO(MemberHealth memberHealth){

        return MemberHealthDTO.builder()
                .id(memberHealth.getId())
                .execStartTime(memberHealth.getExecStartTime())
                .execEndTime(memberHealth.getExecEndTime())
                .div(memberHealth.getDiv())
                .execArea(memberHealth.getExecArea())
                .gymAddress(memberHealth.getGymAddress())
                .spclNote(memberHealth.getSpclNote())
                .bigThree(memberHealth.getBigThree())
                .healthMotto(memberHealth.getHealthMotto())
                .build();

    }
}
