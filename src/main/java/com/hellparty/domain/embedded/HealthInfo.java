package com.hellparty.domain.embedded;

import com.hellparty.enums.Division;
import jakarta.persistence.*;

import java.sql.Time;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */

@Embeddable
public class HealthInfo {

    @Embedded
    private Day execDay;

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
}
