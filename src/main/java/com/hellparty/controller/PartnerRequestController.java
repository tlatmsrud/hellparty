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
     * @param fromMemberId - 요청자 ID
     * @param toMemberId - 파트너 요청 수신 ID
     */
    @PostMapping("/{toMemberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void requestPartner(@LoginMemberId Long fromMemberId, @PathVariable("toMemberId") Long toMemberId){
        partnerRequestService.requestPartner(fromMemberId, toMemberId);
    }

    /**
     * 나의 파트너 요청 리스트 조회
     * @param id - 사용자 ID
     * @param pageable - page 속성
     * @return 페이징 처리된 파트너 요청 리스트
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PartnerRequestDTO> getPartnerRequestList(@LoginMemberId Long id, @PageableDefault Pageable pageable){
        return partnerRequestService.getPartnerRequestList(id, pageable);
    }
}
