package com.AchadosPerdidos.API.Application.DTOs.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Item;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private Tipo_Item tipoItem;
    private String descLocalItem;
    private Integer usuarioRelatorId;
    private LocalDateTime dtaCriacao;
    private Boolean flgInativo;
    private LocalDateTime dtaRemocao;
    private List<FotosDTO> fotos;
}
