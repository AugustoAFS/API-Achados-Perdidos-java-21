package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.ChatMessage.ChatMessageCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ChatMessage.ChatMessageDTO;
import com.AchadosPerdidos.API.Application.DTOs.ChatMessage.ChatMessageListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ChatMessage.ChatMessageUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatMessageModelMapper {

    public ChatMessageDTO toDTO(ChatMessage chatMessage) {
        if (chatMessage == null) {
            return null;
        }
        
        return new ChatMessageDTO(
            chatMessage.getId(),
            chatMessage.getId_Chat(),
            chatMessage.getId_Usuario_Remetente(),
            chatMessage.getId_Usuario_Destino(),
            chatMessage.getMenssagem(),
            chatMessage.getData_Hora_Menssagem() != null ? Date.from(chatMessage.getData_Hora_Menssagem().atZone(ZoneId.systemDefault()).toInstant()) : null,
            chatMessage.getStatus(),
            chatMessage.getTipo()
        );
    }

    public ChatMessage toEntity(ChatMessageDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(dto.getId());
        chatMessage.setId_Chat(dto.getIdChat());
        chatMessage.setId_Usuario_Remetente(dto.getIdUsuarioRemetente());
        chatMessage.setId_Usuario_Destino(dto.getIdUsuarioDestino());
        chatMessage.setMenssagem(dto.getMenssagem());
        if (dto.getDataHoraMenssagem() != null) {
            chatMessage.setData_Hora_Menssagem(dto.getDataHoraMenssagem().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        chatMessage.setStatus(dto.getStatus());
        chatMessage.setTipo(dto.getTipo());
        
        return chatMessage;
    }

    public ChatMessage fromCreateDTO(ChatMessageCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ChatMessage chatMessage = new ChatMessage(
            dto.getIdChat(),
            dto.getIdUsuarioRemetente(),
            dto.getIdUsuarioDestino(),
            dto.getMenssagem(),
            dto.getTipo() != null ? dto.getTipo() : com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem.CHAT,
            dto.getStatus() != null ? dto.getStatus() : com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem.SENT
        );
        
        return chatMessage;
    }

    public void updateFromDTO(ChatMessage chatMessage, ChatMessageUpdateDTO dto) {
        if (chatMessage == null || dto == null) {
            return;
        }
        
        if (dto.getMenssagem() != null) {
            chatMessage.setMenssagem(dto.getMenssagem());
        }
        if (dto.getStatus() != null) {
            chatMessage.setStatus(dto.getStatus());
        }
        if (dto.getTipo() != null) {
            chatMessage.setTipo(dto.getTipo());
        }
    }

    public ChatMessageListDTO toListDTO(List<ChatMessage> mensagens) {
        if (mensagens == null) {
            return null;
        }
        
        List<ChatMessageDTO> dtoList = mensagens.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new ChatMessageListDTO(dtoList, dtoList.size());
    }
}

