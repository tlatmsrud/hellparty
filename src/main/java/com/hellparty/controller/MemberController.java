package com.hellparty.controller;

import com.hellparty.annotation.LoginMemberId;
import com.hellparty.dto.MemberDTO;
import com.hellparty.dto.MemberHealthDTO;
import com.hellparty.enums.ExecStatus;
import com.hellparty.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
     * @param id - 사용자 ID
     * @return 사용자 기본정보
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MemberDTO getDetail(@LoginMemberId Long id){

        return memberService.getDetail(id);

    }

    /**
     * 사용자 헬스정보 조회
     * @param id - 사용자 ID
     * @return 헬스정보
     */
    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public MemberHealthDTO getHealthDetail(@LoginMemberId Long id){

        return memberService.getHealthDetail(id);

    }

    /**
     * 사용자 기본정보 업데이트
     * @param id - 사용자 ID
     * @param request - 업데이트 데이터
     */
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDetail(@LoginMemberId Long id, @RequestBody MemberDTO.Update request){

        memberService.updateDetail(id, request);
    }

    /**
     * 사용자 헬스정보 업데이트
     * @param id - 사용자 ID
     * @param request - 업데이트 데이터
     */
    @PutMapping("/health")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateHealthDetail(@LoginMemberId Long id, @RequestBody MemberHealthDTO.Update request){

        memberService.updateHealthDetail(id, request);
    }

    @PatchMapping("/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@LoginMemberId Long id, @RequestParam ExecStatus status){

        memberService.updateStatus(id,status);
    }
}
