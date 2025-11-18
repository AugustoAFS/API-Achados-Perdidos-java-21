package com.AchadosPerdidos.API.Application.DTOs.ChatMessage;

import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de mensagem de chat")
public class ChatMessageCreateDTO {
    @Schema(description = "ID do chat", example = "chat123", required = true)
    private String idChat;

    @Schema(description = "ID do usuario remetente", example = "1", required = true)
    private String idUsuarioRemetente;

    @Schema(description = "ID do usuario destino", example = "2", required = true)
    private String idUsuarioDestino;

    @Schema(description = "Mensagem", example = "Olá, encontrei seu item!", required = true)
    private String menssagem;

    @Schema(description = "Tipo da mensagem", example = "CHAT")
    private Tipo_Menssagem tipo;

    @Schema(description = "Status da mensagem", example = "SENT")
    private Status_Menssagem status;

    public ChatMessageCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ChatMessageCreateDTO(String idChat, String idUsuarioRemetente, String idUsuarioDestino, String menssagem, Tipo_Menssagem tipo, Status_Menssagem status) {
        this.idChat = idChat;
        this.idUsuarioRemetente = idUsuarioRemetente;
        this.idUsuarioDestino = idUsuarioDestino;
        this.menssagem = menssagem;
        this.tipo = tipo;
        this.status = status;
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

    public Tipo_Menssagem getTipo() {
        return tipo;
    }

    public void setTipo(Tipo_Menssagem tipo) {
        this.tipo = tipo;
    }

    public Status_Menssagem getStatus() {
        return status;
    }

    public void setStatus(Status_Menssagem status) {
        this.status = status;
    }
}

