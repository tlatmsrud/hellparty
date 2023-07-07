package attributes;

import com.hellparty.domain.Member;
import com.hellparty.enums.ExecStatus;
import com.hellparty.enums.MBTI;
import com.hellparty.enums.PartnerFindStatus;
import com.hellparty.enums.Sex;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-07
 * description  :
 */
public interface TestFixture{
    Long LOGIN_MEMBER_ID = 1L;
    Long VALID_MEMBER_ID = 2L;
    Long NOT_LOOKING_FOR_PARTNER_MEMBER_ID = 4L;
    Long INVALID_MEMBER_ID = 1000L;

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


}
