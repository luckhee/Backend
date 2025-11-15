package com.back.domain.chat.chat.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collation = "messages")
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    private String id;

    private Long senderId;
    private Long chatRoomId;

    private String content;
    private List<Attachment> attachmentLists;

    @CreatedDate
    private LocalDateTime createdAt;

}
