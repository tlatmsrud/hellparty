package com.hellparty.factory;

import com.hellparty.domain.Member;
import com.hellparty.domain.PartnerRequest;
import com.hellparty.enums.PartnerResponseStatus;

/**
 * title        : PartnerRequest 팩토리
 * author       : sim
 * date         : 2023-07-07
 * description  : PartnerRequest 엔티티에 대한 팩토리 클래스
 */
public class PartnerRequestFactory {

    public static PartnerRequest createEntity(Member fromMember, Member toMember){
        return PartnerRequest.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .responseStatus(PartnerResponseStatus.NO)
                .build();
    }
}
