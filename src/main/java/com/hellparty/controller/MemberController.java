package com.hellparty.controller;

import com.hellparty.annotation.LoginMemberId;
import com.hellparty.dto.MemberDTO;
import com.hellparty.dto.MemberHealthDTO;
import com.hellparty.dto.SearchMemberDTO;
import com.hellparty.enums.ExecStatus;
import com.hellparty.enums.PartnerFindStatus;
import com.hellparty.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * title        : MemberController
 * author       : sim
 * date         : 2023-07-01
 * description  : 사용자 컨트롤러
 */

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 사용자 기본정보 조회
     * @param loginId - 로그인 ID
     * @return 사용자 기본정보
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MemberDTO getDetail(@LoginMemberId Long loginId){

        return memberService.getDetail(loginId);

    }

    /**
     * 사용자 헬스정보 조회
     * @param loginId - 로그인 ID
     * @return 헬스정보
     */
    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public MemberHealthDTO getHealthDetail(@LoginMemberId Long loginId){

        return memberService.getHealthDetail(loginId);

    }

    /**
     * 사용자 기본정보 업데이트
     * @param loginId - 로그인 ID
     * @param request - 업데이트 데이터
     */
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDetail(@LoginMemberId Long loginId, @Valid @RequestBody MemberDTO.Update request){

        memberService.updateDetail(loginId, request);
    }

    /**
     * 사용자 헬스정보 업데이트
     * @param loginId - 로그인 ID
     * @param request - 업데이트 데이터
     */
    @PutMapping("/health")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateHealthDetail(@LoginMemberId Long loginId, @RequestBody MemberHealthDTO.Update request){

        memberService.updateHealthDetail(loginId, request);
    }

    /**
     * 운동 상태 업데이트
     * @param loginId - 로그인 ID
     * @param status - 운동상태
     */
    @PatchMapping("/exec-status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateExecStatus(@LoginMemberId Long loginId, @RequestParam ExecStatus status){

        memberService.updateExecStatus(loginId,status);
    }

    /**
     * 파트너 구함 상태 업데이트
     * @param loginId - 로그인 ID
     * @param status - 파트너 구함 상태
     */
    @PatchMapping("/partner-find-status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePartnerFindStatus(@LoginMemberId Long loginId, @RequestParam PartnerFindStatus status){

        memberService.updatePartnerFindStatus(loginId,status);
    }

    /**
     * 사용자 검색 - 리스트
     * @param loginId - 로그인 ID
     * @param request - 사용자 검색 Dto
     * @return 검색된 사용자 리스트
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<SearchMemberDTO.Summary> searchMemberList(@LoginMemberId Long loginId
            , @RequestBody SearchMemberDTO.Request request){

        return memberService.searchMemberList(loginId, request);
    }

    /**
     * 사용자 검색 - 상세조회
     * @param memberId - 상세조회할 사용자 ID
     * @return 사용자 검색에 대한 상세조회 정보
     */
    @GetMapping("/search-detail/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public SearchMemberDTO.Detail searchMemberDetail(@PathVariable("memberId") Long memberId){
        return memberService.searchMemberDetail(memberId);
    }
}
