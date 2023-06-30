package com.hellparty.domain.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * title        : 3대 운동
 * author       : sim
 * date         : 2023-06-30
 * description  : 벤치프레스, 스쿼트, 데드리프트에 대한 1rm 중량
 */
@Embeddable
public class BigThree {

    @Column(name = "BENCH_PRESS")
    private Long benchPress;

    @Column(name = "SQUAT")
    private Long squat;

    @Column(name = "DEADLIFT")
    private Long daedlift;
}
