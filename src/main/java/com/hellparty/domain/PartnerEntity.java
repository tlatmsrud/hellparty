package com.hellparty.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * title        : PartnerEntity
 * author       : sim
 * date         : 2023-06-30
 * description  : 파트너 엔티티
 */

@Entity
@NoArgsConstructor
@Getter
@Table(name = "TBL_PARTNER")
public class PartnerEntity extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTNER_ID")
    private MemberEntity partner;

    public PartnerEntity(MemberEntity member, MemberEntity partner){
        this.member = member;
        this.partner = partner;
    }
}
