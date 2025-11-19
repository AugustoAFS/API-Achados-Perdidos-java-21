package com.AchadosPerdidos.API.Application.DTOs.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageCreateDTO {
    private String idChat;
    private String idUsuarioRemetente;
    private String idUsuarioDestino;
    private String menssagem;
    private Tipo_Menssagem tipo;
    private Status_Menssagem status;
}
