package attributes;

import com.hellparty.domain.MemberEntity;
import com.hellparty.domain.PartnerEntity;
import com.hellparty.domain.PartnerRequestEntity;
import com.hellparty.dto.*;
import com.hellparty.enums.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    Long PARTNER_ID_OF_LOGIN_MEMBER = 21L;
    Pageable DEFAULT_PAGEABLE = PageRequest.of(0,10);
    MemberEntity LOGIN_MEMBER_ENTITY = MemberEntity.builder()
            .id(LOGIN_MEMBER_ID)
            .email("tlatmsrud@naver.com")
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

    MemberEntity VALID_MEMBER_ENTITY = MemberEntity.builder()
            .id(VALID_MEMBER_ID)
            .email("test@naver.com")
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

    MemberEntity NOT_LOOKING_FOR_PARTNER_MEMBER_ENTITY = MemberEntity.builder()
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

    List<PartnerRequestDTO.History> PARTNER_REQUEST_HISTORY_FROM_LOGIN_MEMBER = Arrays.asList(
            new PartnerRequestDTO.History(1L, PartnerResponseStatus.N, 30L, "테스터1", "profileUrl1")
            ,new PartnerRequestDTO.History(22L, PartnerResponseStatus.Y, 31L, "테스터2", "profileUrl2")
            ,new PartnerRequestDTO.History(34L, PartnerResponseStatus.N, 32L, "테스터3", "profileUrl3")
    );

    List<PartnerRequestDTO.History> PARTNER_REQUEST_HISTORY_TO_LOGIN_MEMBER = Arrays.asList(
            new PartnerRequestDTO.History(101L, PartnerResponseStatus.N, 55L, "테스터55", "profileUrl1")
            ,new PartnerRequestDTO.History(102L, PartnerResponseStatus.Y, 56L, "테스터56", "profileUrl2")
            ,new PartnerRequestDTO.History(103L, PartnerResponseStatus.N, 57L, "테스터57", "profileUrl3")
    );


    Long PARTNER_REQUEST_ID_TO_LOGIN_MEMBER = 1L;
    Long PARTNER_REQUEST_ID_FROM_LOGIN_MEMBER = 2L;
    Long INVALID_PARTNER_REQUEST_ID = 1000L;

    PartnerRequestDTO.Answer VALID_PARTNER_REQUEST_ANSWER = new PartnerRequestDTO.Answer(PARTNER_REQUEST_ID_TO_LOGIN_MEMBER, PartnerResponseStatus.Y);

    PartnerRequestDTO.Answer INVALID_PARTNER_REQUEST_ANSWER = new PartnerRequestDTO.Answer(INVALID_PARTNER_REQUEST_ID, PartnerResponseStatus.Y);

    PartnerRequestEntity PARTNER_REQUEST_ENTITY_TO_LOGIN_MEMBER = PartnerRequestEntity.builder()
            .id(PARTNER_REQUEST_ID_TO_LOGIN_MEMBER)
            .toMember(LOGIN_MEMBER_ENTITY)
            .fromMember(VALID_MEMBER_ENTITY)
            .responseStatus(PartnerResponseStatus.W)
            .build();

    PartnerRequestEntity PARTNER_REQUEST_ENTITY_FROM_LOGIN_MEMBER = PartnerRequestEntity.builder()
            .id(PARTNER_REQUEST_ID_FROM_LOGIN_MEMBER)
            .toMember(VALID_MEMBER_ENTITY)
            .fromMember(LOGIN_MEMBER_ENTITY)
            .responseStatus(PartnerResponseStatus.W)
            .build();
    List<PartnerDTO> PARTNER_DTO_LIST_FOR_LOGIN_MEMBER = Arrays.asList(
            new PartnerDTO(1L, "파트너1", ExecStatus.W)
            ,new PartnerDTO(2L, "파트너2", ExecStatus.I)
            ,new PartnerDTO(3L, "파트너3", ExecStatus.H)
    );
    ExecDayDTO EXEC_DAY_DTO = new ExecDayDTO(false, true, true, true, true, true, false);
    PartnerFindDTO.Search PARTNER_FIND_SEARCH_REQUEST = PartnerFindDTO.Search.builder()
            .fromAge(20)
            .toAge(30)
            .sex(null)
            .mbti(null)
            .execArea(null)
            .execDay(EXEC_DAY_DTO)
            .execStartTime(null)
            .execEndTime(null)
            .build();



    List<PartnerFindDTO.Summary> PARTNER_FIND_DTO_SUMMARY_LIST = Arrays.asList(
            new PartnerFindDTO.Summary(11L, "닉네임1", 20, Sex.M, "profileUrl1","bodyProfileUrl1",EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("21:00:00"))
            ,new PartnerFindDTO.Summary(12L, "닉네임2", 21, Sex.M, "profileUrl2","bodyProfileUrl2",EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("20:00:00"))
            ,new PartnerFindDTO.Summary(13L, "닉네임3", 22, Sex.W, "defaultProfileUrl","defaultBodyProfileUrl",EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("20:00:00"))
            ,new PartnerFindDTO.Summary(14L, "닉네임4", 23, Sex.W, "profileUrl3","bodyProfileUrl3",EXEC_DAY_DTO, Time.valueOf("13:00:00"), Time.valueOf("15:00:00"))
            ,new PartnerFindDTO.Summary(15L, "닉네임5", 24, Sex.W, "profileUrl4","bodyProfileUrl4",EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("21:00:00"))
            ,new PartnerFindDTO.Summary(16L, "닉네임6", 25, Sex.W, "profileUrl5","bodyProfileUrl5",EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("21:00:00"))
            ,new PartnerFindDTO.Summary(17L, "닉네임7", 26, Sex.M, "profileUrl6","bodyProfileUrl6",EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("21:00:00"))
            ,new PartnerFindDTO.Summary(18L, "닉네임8", 27, Sex.M, "profileUrl7","bodyProfileUrl7",EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("21:00:00"))
            ,new PartnerFindDTO.Summary(19L, "닉네임9", 28, Sex.M, "profileUrl8","bodyProfileUrl8",EXEC_DAY_DTO, Time.valueOf("10:00:00"), Time.valueOf("13:00:00"))
    );

    PartnerFindDTO.Detail PARTNER_FIND_DETAIL_FOR_VALID_MEMBER_ID = PartnerFindDTO.Detail.builder()
            .memberId(VALID_MEMBER_ID)
            .nickname("VALID_MEMBER 닉네임")
            .age(23)
            .height(180)
            .weight(68)
            .mbti(MBTI.ISFJ)
            .sex(Sex.M)
            .execDay(EXEC_DAY_DTO)
            .execStartTime(Time.valueOf("19:00:00"))
            .execEndTime(Time.valueOf("20:00:00"))
            .div(Division.THREE)
            .execArea(1234L)
            .placeName("삼성짐")
            .address("서울시 중랑구 면목동 19동 삼성짐")
            .x(674L)
            .y(123L)
            .spclNote("특이사항")
            .benchPress(100)
            .squat(200)
            .deadlift(110)
            .healthMotto("헬스가 인생이다.")
            .build();

    PartnerEntity VALID_PARTNER_ENTITY = new PartnerEntity(1L, LOGIN_MEMBER_ENTITY, VALID_MEMBER_ENTITY);

    String JWT_SECRET_KEY = "123456789012345678901234567890";
    Map<String, Object> CLAIMS = Map.of(
            "id",1L,"email","test@naver.com","nickname","테스터");


    String VALID_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
            ".eyJzdWIiOiJyZWZyZXNoVG9rZW4iLCJpZCI6MSwiaWF0IjoxNjg4MjI4NzgwLCJleHAiOjIyOTMwMjg3ODB9" +
            ".I9GzqNott_LJE7xIhhJ4ZuoARNHwoDyAgiUZ8tgVGc4";

    String INVALID_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
            ".eyJzdWIiOiJyZWZyZXNoVG9rZW4iLCJpZCI6MSwiaWF0IjoxNjg4MjI4NzgwLCJleHAiOjIyOTMwMjg3ODB9" +
            ".I9GzqNott_LJE7xIhhJ4ZuoARNHwoDyAgiUZ8tgVGc4_INVALID";
    String VALID_AUTHORIZATION = "Bearer "+ VALID_ACCESS_TOKEN;
    String INVALID_AUTHORIZATION = "AUTH "+ VALID_ACCESS_TOKEN;
}
