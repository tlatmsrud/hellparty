package com.hellparty.controller;

import com.hellparty.dto.MemberDTO;
import com.hellparty.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */

@Controller
@RequestMapping("/api/member")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PutMapping
    public void update(MemberDTO.Update updateMemberDTO){
        memberService.update(updateMemberDTO);
    }
}
