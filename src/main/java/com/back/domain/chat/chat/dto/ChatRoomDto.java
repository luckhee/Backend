package com.back.domain.chat.chat.dto;

import java.util.List;

public record ChatRoomDto (
    Long id,
    String name,
    Long postId,
    String lastContent,
    List<String> participants
) {
    public static ChatRoomDto from(Long id, String name, Long postId, String lastContent, List<String> participants) {
        return new ChatRoomDto(id, name, postId, lastContent, participants);
    }
}
