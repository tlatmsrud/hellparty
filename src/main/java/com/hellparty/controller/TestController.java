package com.hellparty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */

@Controller
@RequestMapping("/api/test")
public class TestController {

    @ResponseBody
    public String test(){
        return "test";
    }
}
