package com.back.domain.chat.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;


public record MessageDto(
        Long senderId,
        Long chatRoomId,
        @JsonProperty("senderName")
        String senderName,
        @NonNull
        String senderEmail,
        @NonNull
        String content
)
 {
     public MessageDto filterContent(String filterMessage) {
         return new MessageDto(senderId, chatRoomId, senderName, senderEmail, filterMessage);
     }
 }
