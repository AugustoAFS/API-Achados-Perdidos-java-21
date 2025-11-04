package com.AchadosPerdidos.API.Domain.Entity.Chat;

import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    private String id;
    private String Id_Chat;
    private String Id_Usuario_Destino;
    private String Id_Usuario_Remetente;
    private String Menssagem;
    private LocalDateTime Data_Hora_Menssagem;
    private Status_Menssagem Status;
    private Tipo_Menssagem Tipo;

    // Construtor customizado para inicialização padrão
    public ChatMessage(String chatId, String senderId, String receiverId, String menssagem, Tipo_Menssagem tipo, Status_Menssagem status) {
        this.Id_Chat = chatId;
        this.Id_Usuario_Remetente = senderId;
        this.Id_Usuario_Destino = receiverId;
        this.Menssagem = menssagem;
        this.Tipo = tipo;
        this.Status = status;
        this.Data_Hora_Menssagem = LocalDateTime.now();
        this.Status = status;
        this.Tipo = tipo;
    }
}
