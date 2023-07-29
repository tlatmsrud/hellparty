package com.hellparty.domain.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * title        : 주소 정보
 * author       : sim
 * date         : 2023-06-30
 * description  : 헬스장에 대한 주소 정보로, 헬스장 이름, 주소, x좌표, y좌표를 포함한다.
 */

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Address {

    @Column(name = "PLACE_NAME")
    private String placeName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "X")
    private Long x;

    @Column(name = "Y")
    private Long y;
}
