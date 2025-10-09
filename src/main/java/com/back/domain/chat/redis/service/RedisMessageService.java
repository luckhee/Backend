package com.back.domain.chat.redis.service;

import com.back.domain.chat.chat.dto.MessageDto;
import com.back.global.exception.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessageService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic chatTopic;
    private final ObjectMapper objectMapper;

    /*
     * Redis pub/sub을 통해 메시지 발행
     * @param message 발행할 메시지
     */
    public void publishMessage(MessageDto message) {
        long startTime = System.nanoTime();
        try {
            log.info("=== Redis로 메시지 발행 [시작: {}ns] ===", startTime);
            log.info("채팅방: {}, 발신자: {}, 내용: {}",
                    message.getChatRoomId(),
                    message.getSenderName(),
                    message.getContent());

            // JSON 변환 시간 측정
            long jsonStartTime = System.nanoTime();
            String messageJson = objectMapper.writeValueAsString(message);
            long jsonEndTime = System.nanoTime();
            log.info("⏱️ JSON 변환 완료 | 소요시간: {}ms", (jsonEndTime - jsonStartTime) / 1_000_000.0);

            // Redis 발행 시간 측정
            long redisPublishStartTime = System.nanoTime();
            redisTemplate.convertAndSend(chatTopic.getTopic(), messageJson);
            long redisPublishEndTime = System.nanoTime();
            log.info("⏱️ Redis 채널 발행 완료 | 토픽: {} | 소요시간: {}ms", 
                chatTopic.getTopic(), (redisPublishEndTime - redisPublishStartTime) / 1_000_000.0);

            // 전체 Redis 발행 시간
            long totalEndTime = System.nanoTime();
            log.info("✅ Redis 메시지 발행 전체 완료! 총 소요시간: {}ms", 
                (totalEndTime - startTime) / 1_000_000.0);

        } catch (Exception e) {
            long errorTime = System.nanoTime();
            log.error("❌ Redis 메시지 발행 중 에러 발생 ({}ms): {}", 
                (errorTime - startTime) / 1_000_000.0, e.getMessage(), e);
            throw new ServiceException("400-1","메시지 발행 실패");
        }
    }

    /*
     * 특정 채팅방에 메시지 발행
     * @param chatRoomId 채팅방 ID
     * @param message 발행할 메시지
     */
    public void publishMessageToRoom(Long chatRoomId, MessageDto message) {
        try {
            log.info("=== 특정 채팅방으로 메시지 발행 ===");
            log.info("대상 채팅방: {}", chatRoomId);

            // 채팅방별 토픽으로 메시지 발행
            String roomTopic = "chat-room-" + chatRoomId;
            String messageJson = objectMapper.writeValueAsString(message);

            redisTemplate.convertAndSend(roomTopic, messageJson);

            log.info("채팅방별 메시지 발행 완료: 토픽={}", roomTopic);

        } catch (Exception e) {
            log.error("채팅방별 메시지 발행 중 에러 발생: {}", e.getMessage(), e);
            throw new ServiceException("400-1","메시지 발행 실패");
        }
    }
}
