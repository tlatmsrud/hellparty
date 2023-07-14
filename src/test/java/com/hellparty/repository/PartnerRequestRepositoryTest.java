package com.hellparty.repository;

import attributes.TestConfig;
import attributes.TestFixture;
import com.hellparty.dto.PartnerRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * title        : PartnerRequestRepository Test
 * author       : sim
 * date         : 2023-07-14
 * description  : PartnerRequestRepository 테스트 클래스
 */

@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource(properties = { "spring.config.location=classpath:application-db.yml" })
public class PartnerRequestRepositoryTest implements TestFixture {

    @Autowired
    private PartnerRequestRepository partnerRequestRepository;

    @Test
    @DisplayName("파트너 요청 리스트 조회")
    void findPartnerRequestList(){
        Page<PartnerRequestDTO.History> list = partnerRequestRepository.findPartnerRequestList(LOGIN_MEMBER_ID, DEFAULT_PAGEABLE);
        assertThat(list.getTotalElements()).isEqualTo(3);
        assertThat(list.getTotalPages()).isEqualTo(1);
        assertThat(list.toList().get(0).getMemberId()).isEqualTo(22);
        assertThat(list.toList().get(1).getMemberId()).isEqualTo(20);
        assertThat(list.toList().get(2).getMemberId()).isEqualTo(21);
    }

    @Test
    @DisplayName("나에게 요청한 파트너 요청 리스트 조회")
    void findPartnerRequestToMeList(){
        Page<PartnerRequestDTO.History> list = partnerRequestRepository.findPartnerRequestToMeList(LOGIN_MEMBER_ID, DEFAULT_PAGEABLE);
        assertThat(list.getTotalElements()).isEqualTo(1);
        assertThat(list.getTotalPages()).isEqualTo(1);
        assertThat(list.toList().get(0).getMemberId()).isEqualTo(24);
    }
}
