package com.hellparty.domain;

import com.hellparty.domain.embedded.Address;
import com.hellparty.domain.embedded.BigThree;
import com.hellparty.enums.Division;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

/**
 * title        : 사용자 헬스 정보
 * author       : sim
 * date         : 2023-06-30
 * description  : 사용자의 헬스 관련 정보 엔티티
 */

@Entity
@Table(name = "TBL_MEMBER_HEALTH")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberHealth {

    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "EXEC_STT_TIME")
    @Temporal(TemporalType.TIME)
    private Time execStartTime;

    @Column(name = "EXEC_END_TIME")
    @Temporal(TemporalType.TIME)
    private Time execEndTime;

    @Column(name = "DIV")
    @Enumerated(EnumType.STRING)
    private Division div;

    @Column(name = "EXEC_AREA")
    private Long execArea;

    @Embedded
    private Address gymAddress;

    @Column(name = "SPCL_NOTE")
    private String spclNote;

    @Embedded
    private BigThree bigThree;

    @Column(name = "HEALTH_MOTTO")
    private String healthMotto;

    public void updateMemberHealth(Time execStartTime, Time execEndTime, Division div, Long execArea,
                                   Address gymAddress, String spclNote, BigThree bigThree, String healthMotto){
        this.execStartTime = execStartTime;
        this.execEndTime = execEndTime;
        this.div = div;
        this.execArea = execArea;
        this.gymAddress = gymAddress;
        this.spclNote = spclNote;
        this.bigThree = bigThree;
        this.healthMotto = healthMotto;
    }
}
