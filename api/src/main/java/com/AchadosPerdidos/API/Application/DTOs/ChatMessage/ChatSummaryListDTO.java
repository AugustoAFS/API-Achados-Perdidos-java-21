package com.AchadosPerdidos.API.Application.DTOs.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatSummaryListDTO {
    private List<ChatSummaryDTO> chats;
    private Integer totalCount;

    public ChatSummaryListDTO(List<ChatSummaryDTO> chats) {
        this.chats = chats;
        this.totalCount = chats != null ? chats.size() : 0;
    }
}

