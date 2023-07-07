package attributes;

import com.hellparty.domain.Member;
import com.hellparty.dto.MemberDTO;
import com.hellparty.dto.PartnerRequestDTO;
import com.hellparty.enums.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

/**
 * title        : Text Fixture
 * author       : sim
 * date         : 2023-07-07
 * description  : 테스트 픽스처 관리 인터페이스
 */
public interface TestFixture{
    Long LOGIN_MEMBER_ID = 1L;
    Long VALID_MEMBER_ID = 2L;
    Long NOT_LOOKING_FOR_PARTNER_MEMBER_ID = 4L;
    Long INVALID_MEMBER_ID = 1000L;

    Pageable DEFAULT_PAGEABLE = PageRequest.of(0,10);
    Member LOGIN_MEMBER_ENTITY = Member.builder()
            .id(LOGIN_MEMBER_ID)
            .age(28)
            .profileUrl("profileUrl")
            .bodyProfileUrl("bodyProfileUrl")
            .nickname("테스트 닉네임")
            .height(170.3)
            .weight(65)
            .sex(Sex.M)
            .mbti(MBTI.ISFJ)
            .execStatus(ExecStatus.W)
            .findStatus(PartnerFindStatus.Y)
            .build();

    Member VALID_MEMBER_ENTITY = Member.builder()
            .id(VALID_MEMBER_ID)
            .age(23)
            .profileUrl("profileUrl")
            .bodyProfileUrl("bodyProfileUrl")
            .nickname("VALID_MEMBER 닉네임")
            .height(180)
            .weight(68)
            .sex(Sex.M)
            .mbti(MBTI.ISFJ)
            .execStatus(ExecStatus.W)
            .findStatus(PartnerFindStatus.Y)
            .build();

    Member NOT_LOOKING_FOR_PARTNER_MEMBER_ENTITY = Member.builder()
            .id(NOT_LOOKING_FOR_PARTNER_MEMBER_ID)
            .age(30)
            .profileUrl("profileUrl")
            .bodyProfileUrl("bodyProfileUrl")
            .nickname("파트너 안찾아요 닉네임")
            .height(180)
            .weight(68)
            .sex(Sex.W)
            .mbti(MBTI.ISFJ)
            .execStatus(ExecStatus.W)
            .findStatus(PartnerFindStatus.N)
            .build();

    List<PartnerRequestDTO> PARTNER_REQUEST_DTO_LIST = Arrays.asList(
            new PartnerRequestDTO(LOGIN_MEMBER_ID, MemberDTO.builder().id(20L).build(), PartnerResponseStatus.WAIT)
            ,new PartnerRequestDTO(LOGIN_MEMBER_ID, MemberDTO.builder().id(21L).build(), PartnerResponseStatus.NO)
            ,new PartnerRequestDTO(LOGIN_MEMBER_ID, MemberDTO.builder().id(22L).build(), PartnerResponseStatus.YES)

    );
}
