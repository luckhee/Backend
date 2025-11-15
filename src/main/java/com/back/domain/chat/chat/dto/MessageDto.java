package com.back.domain.chat.chat.dto;

import lombok.NonNull;

/*
 * Todo
 *  senderId가 있는데 senderName, senderEmail이 필요할까
 * */
public record MessageDto(
        Long senderId,
        Long chatRoomId,
//        @JsonProperty("senderName")
//        String senderName,
//        @NonNull
//        String senderEmail,
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
