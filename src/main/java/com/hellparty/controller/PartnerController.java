package com.hellparty.controller;

import com.hellparty.annotation.LoginMemberId;
import com.hellparty.dto.PartnerDTO;
import com.hellparty.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * title        : 파트너 컨트롤러
 * author       : sim
 * date         : 2023-07-06
 * description  : 파트너 관련 컨트롤러 클래스
 */

@RestController
@RequestMapping("/api/partner")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void RequestPartner(@LoginMemberId Long id, PartnerDTO.Request request){

        partnerService.RequestPartner(id, request);
    }
}
