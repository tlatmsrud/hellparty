package com.hellparty.factory;

import com.hellparty.domain.Member;
import com.hellparty.domain.PartnerRequest;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-07
 * description  :
 */
public class PartnerRequestFactory {

    public static PartnerRequest createEntity(Member fromMember, Member toMember){
        return PartnerRequest.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .build();
    }
}
