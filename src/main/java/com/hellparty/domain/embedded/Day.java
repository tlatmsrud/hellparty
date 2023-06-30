package com.hellparty.domain.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */

@Embeddable
public class Day {

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
