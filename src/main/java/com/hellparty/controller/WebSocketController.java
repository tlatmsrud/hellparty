package com.hellparty.controller;

import com.hellparty.dto.ChatDTO;
import com.hellparty.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * title        : 웹 소켓 컨트롤러
 * author       : sim
 * date         : 2023-07-19
 * description  : 채팅에 사용되는 웹 소켓에 대한 컨트롤러 클래스
 */

@Controller
@Slf4j
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketService webSocketService;

    /**
     * 채팅 보내기
     * @param chatDto - 채팅 DTO
     */
    @MessageMapping("/send")
    public void send(@RequestBody ChatDTO chatDto){
        webSocketService.send(chatDto);
    }

}


