package com.AchadosPerdidos.API.Application.DTOs.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageListDTO {
    private List<ChatMessageDTO> mensagens;
    private int totalCount;
}
