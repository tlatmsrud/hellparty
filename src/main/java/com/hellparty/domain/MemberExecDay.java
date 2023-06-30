package com.hellparty.domain;

import jakarta.persistence.*;

/**
 * title        : 운동 요일 엔티티
 * author       : sim
 * date         : 2023-06-30
 * description  : 사용자의 운동 수행 요일에 대한 엔티티
 */

@Entity
@Table(name = "MEMBER_EXEC_DAY")
public class MemberExecDay {

    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member memberId;

    @Column(name = "SUN")
    private int sun;

    @Column(name = "MON")
    private int mon;

    @Column(name = "TUE")
    private int tue;

    @Column(name = "WED")
    private int wed;

    @Column(name = "THU")
    private int thu;

    @Column(name = "FRI")
    private int fri;

    @Column(name = "SAT")
    private int sat;
}
