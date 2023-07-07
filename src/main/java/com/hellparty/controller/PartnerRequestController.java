package com.hellparty.controller;

import com.hellparty.annotation.LoginMemberId;
import com.hellparty.dto.PartnerRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * title        : 파트너 요청 컨트롤러
 * author       : sim
 * date         : 2023-07-07
 * description  : 파트너 요청을 관리하는 컨트롤러
 */

@RestController
@RequestMapping("/api/partner-req")
public class PartnerRequestController {


    /**
     * 파트너 요청
     * @param fromMemberId - 요청자 ID
     * @param request - 요청 DTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void requestPartner(@LoginMemberId Long fromMemberId, @RequestBody PartnerRequestDTO request){

    }
}
