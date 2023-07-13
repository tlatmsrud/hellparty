package com.hellparty.controller;

import com.hellparty.annotation.LoginMemberId;
import com.hellparty.dto.PartnerFindDTO;
import com.hellparty.service.PartnerFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-10
 * description  :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/find-partner")
public class PartnerFindController {

    private final PartnerFindService partnerFindService;

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<PartnerFindDTO.Summary> searchPartnerCandidateList(@LoginMemberId Long loginId, @RequestBody PartnerFindDTO.Search request){
        return partnerFindService.searchPartnerCandidateList(loginId, request);
    }

    @GetMapping("/detail/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public PartnerFindDTO.Detail getPartnerCandidateDetail(@PathVariable Long memberId){
        return partnerFindService.getPartnerCandidateDetail(memberId);
    }
}
