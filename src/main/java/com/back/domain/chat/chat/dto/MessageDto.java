package com.back.domain.chat.chat.dto;

import lombok.NonNull;

public record MessageDto(
        @NonNull
        Long senderId,
        @NonNull
        String targetUserEmail,
        @NonNull
        Long chatRoomId,
        @NonNull
        String content
)
 {
     public MessageDto filterContent(String filterMessage) {
         return new MessageDto(senderId, targetUserEmail ,chatRoomId, filterMessage);
     }
 }
