package com.back.domain.chat.chat.dto;

import lombok.NonNull;

public record MessageDto(
        Long senderId,
        Long chatRoomId,
        @NonNull
        String content
)
 {
     public MessageDto filterContent(String filterMessage) {
         return new MessageDto(senderId, chatRoomId, filterMessage);
     }

     public MessageDto(Long senderId, Long chatRoomId, String content) {
         this.senderId = senderId;
         this.chatRoomId = chatRoomId;
         this.content = content;
     }

 }
