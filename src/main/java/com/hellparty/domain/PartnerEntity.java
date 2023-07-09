package com.hellparty.domain;

import jakarta.persistence.*;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */

@Entity
@Table(name = "TBL_PARTNER")
public class PartnerEntity extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name = "PARTNER_ID")
    private MemberEntity partner;
}
