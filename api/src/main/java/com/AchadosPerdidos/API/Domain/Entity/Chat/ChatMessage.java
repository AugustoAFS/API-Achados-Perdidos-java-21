package com.AchadosPerdidos.API.Domain.Entity.Chat;

import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import java.time.LocalDateTime;

public class ChatMessage {
    private String id;
    private String Id_Chat;
    private String Id_Usuario_Remetente;
    private String Id_Usuario_Destino;
    private String Menssagem;
    private LocalDateTime Data_Hora_Menssagem;
    private Status_Menssagem Status;
    private Tipo_Menssagem Tipo;

    public ChatMessage() {}

    public ChatMessage(String id_Chat, String id_Usuario_Remetente, String id_Usuario_Destino,
                      String menssagem, Tipo_Menssagem tipo, Status_Menssagem status) {
        this.Id_Chat = id_Chat;
        this.Id_Usuario_Remetente = id_Usuario_Remetente;
        this.Id_Usuario_Destino = id_Usuario_Destino;
        this.Menssagem = menssagem;
        this.Tipo = tipo;
        this.Status = status;
        this.Data_Hora_Menssagem = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_Chat() {
        return Id_Chat;
    }

    public void setId_Chat(String id_Chat) {
        this.Id_Chat = id_Chat;
    }

    public String getId_Usuario_Remetente() {
        return Id_Usuario_Remetente;
    }

    public void setId_Usuario_Remetente(String id_Usuario_Remetente) {
        this.Id_Usuario_Remetente = id_Usuario_Remetente;
    }

    public String getId_Usuario_Destino() {
        return Id_Usuario_Destino;
    }

    public void setId_Usuario_Destino(String id_Usuario_Destino) {
        this.Id_Usuario_Destino = id_Usuario_Destino;
    }

    public String getMenssagem() {
        return Menssagem;
    }

    public void setMenssagem(String menssagem) {
        this.Menssagem = menssagem;
    }

    public LocalDateTime getData_Hora_Menssagem() {
        return Data_Hora_Menssagem;
    }

    public void setData_Hora_Menssagem(LocalDateTime data_Hora_Menssagem) {
        this.Data_Hora_Menssagem = data_Hora_Menssagem;
    }

    public Status_Menssagem getStatus() {
        return Status;
    }

    public void setStatus(Status_Menssagem status) {
        this.Status = status;
    }

    public Tipo_Menssagem getTipo() {
        return Tipo;
    }

    public void setTipo(Tipo_Menssagem tipo) {
        this.Tipo = tipo;
    }
}
