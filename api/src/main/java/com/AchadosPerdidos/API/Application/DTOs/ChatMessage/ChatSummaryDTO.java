package com.AchadosPerdidos.API.Application.DTOs.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatSummaryDTO {
    private String chatId;
    private String otherUserId;
    private String otherUserName;
    private String lastMessage;
    private LocalDateTime lastMessageDate;
    private Integer unreadCount;
    private String idUltimaMensagem;
}

