package com.hellparty.controller;

import com.hellparty.annotation.LoginMemberId;
import com.hellparty.dto.MemberDTO;
import com.hellparty.service.MemberService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MemberDTO detail(@LoginMemberId Long id){

        return memberService.getDetail(id);

    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(MemberDTO.Update updateMemberDTO){
        memberService.update(updateMemberDTO);
    }


}
