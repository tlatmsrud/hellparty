package com.hellparty.domain.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * title        : 3대 운동
 * author       : sim
 * date         : 2023-06-30
 * description  : 벤치프레스, 스쿼트, 데드리프트에 대한 1rm 중량
 */
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BigThree {

    @Column(name = "BENCH_PRESS")
    private double benchPress;

    @Column(name = "SQUAT")
    private double squat;

    @Column(name = "DEADLIFT")
    private double daedlift;
}
