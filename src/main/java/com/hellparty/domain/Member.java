package com.hellparty.domain;

import com.hellparty.enums.ExecStatus;
import com.hellparty.enums.MBTI;
import com.hellparty.enums.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * title        : Member entity
 * author       : sim
 * date         : 2023-06-30
 * description  : 회원 엔티티
 */

@Entity
@Table(name = "TBL_MEMBER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Base{

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToMany(mappedBy = "memberId")
    private List<Partner> partnerList;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "HEIGHT")
    private Long height;

    @Column(name = "WEIGHT")
    private Long weight;

    @Column(name = "AGE")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEX")
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "MBTI")
    private MBTI mbti;
    
    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private ExecStatus status = ExecStatus.W;
}
