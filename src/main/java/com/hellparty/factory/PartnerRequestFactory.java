package com.hellparty.factory;

import com.hellparty.domain.MemberEntity;
import com.hellparty.domain.PartnerRequestEntity;
import com.hellparty.enums.PartnerResponseStatus;

/**
 * title        : PartnerRequest 팩토리
 * author       : sim
 * date         : 2023-07-07
 * description  : PartnerRequest 엔티티에 대한 팩토리 클래스
 */
public class PartnerRequestFactory {

    public static PartnerRequestEntity createEntity(MemberEntity fromMember, MemberEntity toMember){
        return PartnerRequestEntity.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .responseStatus(PartnerResponseStatus.N)
                .build();
    }
}
