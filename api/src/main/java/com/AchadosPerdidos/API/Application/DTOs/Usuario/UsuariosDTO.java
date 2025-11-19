package com.AchadosPerdidos.API.Application.DTOs.Usuario;

import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosDTO {
    private Integer id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String matricula;
    private Integer enderecoId;
    private List<CampusDTO> campus;
    private FotosDTO fotoPerfil;
    private LocalDateTime dtaCriacao;
    private Boolean flgInativo;
    private LocalDateTime dtaRemocao;
}
