package com.hellparty.repository;

import attributes.TestConfig;
import attributes.TestFixture;
import com.hellparty.domain.PartnerEntity;
import com.hellparty.dto.PartnerDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-14
 * description  :
 */


@Import(TestConfig.class)
@DataJpaTest
@TestPropertySource(properties = { "spring.config.location=classpath:application-db.yml" })
public class PartnerRepositoryTest implements TestFixture {

    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    @DisplayName("파트너 리스트 조회")
    void getPartnerList(){
        List<PartnerDTO> list = partnerRepository.getPartnerList(LOGIN_MEMBER_ID);
        assertThat(list).hasSize(2);
        assertThat(list.get(0).getNickname()).isEqualTo("테스터1");
        assertThat(list.get(1).getNickname()).isEqualTo("테스터2");
    }

    @Test
    @DisplayName("MemberId와 PartnerId를 통한 PartnerEntity 조회")
    void findByMemberIdAndPartnerId(){
        PartnerEntity result = partnerRepository.findByMemberIdAndPartnerId(LOGIN_MEMBER_ID, PARTNER_ID_OF_LOGIN_MEMBER);
        assertThat(result.getMember().getId()).isEqualTo(LOGIN_MEMBER_ID);
        assertThat(result.getPartner().getId()).isEqualTo(PARTNER_ID_OF_LOGIN_MEMBER);
    }
}
