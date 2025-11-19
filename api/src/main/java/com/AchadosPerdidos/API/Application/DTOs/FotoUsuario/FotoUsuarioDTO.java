package com.AchadosPerdidos.API.Application.DTOs.FotoUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoUsuarioDTO {
    private Integer id;
    private Integer usuarioId;
    private Integer fotoId;
    private LocalDateTime dtaCriacao;
    private Boolean flgInativo;
    private LocalDateTime dtaRemocao;
}
