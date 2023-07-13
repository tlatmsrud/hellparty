package com.hellparty.controller;

import com.hellparty.annotation.LoginMemberId;
import com.hellparty.dto.PartnerRequestDTO;
import com.hellparty.service.PartnerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * title        : 파트너 요청 컨트롤러
 * author       : sim
 * date         : 2023-07-07
 * description  : 파트너 요청을 관리하는 컨트롤러
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/partner-req")
public class PartnerRequestController {

    private final PartnerRequestService partnerRequestService;

    /**
     * 파트너 요청하기
     * @param loginId - 요청자 ID
     * @param toMemberId - 파트너 요청 수신 ID
     */
    @PostMapping("/{toMemberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void requestPartner(@LoginMemberId Long loginId, @PathVariable("toMemberId") Long toMemberId){
        partnerRequestService.requestPartner(loginId, toMemberId);
    }

    /**
     * 파트너 요청 취소하기
     * @param loginId - 로그인 ID
     * @param requestId - 파트너 요청 ID
     */
    @DeleteMapping("/{requestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelPartner(@LoginMemberId Long loginId, @PathVariable("requestId") Long requestId){
        partnerRequestService.cancelPartner(loginId, requestId);
    }

    /**
     * 나의 파트너 요청 리스트 조회
     * @param memberId - 사용자 ID
     * @param pageable - page 속성
     * @return 페이징 처리된 파트너 요청 리스트
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PartnerRequestDTO.History> getPartnerRequestList(@LoginMemberId Long memberId
            , @PageableDefault Pageable pageable){
        return partnerRequestService.getPartnerRequestList(memberId, pageable);
    }

    /**
     * 나에게 요청한 파트너 요청 리스트 조회
     * @param memberId - 사용자 ID
     * @param pageable - 페이지 객체
     * @return 페이징 처리된 나에게 요청한 파트너 요청 리스트
     */
    @GetMapping("/to-me")
    @ResponseStatus(HttpStatus.OK)
    public Page<PartnerRequestDTO.History> getPartnerRequestToMeList(@LoginMemberId Long memberId
            , @PageableDefault Pageable pageable){
        return partnerRequestService.getPartnerRequestToMeList(memberId, pageable);
    }

    /**
     * 파트너 요청에 대한 응답
     * @param memberId - 사용자 ID
     * @param request - 파트너 요청 응답 Dto
     */
    @PostMapping("/answer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void answerPartnerRequest(@LoginMemberId Long memberId, @RequestBody PartnerRequestDTO.Answer request){
        partnerRequestService.answerPartnerRequest(memberId, request);
    }
}
