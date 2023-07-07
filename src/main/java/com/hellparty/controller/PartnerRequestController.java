package com.hellparty.controller;

import com.hellparty.annotation.LoginMemberId;
import com.hellparty.service.PartnerRequestService;
import lombok.RequiredArgsConstructor;
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
     * 파트너 요청
     * @param fromMemberId - 요청자 ID
     * @param toMemberId - 파트너 요청 수신 ID
     */
    @PostMapping("/{toMemberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void requestPartner(@LoginMemberId Long fromMemberId, @PathVariable("toMemberId") Long toMemberId){
        partnerRequestService.requestPartner(fromMemberId, toMemberId);
    }
}
