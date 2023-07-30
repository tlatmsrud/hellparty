package com.hellparty.repository;

import attributes.TestConfig;
import attributes.TestFixture;
import com.hellparty.dto.PartnerDTO;
import jakarta.persistence.EntityManager;
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
    private EntityManager entityManager;
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
    @DisplayName("파트너 관계 삭제")
    void deletePartnership(){

        // partner1과 partner2는 파트너쉽 관계로, 서로에 대한 파트너 엔티티를 갖고있다.
        assertThat(partnerRepository.findById(3L).isPresent()).isTrue();// LOGIN_MEMBER -> PARTNER_ID_OF_LOGIN_MEMBER
        assertThat(partnerRepository.findById(4L).isPresent()).isTrue(); // PARTNER_ID_OF_LOGIN_MEMBER -> LOGIN_MEMBER

        partnerRepository.deletePartnership(LOGIN_MEMBER_ID, PARTNER_ID_OF_LOGIN_MEMBER);

        entityManager.clear();
        assertThat(partnerRepository.findById(3L).isPresent()).isFalse();// LOGIN_MEMBER -> PARTNER_ID_OF_LOGIN_MEMBER
        assertThat(partnerRepository.findById(4L).isPresent()).isFalse(); // PARTNER_ID_OF_LOGIN_MEMBER -> LOGIN_MEMBER
    }
}
