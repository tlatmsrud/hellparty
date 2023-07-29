package attributes;

import com.hellparty.domain.MemberEntity;
import com.hellparty.domain.MemberHealthEntity;
import com.hellparty.domain.PartnerRequestEntity;
import com.hellparty.domain.embedded.Address;
import com.hellparty.domain.embedded.BigThree;
import com.hellparty.domain.embedded.ExecDay;
import com.hellparty.dto.*;
import com.hellparty.enums.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * title        : Text Fixture
 * author       : sim
 * date         : 2023-07-07
 * description  : 테스트 픽스처 관리 인터페이스
 */
public interface TestFixture {
    Long LOGIN_MEMBER_ID = 1L;
    Long VALID_MEMBER_ID = 2L;
    Long NOT_LOOKING_FOR_PARTNER_MEMBER_ID = 4L;
    Long INVALID_MEMBER_ID = 1000L;
    Long PARTNER_ID_OF_LOGIN_MEMBER = 21L;
    Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10);
    MemberEntity LOGIN_MEMBER_ENTITY = MemberEntity.builder()
            .id(LOGIN_MEMBER_ID)
            .email("tlatmsrud@naver.com")
            .birthYear(1995)
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

    MemberDTO LOGIN_MEMBER_DTO = MemberDTO.builder()
            .id(LOGIN_MEMBER_ID)
            .email(LOGIN_MEMBER_ENTITY.getEmail())
            .birthYear(LOGIN_MEMBER_ENTITY.getBirthYear())
            .profileUrl(LOGIN_MEMBER_ENTITY.getProfileUrl())
            .bodyProfileUrl(LOGIN_MEMBER_ENTITY.getBodyProfileUrl())
            .nickname(LOGIN_MEMBER_ENTITY.getNickname())
            .height(LOGIN_MEMBER_ENTITY.getHeight())
            .weight(LOGIN_MEMBER_ENTITY.getWeight())
            .sex(LOGIN_MEMBER_ENTITY.getSex())
            .mbti(LOGIN_MEMBER_ENTITY.getMbti())
            .build();

    MemberHealthEntity LOGIN_MEMBER_HEALTH_ENTITY = MemberHealthEntity.builder()
            .id(1L)
            .member(LOGIN_MEMBER_ENTITY)
            .execStartTime(Time.valueOf("20:00:00"))
            .execEndTime(Time.valueOf("21:00:00"))
            .div(Division.THREE)
            .execArea(123L)
            .gymAddress(new Address("중랑헬스장","서울시 중랑구", 1L, 2L))
            .spclNote("특이사항")
            .bigThree(new BigThree(100,100,100))
            .healthMotto("헬스 좌우명")
            .execDay(new ExecDay(false, true, true, true, true,true,false))
            .build();

    MemberHealthDTO LOGIN_MEMBER_HEALTH_DTO = MemberHealthDTO.builder()
            .execStartTime(LOGIN_MEMBER_HEALTH_ENTITY.getExecStartTime())
            .execEndTime(LOGIN_MEMBER_HEALTH_ENTITY.getExecEndTime())
            .div(LOGIN_MEMBER_HEALTH_ENTITY.getDiv())
            .execArea(LOGIN_MEMBER_HEALTH_ENTITY.getExecArea())
            .gymAddress(LOGIN_MEMBER_HEALTH_ENTITY.getGymAddress())
            .spclNote(LOGIN_MEMBER_HEALTH_ENTITY.getSpclNote())
            .bigThree(LOGIN_MEMBER_HEALTH_ENTITY.getBigThree())
            .healthMotto(LOGIN_MEMBER_HEALTH_ENTITY.getHealthMotto())
            .build();

    MemberEntity VALID_MEMBER_ENTITY = MemberEntity.builder()
            .id(VALID_MEMBER_ID)
            .email("test@naver.com")
            .birthYear(2000)
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
            .birthYear(1993)
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
            , new PartnerRequestDTO.History(22L, PartnerResponseStatus.Y, 31L, "테스터2", "profileUrl2")
            , new PartnerRequestDTO.History(34L, PartnerResponseStatus.N, 32L, "테스터3", "profileUrl3")
    );

    List<PartnerRequestDTO.History> PARTNER_REQUEST_HISTORY_TO_LOGIN_MEMBER = Arrays.asList(
            new PartnerRequestDTO.History(101L, PartnerResponseStatus.N, 55L, "테스터55", "profileUrl1")
            , new PartnerRequestDTO.History(102L, PartnerResponseStatus.Y, 56L, "테스터56", "profileUrl2")
            , new PartnerRequestDTO.History(103L, PartnerResponseStatus.N, 57L, "테스터57", "profileUrl3")
    );

    MemberDTO.Update UPDATE_MEMBER_DETAIL_REQUEST = MemberDTO.Update.builder()
            .birthYear(1995).height(170).weight(50.1).sex(Sex.M)
            .nickname("nickname").profileUrl("test.url")
            .build();

    MemberDTO.Update UPDATE_MEMBER_DETAIL_REQUEST_WITHOUT_NICKNAME = MemberDTO.Update.builder()
            .birthYear(1995).height(170).weight(50.1).sex(Sex.M)
            .profileUrl("test.url")
            .build();

    MemberHealthDTO.Update UPDATE_HEALTH_DETAIL_REQUEST = MemberHealthDTO.Update.builder()
            .execStartTime(Time.valueOf("19:00:00"))
            .execEndTime(Time.valueOf("20:00:00"))
            .bigThree(new BigThree(100,110,80))
            .execArea(123L)
            .healthMotto("뇌는 근육으로 이루어져있다.")
            .gymAddress(Address.builder()
                    .y(1L)
                    .x(2L)
                    .address("서울시 중랑구")
                    .placeName("중랑헬스장")
                    .build())
            .spclNote("특이사항")
            .div(Division.THREE)
            .execDay(new ExecDayDTO(false, true, true, true, true,true,false))
            .build();

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
            new PartnerDTO(1L,  2L,"파트너1","profileURl",ExecStatus.W)
            , new PartnerDTO(2L, 3L,"파트너2", "profileURl",ExecStatus.I)
            , new PartnerDTO(3L, 4L,"파트너3", "profileURl",ExecStatus.H)
    );
    ExecDayDTO EXEC_DAY_DTO = new ExecDayDTO(false, true, true, true, true, true, false);
    SearchMemberDTO.Request SEARCH_MEMBER_REQUEST_DTO = SearchMemberDTO.Request.builder()
            .fromAge(20)
            .toAge(30)
            .sex(Sex.M)
            .mbti(null)
            .execArea(null)
            .execDay(EXEC_DAY_DTO)
            .execStartTime(Time.valueOf("15:00:00"))
            .execEndTime(Time.valueOf("20:00:00"))
            .build();


    List<SearchMemberDTO.Summary> SEARCH_MEMBER_SUMMARY_DTO = Arrays.asList(
            new SearchMemberDTO.Summary(11L, "닉네임1", 20, Sex.M, "profileUrl1", "bodyProfileUrl1", EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("21:00:00"))
            , new SearchMemberDTO.Summary(12L, "닉네임2", 21, Sex.M, "profileUrl2", "bodyProfileUrl2", EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("20:00:00"))
            , new SearchMemberDTO.Summary(13L, "닉네임3", 22, Sex.W, "defaultProfileUrl", "defaultBodyProfileUrl", EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("20:00:00"))
            , new SearchMemberDTO.Summary(14L, "닉네임4", 23, Sex.W, "profileUrl3", "bodyProfileUrl3", EXEC_DAY_DTO, Time.valueOf("13:00:00"), Time.valueOf("15:00:00"))
            , new SearchMemberDTO.Summary(15L, "닉네임5", 24, Sex.W, "profileUrl4", "bodyProfileUrl4", EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("21:00:00"))
            , new SearchMemberDTO.Summary(16L, "닉네임6", 25, Sex.W, "profileUrl5", "bodyProfileUrl5", EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("21:00:00"))
            , new SearchMemberDTO.Summary(17L, "닉네임7", 26, Sex.M, "profileUrl6", "bodyProfileUrl6", EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("21:00:00"))
            , new SearchMemberDTO.Summary(18L, "닉네임8", 27, Sex.M, "profileUrl7", "bodyProfileUrl7", EXEC_DAY_DTO, Time.valueOf("19:00:00"), Time.valueOf("21:00:00"))
            , new SearchMemberDTO.Summary(19L, "닉네임9", 28, Sex.M, "profileUrl8", "bodyProfileUrl8", EXEC_DAY_DTO, Time.valueOf("10:00:00"), Time.valueOf("13:00:00"))
    );

    SearchMemberDTO.Detail SEARCH_MEMBER_DETAIL_DTO = SearchMemberDTO.Detail.builder()
            .memberId(VALID_MEMBER_ID)
            .nickname("VALID_MEMBER 닉네임")
            .birthYear(2000)
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

   SearchMemberDTO.Detail SEARCH_LOGIN_MEMBER_DETAIL_DTO = SearchMemberDTO.Detail.builder()
            .memberId(LOGIN_MEMBER_ID)
            .nickname("심승경")
            .birthYear(1995)
            .height(170.5)
            .weight(65)
            .mbti(MBTI.ISFJ)
            .sex(Sex.M)
            .execDay(EXEC_DAY_DTO)
            .execStartTime(Time.valueOf("19:00:00"))
            .execEndTime(Time.valueOf("20:30:00"))
            .div(Division.THREE)
            .execArea(null)
            .placeName(null)
            .address(null)
            .x(null)
            .y(null)
            .spclNote("데드 리프트는 못합니다.")
            .benchPress(96)
            .squat(100)
            .deadlift(0)
            .healthMotto("안다치게 하자")
            .build();

    String JWT_SECRET_KEY = "123456789012345678901234567890";
    Map<String, Object> CLAIMS = Map.of(
            "id", 1L, "email", "test@naver.com", "nickname", "테스터");


    String VALID_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
            ".eyJzdWIiOiJyZWZyZXNoVG9rZW4iLCJpZCI6MSwiaWF0IjoxNjg4MjI4NzgwLCJleHAiOjIyOTMwMjg3ODB9" +
            ".I9GzqNott_LJE7xIhhJ4ZuoARNHwoDyAgiUZ8tgVGc4";

    String INVALID_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
            ".eyJzdWIiOiJyZWZyZXNoVG9rZW4iLCJpZCI6MSwiaWF0IjoxNjg4MjI4NzgwLCJleHAiOjIyOTMwMjg3ODB9" +
            ".I9GzqNott_LJE7xIhhJ4ZuoARNHwoDyAgiUZ8tgVGc4_INVALID";
    String VALID_AUTHORIZATION = "Bearer " + VALID_ACCESS_TOKEN;
    String INVALID_AUTHORIZATION = "AUTH " + VALID_ACCESS_TOKEN;

    Long CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER = 1L;

    Long INVALID_CHATTING_ROOM_ID = 1000L;
    Long NEVER_CHATTING_MEMBER_ID = 5L;
    Long CREATED_CHATTING_ROOM_ID = 5L;

    List<ChattingHistoryDTO> CHATTING_HISTORY_DTO_LIST = Arrays.asList(
            new ChattingHistoryDTO(LOGIN_MEMBER_ID, "안녕하세요", LocalDateTime.now())
            , new ChattingHistoryDTO(VALID_MEMBER_ID, "저도 안녕하세요~", LocalDateTime.now())
            , new ChattingHistoryDTO(LOGIN_MEMBER_ID, "다니시는 헬스장이 어디세요?", LocalDateTime.now())
            , new ChattingHistoryDTO(VALID_MEMBER_ID, "용마산에 있는 중곡 스포렉스요!", LocalDateTime.now())
            , new ChattingHistoryDTO(LOGIN_MEMBER_ID, "앗 그럼 같이해요!", LocalDateTime.now())
    );

    List<ChatDTO> ADD_CHAT_DTO_LIST = Arrays.asList(
            new ChatDTO(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER, PARTNER_ID_OF_LOGIN_MEMBER, "조금 늦을것 같습니다.", LocalDateTime.now())
            ,new ChatDTO(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER, LOGIN_MEMBER_ID, "괜찮습니다. 천천히 오세요~", LocalDateTime.now())
    ) ;
}