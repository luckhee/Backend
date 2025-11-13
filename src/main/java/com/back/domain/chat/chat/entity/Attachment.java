package com.back.domain.chat.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
//static 설정 할지 말지 고민해보심 File 관련해서 Attachment
public class Attachment {
    private String type;
    private String url;
    private Map<String, Object> metadata;
}
