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
 * title        : 토큰 컨트롤러
 * author       : sim
 * date         : 2023-07-03
 * description  : 토큰 관리 컨트롤러 클래스
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;

    /**
     * 리프레시 토큰을 통한 액세스 토큰 및 리프레시 토큰 갱신
     * @param refreshToken - 리프레시 토큰
     * @return 토큰 객체 (AT + RT)
     */
    @GetMapping("/refresh-token")
    public TokenDTO getRefreshToken(@RequestParam("refreshToken") String refreshToken){
        return tokenService.renewToken(refreshToken);
    }
}
