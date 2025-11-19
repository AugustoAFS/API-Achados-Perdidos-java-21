package com.AchadosPerdidos.API.Application.DTOs.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageUpdateDTO {
    private String menssagem;
    private String Nome_Campus;
    private Integer Instituicao_Id;
    private Integer Endereco_Id;
    private Status_Menssagem status;
    private Tipo_Menssagem tipo;
}
