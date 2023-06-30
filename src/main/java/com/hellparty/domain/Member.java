package com.hellparty.domain;

import com.hellparty.domain.embeded.BigThree;
import com.hellparty.domain.embeded.HealthInfo;
import com.hellparty.enums.MBTI;
import com.hellparty.enums.Sex;
import jakarta.persistence.*;

import java.util.List;

/**
 * title        : Member entity
 * author       : sim
 * date         : 2023-06-30
 * description  : 회원 엔티티
 */

@Entity
@Table(name = "TBL_MEMBER")
public class Member extends Base{

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToMany(mappedBy = "memberId")
    private List<Partner> partnerList;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @Column(name = "HEIGHT" , nullable = false)
    private Long height;

    @Column(name = "WEIGHT" , nullable = false)
    private Long weight;

    @Column(name = "AGE" , nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEX", nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "MBTI")
    private MBTI mbti;
    
    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Column(name = "HEALTH_MOTTO")
    private String healthMotto;

    @Embedded
    private BigThree bigThree;

    @Embedded
    private HealthInfo healthInfo;

}
