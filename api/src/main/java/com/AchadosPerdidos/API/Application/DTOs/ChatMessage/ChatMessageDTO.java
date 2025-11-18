package com.AchadosPerdidos.API.Application.DTOs.ChatMessage;

import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO completo de mensagem de chat")
public class ChatMessageDTO {
    @Schema(description = "ID da mensagem", example = "msg123", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "ID do chat", example = "chat123")
    private String idChat;

    @Schema(description = "ID do usuario remetente", example = "1")
    private String idUsuarioRemetente;

    @Schema(description = "ID do usuario destino", example = "2")
    private String idUsuarioDestino;

    @Schema(description = "Mensagem", example = "Olá, encontrei seu item!")
    private String menssagem;

    @Schema(description = "Data e hora da mensagem", example = "2024-01-01T00:00:00")
    private java.util.Date dataHoraMenssagem;

    @Schema(description = "Status da mensagem", example = "SENT")
    private Status_Menssagem status;

    @Schema(description = "Tipo da mensagem", example = "CHAT")
    private Tipo_Menssagem tipo;

    public ChatMessageDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ChatMessageDTO(String id, String idChat, String idUsuarioRemetente, String idUsuarioDestino, String menssagem, java.util.Date dataHoraMenssagem, Status_Menssagem status, Tipo_Menssagem tipo) {
        this.id = id;
        this.idChat = idChat;
        this.idUsuarioRemetente = idUsuarioRemetente;
        this.idUsuarioDestino = idUsuarioDestino;
        this.menssagem = menssagem;
        this.dataHoraMenssagem = dataHoraMenssagem;
        this.status = status;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getIdUsuarioRemetente() {
        return idUsuarioRemetente;
    }

    public void setIdUsuarioRemetente(String idUsuarioRemetente) {
        this.idUsuarioRemetente = idUsuarioRemetente;
    }

    public String getIdUsuarioDestino() {
        return idUsuarioDestino;
    }

    public void setIdUsuarioDestino(String idUsuarioDestino) {
        this.idUsuarioDestino = idUsuarioDestino;
    }

    public String getMenssagem() {
        return menssagem;
    }

    public void setMenssagem(String menssagem) {
        this.menssagem = menssagem;
    }

    public java.util.Date getDataHoraMenssagem() {
        return dataHoraMenssagem;
    }

    public void setDataHoraMenssagem(java.util.Date dataHoraMenssagem) {
        this.dataHoraMenssagem = dataHoraMenssagem;
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

