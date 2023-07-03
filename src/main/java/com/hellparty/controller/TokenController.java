package com.hellparty.controller;

import com.hellparty.dto.TokenDTO;
import com.hellparty.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-03
 * description  :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/refresh-token")
    public TokenDTO getRefreshToken(@RequestParam("refreshToken") String refreshToken){
        return tokenService.renewToken(refreshToken);
    }
}
