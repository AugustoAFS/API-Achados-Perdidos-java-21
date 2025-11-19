package com.AchadosPerdidos.API.Application.DTOs.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private String id;
    private String idChat;
    private String idUsuarioRemetente;
    private String idUsuarioDestino;
    private String menssagem;
    private LocalDateTime dataHoraMenssagem;
    private Status_Menssagem status;
    private Tipo_Menssagem tipo;
}
