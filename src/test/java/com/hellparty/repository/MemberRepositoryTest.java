package com.hellparty.repository;

import attributes.TestConfig;
import attributes.TestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * title        : MemberRepositoryTest
 * author       : sim
 * date         : 2023-07-14
 * description  : MemberRepository 테스트
 */

@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource(properties = { "spring.config.location=classpath:application-db.yml" })
public class MemberRepositoryTest implements TestFixture {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("email로 memberId 조회")
    public void findMemberIdByEmail(){
        assertThat(memberRepository.findMemberIdByEmail(LOGIN_MEMBER_ENTITY.getEmail()))
                .isEqualTo(LOGIN_MEMBER_ID);
    }
}
