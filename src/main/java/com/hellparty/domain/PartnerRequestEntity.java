package com.hellparty.domain;

import com.hellparty.enums.PartnerResponseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * title        : 파트너 요청 엔티티
 * author       : sim
 * date         : 2023-07-07
 * description  : 파트너 요청 관리 엔티티
 */

@Entity
@Table(name = "TBL_PARTNER_REQUEST")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequestEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_MEMBER_ID")
    private MemberEntity fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_MEMBER_ID")
    private MemberEntity toMember;

    @Column(name = "RESPONSE_STATUS")
    @Enumerated(EnumType.STRING)
    private PartnerResponseStatus responseStatus;

    public void updateStatus(PartnerResponseStatus responseStatus){
        this.responseStatus = responseStatus;
    }
}
