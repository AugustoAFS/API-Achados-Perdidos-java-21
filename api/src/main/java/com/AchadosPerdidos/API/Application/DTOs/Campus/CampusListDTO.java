package com.AchadosPerdidos.API.Application.DTOs.Campus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampusListDTO {
    private List<CampusDTO> campi;
    private int totalCount;
}