package com.AchadosPerdidos.API.Application.DTOs.ChatMessage;

import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de mensagem de chat")
public class ChatMessageUpdateDTO {
    @Schema(description = "Mensagem", example = "Olá, encontrei seu item!")
    private String menssagem;

    @Schema(description = "Status da mensagem", example = "READ")
    private Status_Menssagem status;

    @Schema(description = "Tipo da mensagem", example = "CHAT")
    private Tipo_Menssagem tipo;

    public ChatMessageUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ChatMessageUpdateDTO(String menssagem, Status_Menssagem status, Tipo_Menssagem tipo) {
        this.menssagem = menssagem;
        this.status = status;
        this.tipo = tipo;
    }

    public String getMenssagem() {
        return menssagem;
    }

    public void setMenssagem(String menssagem) {
        this.menssagem = menssagem;
    }

    public Status_Menssagem getStatus() {
        return status;
    }

    public void setStatus(Status_Menssagem status) {
        this.status = status;
    }

    public Tipo_Menssagem getTipo() {
        return tipo;
    }

    public void setTipo(Tipo_Menssagem tipo) {
        this.tipo = tipo;
    }
}

