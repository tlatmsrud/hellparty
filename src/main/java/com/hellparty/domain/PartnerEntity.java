package com.hellparty.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
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
