package com.hellparty.controller;

import com.hellparty.annotation.LoginMemberId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * title        : 뷰 컨트롤러
 * author       : sim
 * date         : 2023-07-27
 * description  : 채팅 화면 제공을 위한 임시 뷰 컨트롤러
 */

@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/chatting-list")
    public String chattingList(){

        return "list";
    }

    @GetMapping("/chatting-room/{roomId}")
    public ModelAndView chattingRoom(@LoginMemberId Long loginId, @PathVariable("roomId") Long roomId){
        ModelAndView mav = new ModelAndView();
        mav.addObject("roomId", roomId);
        mav.addObject("loginId", loginId);
        mav.setViewName("chat");

        return mav;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
