package com.hellparty.controller;

import com.hellparty.annotation.LoginMemberId;
import com.hellparty.dto.ChattingHistoryDTO;
import com.hellparty.service.ChattingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * title        : 채팅 컨트롤러
 * author       : sim
 * date         : 2023-07-26
 * description  : 채팅 컨트롤러
 */
@RestController
@RequestMapping("/api/chatting")
@RequiredArgsConstructor
public class ChattingController {

    private final ChattingService chattingService;

    /**
     * 채팅방 ID 조회
     * @param loginId - 로그인 사용자 ID
     * @param memberId - 상대방 사용자 ID
     * @return 두 사용자 ID에 매핑된 채팅방 ID를 조회한다.
     *          첫 채팅일 경우 내부 로직에 의해 채팅방 생성 후 조회한다.
     */
    @GetMapping("/room/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public Long getChattingRoom(@LoginMemberId Long loginId, @PathVariable("memberId") Long memberId){
        return chattingService.getChattingRoom(loginId, memberId);
    }

    /**
     * 채팅 내역 조회
     * @param roomId - 채팅방 ID
     * @return 채팅내역 리스트
     */
    @GetMapping("/history/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ChattingHistoryDTO> getChatHistory(@PathVariable("roomId") Long roomId){
        return chattingService.getChattingHistory(roomId);
    }
}
