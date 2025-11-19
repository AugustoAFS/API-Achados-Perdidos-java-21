package com.AchadosPerdidos.API.Application.DTOs.Local;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalListDTO {
    private List<LocalDTO> Locais;
}
