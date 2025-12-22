package com.back.domain.chat.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "messages")
@NoArgsConstructor
@Getter
public class Message {
    @Id
    private String id;

    private Long senderId;
    private Long chatRoomId;
    private String targetUserEmail;

    private String content;
    private List<Attachment> attachmentLists;

    @CreatedDate
    private LocalDateTime createdAt;




    public void setMessageId (String id) {
        this.id = id;
    }

    public void updateSenderId (Long senderId) {
        this.senderId = senderId;
    }

    public void updateChatRoomId (Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void updateTargetUserEmail (String targetUserEmail) {
        this.targetUserEmail = targetUserEmail;
    }

    public void updateContent (String content) {
        this.content = content;
    }

    public void updateCreatedAt (LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
