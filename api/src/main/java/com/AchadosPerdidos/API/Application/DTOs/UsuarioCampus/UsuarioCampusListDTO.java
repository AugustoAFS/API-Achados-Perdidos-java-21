package com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCampusListDTO {
    private List<UsuarioCampusDTO> usuarioCampus;
    private int totalCount;
}
