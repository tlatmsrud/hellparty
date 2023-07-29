package com.hellparty.repository;

import attributes.TestConfig;
import attributes.TestFixture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.dto.SearchMemberDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

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

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    @DisplayName("email로 memberId 조회")
    public void findMemberIdByEmail(){
        assertThat(memberRepository.findMemberIdByEmail(LOGIN_MEMBER_ENTITY.getEmail()))
                .isEqualTo(LOGIN_MEMBER_ID);
    }

    @Test
    @DisplayName("사용자 검색 - 리스트")
    public void searchMemberList(){
        List<SearchMemberDTO.Summary> list = memberRepository.searchMemberList(LOGIN_MEMBER_ID, SEARCH_MEMBER_REQUEST_DTO);
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자 검색 - 상세")
    public void searchMemberDetail() throws JsonProcessingException {
        SearchMemberDTO.Detail result = memberRepository.searchMemberDetail(LOGIN_MEMBER_ID);
        assertThat(objectMapper.writeValueAsString(result)).isEqualTo(objectMapper.writeValueAsString(SEARCH_LOGIN_MEMBER_DETAIL_DTO));
    }
}
