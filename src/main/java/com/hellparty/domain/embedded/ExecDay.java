package com.hellparty.domain.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-05
 * description  :
 */

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExecDay {
    private boolean sun;

    private boolean mon;

    private boolean tue;

    private boolean wed;

    private boolean thu;

    private boolean fri;

    private boolean sat;
}
