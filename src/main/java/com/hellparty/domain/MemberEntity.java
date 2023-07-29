package com.hellparty.domain;

import com.hellparty.enums.ExecStatus;
import com.hellparty.enums.MBTI;
import com.hellparty.enums.PartnerFindStatus;
import com.hellparty.enums.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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
@Getter
public class MemberEntity extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<PartnerEntity> partnerList;

    @OneToOne(mappedBy = "member")
    private MemberHealthEntity memberHealth;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "HEIGHT")
    private double height;

    @Column(name = "WEIGHT")
    private double weight;

    @Column(name = "BIRTH_YEAR")
    private int birthYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEX")
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "MBTI")
    private MBTI mbti;
    
    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Column(name = "BODY_PROFILE_URL")
    private String bodyProfileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "EXEC_STATUS", nullable = false)
    @Builder.Default // TODO : 초기화 하지 않는 쪽으로 구상해보자.
    private ExecStatus execStatus = ExecStatus.W;

    @Enumerated(EnumType.STRING)
    @Column(name = "PARTNER_FIND_STATUS")
    @Builder.Default
    private PartnerFindStatus findStatus = PartnerFindStatus.N;

    public void updateMember(String nickname, double height, double weight
            , int birthYear, Sex sex, MBTI mbti, String profileUrl){
        this.nickname = nickname;
        this.height = height;
        this.weight = weight;
        this.birthYear = birthYear;
        this.sex = sex;
        this.mbti = mbti;
        this.profileUrl = profileUrl;
    }

    public void updateExecStatus(ExecStatus execStatus){
        this.execStatus = execStatus;
    }

    public void updatePartnerFindStatus(PartnerFindStatus findStatus){
        this.findStatus = findStatus;
    }

    public boolean isLookingForPartner(){
        return findStatus == PartnerFindStatus.Y;
    }
}
