package com.hellparty.service;

import com.hellparty.dto.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * title        : 웹 소켓 서비스
 * author       : sim
 * date         : 2023-07-20
 * description  : 채팅에 사용되는 웹소켓에 대한 서비스 클래스
 */

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final static String QUEUE = "/queue/";
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final RedisService redisService;

    /**
     * 채팅 보내기
     * @param chatDto - 채팅 DTO
     */
    public void send(ChatDTO chatDto) {
        simpMessagingTemplate.convertAndSend(QUEUE+chatDto.getRoomId(), chatDto.getMessage());
        redisService.saveChatting(chatDto);
    }


}
