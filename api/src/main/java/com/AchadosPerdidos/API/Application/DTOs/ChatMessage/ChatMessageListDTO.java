package com.AchadosPerdidos.API.Application.DTOs.ChatMessage;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de mensagens de chat")
public class ChatMessageListDTO {
    @Schema(description = "Lista de mensagens de chat")
    private List<ChatMessageDTO> mensagens;
    
    @Schema(description = "Total de mensagens na lista")
    private int totalCount;

    public ChatMessageListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ChatMessageListDTO(List<ChatMessageDTO> mensagens, int totalCount) {
        this.mensagens = mensagens;
        this.totalCount = totalCount;
    }

    public List<ChatMessageDTO> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<ChatMessageDTO> mensagens) {
        this.mensagens = mensagens;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

