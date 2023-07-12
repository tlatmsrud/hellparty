package com.hellparty.repository;

import attributes.TestConfig;
import attributes.TestFixture;
import com.hellparty.dto.PartnerFindDTO;
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
 * date         : 2023-07-12
 * description  :
 */

@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource(properties = { "spring.config.location=classpath:application-db.yml" })
public class PartnerFindRepositoryTest implements TestFixture {

    @Autowired
    private PartnerFindRepository partnerFindRepository;

    @Test
    void searchPartnerCandidateList(){
        List<PartnerFindDTO.Summary> list =
                partnerFindRepository.searchPartnerCandidateList(LOGIN_MEMBER_ID, PARTNER_FIND_SEARCH_REQUEST);

        assertThat(list.size()).isEqualTo(1);

        list.forEach(a -> System.out.println(a.getMemberId()));

    }


}
