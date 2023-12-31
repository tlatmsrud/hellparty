package com.hellparty.controller;

import com.hellparty.annotation.LoginMemberId;
import com.hellparty.dto.PartnerDTO;
import com.hellparty.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 사용자의 파트너 리스트 조회
     * @param loginId - 로그인 ID
     * @return 사용자의 파트너 리스트
     */
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<PartnerDTO> getPartnerList(@LoginMemberId Long loginId){
        return partnerService.getPartnerList(loginId);
    }

    /**
     * 사용자의 파트너 삭제
     * @param loginId - 로그인 ID
     * @param partnerId - 파트너 ID
     */
    @DeleteMapping("/{partnerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartner(@LoginMemberId Long loginId, @PathVariable("partnerId") Long partnerId){
        partnerService.deletePartnership(loginId, partnerId);
    }
}
