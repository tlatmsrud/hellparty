package com.hellparty.controller;

import com.hellparty.dto.ChatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-19
 * description  :
 */

@Controller
@Slf4j
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/chat-page")
    public String getChattingRoom(){
        return "chat.html";
    }

    @MessageMapping("/send")
    public void send(@RequestBody ChatDTO chatDTO){
        simpMessagingTemplate.convertAndSend("/topic/"+ chatDTO.getRoomId(), chatDTO.getChat());
    }
}


