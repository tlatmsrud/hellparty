package com.hellparty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */

@Controller
@RequestMapping("/api/login/oauth")
public class LoginController {

    @GetMapping("/naver")
    public void naverOauthLogin(){

    }
}
