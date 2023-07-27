package com.hellparty.quartz;

import com.hellparty.dto.ChatDTO;
import com.hellparty.repository.ChattingRepository;
import com.hellparty.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * title        : 채팅내역 저장 잡
 * author       : sim
 * date         : 2023-07-24
 * description  : Redis에 저장된 채팅내역을 DB에 저장하는 잡 클래스
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class SaveChattingJob extends QuartzJobBean {

    private final ChattingRepository chattingRepository;

    private final RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext context) {

        // Redis에 저장된 채팅내역 키 리스트 조회
        Set<String> keySet = redisService.getKeysForChatting();

        // 키 리스트에 대한 채팅내역 조회
        List<ChatDTO> chattingList = redisService.getAllChattingList(keySet);

        // 조회한 채팅 내역을 DB에 저장
        chattingRepository.saveAllChatting(chattingList);

        // Redis에 저장된 채팅내역 삭제
        redisService.delete(keySet);
    }

}
