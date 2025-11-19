package com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCampusDTO {
    private Integer id;
    private Integer usuarioId;
    private Integer campusId;
    private LocalDateTime dtaCriacao;
    private Boolean flgInativo;
    private LocalDateTime dtaRemocao;
}
