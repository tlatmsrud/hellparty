package com.hellparty.domain;

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
public class PartnerRequest {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_MEMBER_ID")
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_MEMBER_ID")
    private Member toMember;

    @Column(name = "RESPONSE_FLAG")
    private boolean responseFlag;
}
